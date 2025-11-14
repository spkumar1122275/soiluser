package com.campuscoders.posterminalapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CustomersResponse(
    @SerializedName("customers")
    val customers: List<CustomerData>? = null
)

data class CustomerData(
    @SerializedName("customer_id")
    val customerId: Int? = null,
    
    @SerializedName("customer_vkn_tckn")
    val customertaxId: String? = null,
    
    @SerializedName("customer_company_name")
    val customerCompanyName: String? = null,
    
    @SerializedName("customer_first_name")
    val customerFirstName: String? = null,
    
    @SerializedName("customer_last_name")
    val customerLastName: String? = null,
    
    @SerializedName("customer_phone_number")
    val customerPhoneNumber: String? = null,
    
    @SerializedName("customer_email")
    val customerEmail: String? = null,
    
    @SerializedName("customer_province")
    val customerProvince: String? = null,
    
    @SerializedName("customer_district")
    val customerDistrict: String? = null,
    
    @SerializedName("customer_tax_office")
    val customerTaxOffice: String? = null,
    
    @SerializedName("customer_address")
    val customerAddress: String? = null
)
