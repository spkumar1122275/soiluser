package com.campuscoders.posterminalapp.domain.use_case.login

import com.campuscoders.posterminalapp.data.mapper.toDomainUser
import com.campuscoders.posterminalapp.domain.model.AnyUser
import com.campuscoders.posterminalapp.domain.model.LoginParams
import com.campuscoders.posterminalapp.domain.model.LoginResult
import com.campuscoders.posterminalapp.domain.repository.AuthRepository
import com.campuscoders.posterminalapp.domain.repository.CompanyRepository
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject

class HandleLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(params: LoginParams): Resource<LoginResult> {

        if (!authRepository.isLoginExpired()) {
            authRepository.getSavedUser()?.let { localUser ->
                val role = authRepository.getSavedRole() ?: ""
                return Resource.Success(LoginResult(role, localUser, true))
            }
        }

        val resp = authRepository.loginOnline(
            params.terminalId, params.taxId, params.storeId, params.password
        )

        if (resp.isFailure) {
            return Resource.Error(null, resp.exceptionOrNull()?.message ?: "Login failed")
        }

        val body = resp.getOrNull() ?: return Resource.Error(null, "Empty server response")

        val apiUser = body.user ?: return Resource.Error(null, "Malformed response")

        // FIX: USE access_token
        val token = apiUser.access_token
            ?: return Resource.Error(null, "Access token missing")

        val domainUser: AnyUser = apiUser.toDomainUser()

        authRepository.saveSession(domainUser)

        val sync = companyRepository.fetchAndStoreCompanyData(token, apiUser.terminal_id)
        if (sync.isFailure) {
            return Resource.Error(null, "Company Sync Failed: ${sync.exceptionOrNull()?.message}")
        }

        return Resource.Success(
            LoginResult(role = apiUser.role, userData = domainUser, isOffline = false)
        )
    }
}


