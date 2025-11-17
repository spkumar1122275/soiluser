package com.campuscoders.posterminalapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.campuscoders.posterminalapp.domain.model.TerminalUsers

@Dao
interface TerminalUsersDao {
    @Insert
    suspend fun insertTerminalUser(terminalUsers: TerminalUsers): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<TerminalUsers>)
    @Query("SELECT * FROM TerminalUsers WHERE terminal_user_terminal_id = :terminalId")
    suspend fun queryTerminalUser(terminalId: String): TerminalUsers?

    @Query("SELECT * FROM TerminalUsers WHERE terminal_user_store_id = :memberStoreId")
    suspend fun queryTerminalUserByMemberStoreId(memberStoreId: String): TerminalUsers?

    @Query("SELECT terminal_user_password FROM TerminalUsers WHERE terminal_user_terminal_id = :terminalId")
    suspend fun queryTerminalUserForPassword(terminalId: String): String?

    @Query("UPDATE TerminalUsers SET terminal_user_password = :newPassword WHERE terminal_user_pan = :taxId")
    suspend fun updateTerminalUserPassword(taxId: String, newPassword: String): Int

    @Query("DELETE FROM TerminalUsers WHERE terminalUserId = :terminalId")
    suspend fun deleteTerminalUser(terminalId: Int): Int

    @Query("SELECT * FROM TerminalUsers")
    suspend fun queryAllTerminalUsers(): List<TerminalUsers>?

    @Query("SELECT terminalUserId FROM TerminalUsers ORDER BY terminalUserId DESC LIMIT 1")
    suspend fun queryLastInsertedTerminalUsers(): Int?

    @Query("SELECT * FROM TerminalUsers WHERE terminalUserId = :terminalId")
    suspend fun queryTerminalUserById(terminalId: String): TerminalUsers?

    @Update
    suspend fun updateTerminalUser(terminalUser: TerminalUsers): Int
}