package com.campuscoders.posterminalapp.domain.model

data class LoginParams(
    val terminalId: String,
    val taxId: String,
    val storeId: String,
    val password: String
)
