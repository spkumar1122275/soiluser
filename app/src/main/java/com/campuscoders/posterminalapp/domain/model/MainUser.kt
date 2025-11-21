package com.campuscoders.posterminalapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "MainUser",
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["store_id"],
            childColumns = ["main_user_store_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["main_user_store_id"])]
)
data class MainUser(
    @ColumnInfo(name = "main_user_terminal_id") var mainUserTerminalId: String? = null,
    @ColumnInfo(name = "main_user_pan") var mainUserTaxId: String? = null,
    @ColumnInfo(name = "main_user_store_id") var mainUserStoreId: String? = null,
    @ColumnInfo(name = "main_user_department_id") var mainUserDepartmentId: Int? = null,
    @ColumnInfo(name = "main_user_password") var mainUserPassword: String? = null,
    @ColumnInfo(name = "main_user_cellphone_number") var mainUserCellphoneNumber: String? = null,
    @ColumnInfo(name = "main_user_first_name") var mainUserFirstName: String? = null,
    @ColumnInfo(name = "main_user_last_name") var mainUserLastName: String? = null,
    @ColumnInfo(name = "main_user_role") val mainUserRole: String? = null,
    @ColumnInfo(name = "main_user_emp_no") val mainUserEmpNo: Int? = null,
    @ColumnInfo(name = "main_user_is_admin") val  mainUserIsAdmin: Boolean? = null,

    @ColumnInfo(name = "main_user_is_active") val mainUserIsActive: Boolean? = null
) {
    @PrimaryKey(autoGenerate = true)
    var mainUserId: Int = 0
}
