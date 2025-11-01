package com.campuscoders.posterminalapp.domain.use_case.login

import com.campuscoders.posterminalapp.data.remote.dto.LoginResponse
import com.campuscoders.posterminalapp.domain.repository.locale.LoginRepository
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject

class LoginWithApiUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend fun executeLoginWithApi(
        terminalId: String,
        taxId: String,
        memberId: String,
        password: String
    ): Resource<LoginResponse> {
        return try {
            val response = repository.loginWithApi(terminalId, taxId, memberId, password)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(null, response.message() ?: "Login failed")
            }
        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage ?: "Network error occurred")
        }
    }
}
