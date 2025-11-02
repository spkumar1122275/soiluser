package com.campuscoders.posterminalapp.domain.model


import com.campuscoders.posterminalapp.data.remote.dto.UserData

data class LoginResult(
    val role: String,
    val userData: UserData?,
    val isOffline: Boolean
)
