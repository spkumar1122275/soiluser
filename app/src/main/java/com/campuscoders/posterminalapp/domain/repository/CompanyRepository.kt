package com.campuscoders.posterminalapp.domain.repository

interface CompanyRepository {
    suspend fun fetchAndStoreCompanyData(accessToken: String, terminalId: String): Result<Unit>
    suspend fun deleteAllByStoreId(storeId: Int): Result<Unit>
}
