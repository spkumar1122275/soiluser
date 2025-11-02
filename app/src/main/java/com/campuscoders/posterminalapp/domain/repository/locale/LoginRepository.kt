package com.campuscoders.posterminalapp.domain.repository.locale

import com.campuscoders.posterminalapp.data.remote.dto.LoginResponse
import com.campuscoders.posterminalapp.data.remote.dto.TerminalUsersResponse
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers
import retrofit2.Response

interface LoginRepository {

    // Local Database Methods
    suspend fun saveMainUserToDatabase(mainUser: MainUser): Long

    suspend fun fetchMainUserFromDatabase(terminalId: String): MainUser?

    suspend fun fetchMainUserPassword(memberStoreId: String): String?

    suspend fun saveTerminalUserToDatabase(terminalUser: TerminalUsers): Long

    suspend fun fetchTerminalUserFromDatabase(terminalId: String): TerminalUsers?

    suspend fun fetchTerminalUserFromDatabaseByMemberStoreId(memberStoreId: String): TerminalUsers?

    suspend fun fetchTerminalUserPassword(terminalId: String): String?

    suspend fun fetchMainUserCellPhoneNumber(vknTckn: String): String?

    suspend fun updateMainUserPassword(vknTckn: String, newPassword: String): Int

    suspend fun updateTerminalUserPassword(vknTckn: String, newPassword: String): Int

    // API Methods
    suspend fun loginWithApi(
        terminalId: String,
        taxId: String,
        memberId: String,
        password: String
    ): Response<LoginResponse>

    suspend fun fetchTerminalUsersFromApi(
        accessToken: String,
        terminalId: String
    ): Response<TerminalUsersResponse>

}