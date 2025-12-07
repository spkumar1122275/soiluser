package com.campuscoders.posterminalapp.domain.repository

import com.campuscoders.posterminalapp.data.remote.dto.LoginResponse
import com.campuscoders.posterminalapp.domain.model.AnyUser
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers

interface AuthRepository {

    // LOCAL DATABASE (MainUser)
    suspend fun saveMainUserToDatabase(mainUser: MainUser): Long
    suspend fun fetchMainUserFromDatabase(terminalId: String): MainUser?
    suspend fun fetchMainUserPassword(memberStoreId: String): String?
    suspend fun fetchMainUserCellPhoneNumber(taxId: String): String?
    suspend fun updateMainUserPassword(taxId: String, newPassword: String): Int

    // LOCAL DATABASE (TerminalUsers)
    suspend fun saveTerminalUserToDatabase(terminalUser: TerminalUsers): Long
    suspend fun fetchTerminalUserFromDatabase(terminalId: String): TerminalUsers?
    suspend fun fetchTerminalUserFromDatabaseByMemberStoreId(memberStoreId: String): TerminalUsers?
    suspend fun fetchTerminalUserPassword(terminalId: String): String?
    suspend fun updateTerminalUserPassword(taxId: String, newPassword: String): Int

    // ONLINE LOGIN
    suspend fun loginOnline(
        terminalId: String,
        taxId: String,
        storeId: String,
        password: String
    ): Result<LoginResponse>

    // OFFLINE LOGIN
    suspend fun loginOffline(terminalId: String, password: String): AnyUser?

    // SESSION MANAGEMENT
    fun saveSession(user: AnyUser)
    fun getSavedUser(): AnyUser?
    fun getSavedRole(): String?
    fun isLoginExpired(): Boolean
    fun clearSession()
}
