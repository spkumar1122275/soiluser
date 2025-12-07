package com.campuscoders.posterminalapp.data.remote.dto

data class ApiUser(
    val terminal_id: String,
    val tax_id: String,
    val member_store: String,
    val role: String,
    val first_name: String,
    val last_name: String,
    val cellphone_number: String,
    val access_token: String
)