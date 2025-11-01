package com.campuscoders.posterminalapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user")
    val user: UserData? = null
)

data class UserData(
    @SerializedName("terminal_id")
    val terminalId: String? = null,
    
    @SerializedName("tax_id")
    val taxId: String? = null,
    
    @SerializedName("member_store")
    val memberStore: String? = null,
    
    @SerializedName("role")
    val role: String? = null,
    
    @SerializedName("first_name")
    val firstName: String? = null,
    
    @SerializedName("last_name")
    val lastName: String? = null,
    
    @SerializedName("cellphone_number")
    val cellphoneNumber: String? = null,
    
    @SerializedName("access_token")
    val accessToken: String? = null
)
