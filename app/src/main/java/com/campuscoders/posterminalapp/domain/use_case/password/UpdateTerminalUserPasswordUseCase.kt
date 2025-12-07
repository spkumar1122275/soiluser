package com.campuscoders.posterminalapp.domain.use_case.password

import com.campuscoders.posterminalapp.domain.repository.AuthRepository
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject

class UpdateTerminalUserPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(taxId: String, newPassword: String): Resource<Int> {
        return try {
            val result = authRepository.updateTerminalUserPassword(taxId, newPassword)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage ?: "Beklenmeyen hata")
        }
    }
}
