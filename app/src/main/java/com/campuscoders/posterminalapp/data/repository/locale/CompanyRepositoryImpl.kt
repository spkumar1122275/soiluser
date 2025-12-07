package com.campuscoders.posterminalapp.data.repository.locale

import androidx.room.withTransaction
import com.campuscoders.posterminalapp.data.locale.PosDatabase
import com.campuscoders.posterminalapp.data.mapper.toBundle
import com.campuscoders.posterminalapp.data.remote.api.AuthApiService
import com.campuscoders.posterminalapp.domain.repository.CompanyRepository
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val db: PosDatabase
): CompanyRepository {

    override suspend fun fetchAndStoreCompanyData(accessToken: String, terminalId: String): Result<Unit> = runCatching {
        val response = api.getCompanyData(accessToken, terminalId)

        if (!response.isSuccessful) {
            throw Exception("API error: ${response.code()}")
        }

        val apiResponse = response.body()
            ?: throw Exception("Empty response from server")

        val bundle = apiResponse.toBundle()

        db.withTransaction {
            db.companyDao().insertCompany(bundle.company)
            db.departmentDao().insertAll(bundle.departments)
            db.licenseDao().insertAll(bundle.licenses)
            db.mainUserDao().insertAll(bundle.mainUsers)
            db.terminalUsersDao().insertAll(bundle.terminalUsers)
            db.terminalUserLicenseDao().insertAll(bundle.terminalUserLicenses)
        }
    }

    override suspend fun deleteAllByStoreId(storeId: Int): Result<Unit> = runCatching {
        db.withTransaction {
            db.terminalUsersDao().deleteByStoreId(storeId)
            db.mainUserDao().deleteByStoreId(storeId)
            db.licenseDao().deleteByStoreId(storeId)
            db.departmentDao().deleteByStoreId(storeId)
            db.companyDao().deleteByStoreId(storeId)
        }
    }
}
