package com.campuscoders.posterminalapp.utils


import com.campuscoders.posterminalapp.data.remote.dto.UserData

data class LoginResult(
    val role: String,
    val userData: UserData? = null,
    val isOffline: Boolean = false
)

object Roles {
    const val MAIN_USER = "main_user"
    const val TERMINAL_USER = "terminal_user"
}
