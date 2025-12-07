package com.campuscoders.posterminalapp.domain.use_case.password

import com.campuscoders.posterminalapp.domain.repository.AuthRepository
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject



class FetchMainUserCellPhoneNumberUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(taxId: String): Resource<String> {
        return try {
            val phone = authRepository.fetchMainUserCellPhoneNumber(taxId)
            if (phone.isNullOrEmpty()) {
                Resource.Error(null, "Telefon numarası bulunamadı")
            } else {
                Resource.Success(phone)
            }
        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage ?: "Beklenmeyen hata")
        }
    }
}
