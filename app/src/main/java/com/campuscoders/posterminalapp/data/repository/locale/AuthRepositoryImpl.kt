package com.campuscoders.posterminalapp.data.repository.locale

import com.campuscoders.posterminalapp.data.locale.MainUserDao
import com.campuscoders.posterminalapp.data.locale.TerminalUsersDao
import com.campuscoders.posterminalapp.data.mapper.toAnyUser
import com.campuscoders.posterminalapp.data.mapper.toUserData
import com.campuscoders.posterminalapp.data.remote.api.AuthApiService
import com.campuscoders.posterminalapp.data.remote.dto.LoginResponse
import com.campuscoders.posterminalapp.domain.model.AnyUser
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers
import com.campuscoders.posterminalapp.domain.repository.AuthRepository
import com.campuscoders.posterminalapp.utils.CustomSharedPreferences
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val mainUserDao: MainUserDao,
    private val terminalUsersDao: TerminalUsersDao,
    private val api: AuthApiService,
    private val prefs: CustomSharedPreferences,
) : AuthRepository {

    // ------------------------------
    // DATABASE (LOCAL ROOM) OPERATIONS
    // ------------------------------

    override suspend fun saveMainUserToDatabase(mainUser: MainUser): Long {
        return mainUserDao.insertMainUser(mainUser)
    }

    override suspend fun fetchMainUserFromDatabase(terminalId: String): MainUser? {
        return mainUserDao.queryMainUser(terminalId)
    }

    override suspend fun fetchMainUserPassword(memberStoreId: String): String? {
        return mainUserDao.queryMainUserWithMemberStoreId(memberStoreId)
    }

    override suspend fun saveTerminalUserToDatabase(terminalUser: TerminalUsers): Long {
        return terminalUsersDao.insertTerminalUser(terminalUser)
    }

    override suspend fun fetchTerminalUserFromDatabase(terminalId: String): TerminalUsers? {
        return terminalUsersDao.queryTerminalUser(terminalId)
    }

    override suspend fun fetchTerminalUserFromDatabaseByMemberStoreId(memberStoreId: String): TerminalUsers? {
        return terminalUsersDao.queryTerminalUserByMemberStoreId(memberStoreId)
    }

    override suspend fun fetchTerminalUserPassword(terminalId: String): String? {
        return terminalUsersDao.queryTerminalUserForPassword(terminalId)
    }

    override suspend fun fetchMainUserCellPhoneNumber(taxId: String): String? {
        return mainUserDao.queryMainUserWithtaxId(taxId)
    }

    override suspend fun updateMainUserPassword(taxId: String, newPassword: String): Int {
        return mainUserDao.updateMainUserPassword(taxId, newPassword)
    }

    override suspend fun updateTerminalUserPassword(taxId: String, newPassword: String): Int {
        return terminalUsersDao.updateTerminalUserPassword(taxId, newPassword)
    }

    // ------------------------------
    // ONLINE LOGIN
    // ------------------------------

    override suspend fun loginOnline(
        terminalId: String,
        taxId: String,
        storeId: String,
        password: String
    ): Result<LoginResponse> = runCatching {
        val response = api.login(terminalId, taxId, storeId, password)
        if (!response.isSuccessful) throw Exception("API error: ${response.code()}")
        response.body() ?: throw Exception("Empty body")
    }

    // ------------------------------
    // OFFLINE LOGIN
    // ------------------------------

    override suspend fun loginOffline(terminalId: String, password: String): AnyUser? {
        return prefs.getUserData()?.toAnyUser()
    }

    // ------------------------------
    // SESSION MANAGEMENT (SharedPreferences)
    // ------------------------------

    override fun saveSession(user: AnyUser) {
        prefs.saveUserData(user.toUserData())
        prefs.saveLoginTimestamp()
    }

    override fun getSavedUser(): AnyUser? {
        return prefs.getUserData()?.toAnyUser()
    }

    override fun getSavedRole(): String? {
        return prefs.getUserRole()
    }

    override fun isLoginExpired(): Boolean {
        return prefs.isLoginExpired()
    }

    override fun clearSession() {
        prefs.clearUserData()
    }
}
