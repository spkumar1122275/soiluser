package com.campuscoders.posterminalapp.domain.use_case.login

import com.campuscoders.posterminalapp.domain.model.AnyUser
import com.campuscoders.posterminalapp.domain.repository.AuthRepository
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject

class FetchMainUserPasswordUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun executeFetchMainUserPassword(memberStoreId: String): Resource<String> {
        return try {
            val response = repository.fetchMainUserPassword(memberStoreId)
            response?.let {
                return@let Resource.Success(it)
            } ?: Resource.Error(null, "User information not found!")
        } catch (e: Exception) {
            Resource.Error(null,e.localizedMessage?:"Error!")
        }
    }
}
