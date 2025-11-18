package com.campuscoders.posterminalapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers

@Dao
interface MainUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainUser(mainUser: MainUser): Long  //primary id if success

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<MainUser>)

    @Query("SELECT * FROM MainUser WHERE main_user_terminal_id = :terminalId")
    suspend fun queryMainUser(terminalId: String): MainUser?

    @Query("SELECT main_user_password FROM MainUser WHERE main_user_store_id = :memberStoreId")
    suspend fun queryMainUserWithMemberStoreId(memberStoreId: String): String?

    @Query("SELECT main_user_cellphone_number FROM MainUser WHERE main_user_pan = :taxId")
    suspend fun queryMainUserWithtaxId(taxId: String): String?

    @Query("UPDATE MainUser SET main_user_password = :newPassword WHERE main_user_pan = :taxId")
    suspend fun updateMainUserPassword(taxId: String, newPassword: String): Int

    @Query("SELECT * FROM MainUser WHERE main_user_store_id = :storeId LIMIT 1")
    suspend fun getMainUserByStoreId(storeId: Int): MainUser?

    @Query("DELETE FROM MainUser")
    suspend fun deleteAll()

    @Query("DELETE FROM MainUser WHERE main_user_store_id = :storeId")
    suspend fun deleteByStoreId(storeId: Int)
}