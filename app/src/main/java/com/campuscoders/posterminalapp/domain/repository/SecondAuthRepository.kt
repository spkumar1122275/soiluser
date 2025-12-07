package com.campuscoders.posterminalapp.domain.repository

import com.campuscoders.posterminalapp.domain.model.AnylocalUser

interface SecondAuthRepository {

    /**
     * Local DB login (TerminalUsers + MainUser fallback)
     * @return matching AnylocalUser or null
     */
    suspend fun loginOffline(terminalId: String, password: String): AnylocalUser?

    /** Save user session into shared prefs */
    fun saveSession(user: AnylocalUser)

    /** Retrieve saved user model */
    fun getSavedUser(): AnylocalUser?

    /** Retrieve saved role string ("admin", "cashier", etc.) */
    fun getSavedRole(): String?

    /** Remember-me flag */
    fun setRememberMe(value: Boolean)
    fun isRememberMe(): Boolean

    /** Checks if last login >24h old */
    fun isLoginExpired(): Boolean

    /** Clears all user session data */
    fun clearSession()
}
