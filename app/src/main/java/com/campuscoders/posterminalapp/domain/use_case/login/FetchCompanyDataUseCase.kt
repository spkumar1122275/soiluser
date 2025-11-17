package com.campuscoders.posterminalapp.domain.use_case.login

import com.campuscoders.posterminalapp.domain.repository.CompanyRepository

class FetchCompanyDataUseCase(
    private val repository: CompanyRepository
) {

    suspend operator fun invoke(
        accessToken: String,
        terminalId: String
    ): Result<Unit> {
        return repository.fetchAndStoreCompanyData(accessToken, terminalId)
    }
}
