package com.campuscoders.posterminalapp.data.repository.locale

import com.campuscoders.posterminalapp.data.locale.MainUserDao
import com.campuscoders.posterminalapp.data.locale.TerminalUsersDao
import com.campuscoders.posterminalapp.data.mapper.toDomainUser
import com.campuscoders.posterminalapp.domain.model.AnylocalUser
import com.campuscoders.posterminalapp.domain.repository.SecondAuthRepository
import com.campuscoders.posterminalapp.utils.SecondLoginSharedPreferences
import javax.inject.Inject

class SecondAuthRepositoryImpl @Inject constructor(
    private val terminalUsersDao: TerminalUsersDao,
    private val mainUserDao: MainUserDao,
    private val prefs: SecondLoginSharedPreferences
) : SecondAuthRepository {

    override suspend fun loginOffline(terminalId: String, password: String): AnylocalUser? {
        // 1) Check TerminalUsers (Cashier)
        terminalUsersDao.queryTerminalUser(terminalId)?.let { entity ->
            if (entity.terminalUserPassword == password) {
                return entity.toDomainUser()
            }
        }

        // 2) Check MainUser (Admin)
        mainUserDao.queryMainUser(terminalId)?.let { entity ->
            if (entity.mainUserPassword == password) {
                return entity.toDomainUser()
            }
        }

        // 3) No match found
        return null
    }

    override fun saveSession(user: AnylocalUser) {
        prefs.saveUser(user)

        // Normalize role so it's NEVER null
        val role = when (user) {
            is AnylocalUser.MainUser -> user.role ?: "admin"
            is AnylocalUser.TerminalUser -> user.role ?: "cashier"
        }

        prefs.saveUserRole(role)
        prefs.saveLoginTimestamp()
    }

    override fun getSavedUser(): AnylocalUser? = prefs.getUser()

    override fun getSavedRole(): String? = prefs.getUserRole()

    override fun setRememberMe(value: Boolean) {
        prefs.setRememberMe(value)
    }

    override fun isRememberMe(): Boolean = prefs.isRememberMe()

    override fun isLoginExpired(): Boolean {
        val timestamp = prefs.getLoginTimestamp()
        val now = System.currentTimeMillis()

        // 24 hours timeout
        val maxDuration = 24 * 60 * 60 * 1000L
        return (now - timestamp) > maxDuration
    }

    override fun clearSession() {
        prefs.clear()
    }
}
