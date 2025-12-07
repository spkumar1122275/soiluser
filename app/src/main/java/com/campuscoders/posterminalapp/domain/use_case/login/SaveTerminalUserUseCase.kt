package com.campuscoders.posterminalapp.domain.use_case.login

import com.campuscoders.posterminalapp.data.locale.TerminalUsersDao
import com.campuscoders.posterminalapp.domain.model.TerminalUsers
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject

class SaveTerminalUserUseCase @Inject constructor(
    private val terminalUsersDao: TerminalUsersDao
) {
    suspend fun executeSaveTerminalUser(terminalUser: TerminalUsers): Resource<Boolean> {
        return try {
            terminalUsersDao.insertTerminalUser(terminalUser)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(false, e.message ?: "Failed to save Terminal User")
        }
    }
}
