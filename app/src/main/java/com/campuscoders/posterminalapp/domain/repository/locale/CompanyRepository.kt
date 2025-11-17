package com.campuscoders.posterminalapp.domain.repository

import com.campuscoders.posterminalapp.data.locale.PosDatabase
import com.campuscoders.posterminalapp.data.mapper.toBundle
import com.campuscoders.posterminalapp.data.remote.api.AuthApiService
import androidx.room.withTransaction

class CompanyRepository(
    private val api: AuthApiService,
    private val db: PosDatabase
) {

    suspend fun fetchAndStoreCompanyData(
        accessToken: String,
        terminalId: String
    ): Result<Unit> {

        return try {
            val response = api.getCompanyData(accessToken, terminalId)

            if (!response.isSuccessful) {
                return Result.failure(Exception("API error: ${response.code()}"))
            }

            val apiResponse = response.body()
                ?: return Result.failure(Exception("Empty response"))

            val bundle = apiResponse.toBundle()

            db.withTransaction {
                db.companyDao().insertCompany(bundle.company)
                db.licenseDao().insertAll(bundle.licenses)
                db.mainUserDao().insertMainUser(bundle.mainUser)
                db.terminalUsersDao().insertAll(bundle.terminalUsers)
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
