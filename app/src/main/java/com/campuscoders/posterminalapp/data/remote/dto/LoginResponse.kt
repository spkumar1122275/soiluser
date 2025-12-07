package com.campuscoders.posterminalapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user")
    val user: ApiUser?
)