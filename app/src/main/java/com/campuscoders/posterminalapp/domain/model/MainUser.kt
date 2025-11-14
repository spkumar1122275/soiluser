package com.campuscoders.posterminalapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "MainUser")
data class MainUser(
    @ColumnInfo(name = "main_user_terminal_id") var mainUserTerminalId: String? = null,
    @ColumnInfo(name = "main_user_tax_id") var mainUserTaxId: String? = null,
    @ColumnInfo(name = "main_user_store_id") var mainUserStoreId: String? = null,
    @ColumnInfo(name = "main_user_password") var mainUserPassword: String? = null,
    @ColumnInfo(name = "main_user_cellphone_number") var mainUserCellphoneNumber: String? = null,
    @ColumnInfo(name = "main_user_first_name") var mainUserFirstName: String? = null,
    @ColumnInfo(name = "main_user_last_name") var mainUserLastName: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var mainUserId: Int = 0
}
