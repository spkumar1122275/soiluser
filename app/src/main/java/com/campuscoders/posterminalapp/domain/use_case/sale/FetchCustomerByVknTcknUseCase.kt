package com.campuscoders.posterminalapp.domain.use_case.sale

import com.campuscoders.posterminalapp.domain.model.Customers
import com.campuscoders.posterminalapp.domain.repository.locale.SaleRepository
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject

class FetchCustomerBytaxIdUseCase @Inject constructor(private val repository: SaleRepository) {
    suspend fun executeFetchCustomerBytaxId(customertaxId: String): Resource<Customers> {
        return try {
            val response = repository.fetchCustomerBytaxId(customertaxId)
            response?.let {
                return@let Resource.Success(it)
            } ?: Resource.Error(null,"No Customer data")
        } catch (e: Exception) {
            Resource.Error(null,e.localizedMessage?:"Error - (executeFetchCustomerById)")
        }
    }
}