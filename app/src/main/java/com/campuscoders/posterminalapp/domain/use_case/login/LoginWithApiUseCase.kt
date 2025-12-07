package com.campuscoders.posterminalapp.domain.use_case.login

import com.campuscoders.posterminalapp.data.remote.dto.LoginResponse
import com.campuscoders.posterminalapp.domain.repository.AuthRepository
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject

class LoginWithApiUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun executeLoginWithApi(
        terminalId: String,
        taxId: String,
        memberId: String,
        password: String
    ): Resource<LoginResponse> {
        return try {
            val result = repository.loginOnline(terminalId, taxId, memberId, password)
            if (result.isSuccess) {
                Resource.Success(result.getOrThrow())
            } else {
                Resource.Error(null, result.exceptionOrNull()?.message ?: "Login failed")
            }
        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}
