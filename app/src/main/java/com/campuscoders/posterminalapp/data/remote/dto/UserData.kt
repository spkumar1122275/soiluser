package com.campuscoders.posterminalapp.data.remote.dto

data class UserData(
    val terminalId: String?,
    val taxId: String?,
    val memberStore: String?,
    val role: String?,
    val firstName: String?,
    val lastName: String?,
    val cellphoneNumber: String?,
    val accessToken: String?
)
