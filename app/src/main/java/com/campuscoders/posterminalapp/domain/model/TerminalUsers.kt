package com.campuscoders.posterminalapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity (
    tableName = "TerminalUsers",
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["store_id"],
            childColumns = ["terminal_user_store_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Department::class,
            parentColumns = ["dept_id"],
            childColumns = ["terminal_dept_id"],
            onDelete = ForeignKey.CASCADE
        )

    ],
    indices = [Index(value = ["terminal_dept_id"]), Index(value = ["terminal_user_store_id"])]
)

data class TerminalUsers(
    @ColumnInfo(name = "terminal_user_terminal_id") var terminalUserTerminalId: String? = null,  //FK
    @ColumnInfo(name = "terminal_user_pan") var terminalUsertaxId: String? = null,
    @ColumnInfo(name = "terminal_user_store_id") var terminalUserStoreId: String? = null,
    @ColumnInfo(name = "terminal_user_full_name") var terminalUserFullName: String? = null,
    @ColumnInfo(name = "terminal_user_password") var terminalUserPassword: String? = null,
    @ColumnInfo(name = "terminal_user_date") var terminalUserDate: String? = null,
    @ColumnInfo(name = "terminal_user_time") var terminalUserTime: String? = null,

    // Permission flags
    @ColumnInfo(name = "can_cancel_refund") var canCancelRefund: Boolean? = null,
    @ColumnInfo(name = "can_collect_payment") var canCollectPayment: Boolean? = null,
    @ColumnInfo(name = "can_view_cashiers") var canViewCashiers: Boolean? = null,
    @ColumnInfo(name = "can_add_edit_cashiers") var canAddEditCashiers: Boolean? = null,
    @ColumnInfo(name = "can_delete_cashiers") var canDeleteCashiers: Boolean? = null,
    @ColumnInfo(name = "can_view_products") var canViewProducts: Boolean? = null,
    @ColumnInfo(name = "can_add_edit_products") var canAddEditProducts: Boolean? = null,
    @ColumnInfo(name = "can_delete_products") var canDeleteProducts: Boolean? = null,
    @ColumnInfo(name = "can_view_all_reports") var canViewAllReports: Boolean? = null,
    @ColumnInfo(name = "can_save_send_reports") var canSaveSendReports: Boolean? = null,
    @ColumnInfo(name = "can_manage_pos") var canManagePos: Boolean? = null,
    @ColumnInfo(name = "is_admin") var terminalUserAdmin: Boolean? = null,

    @ColumnInfo(name = "cellphone_number") val terminalUserphoneNumber: String? = null,
    @ColumnInfo(name = "first_name") val terminalUserfirstName: String? = null,
    @ColumnInfo(name = "last_name") val terminalUserlastName: String? = null,
    @ColumnInfo(name = "role") val terminalUserrole: String? = null,
    @ColumnInfo(name = "terminal_dept_id") val terminalUserDeptId: Int? = null,
    @ColumnInfo(name = "emp_no") val terminalUserempNo: Int? = null
) {
    @PrimaryKey(autoGenerate = true)
    var terminalUserId: Int = 0
}

data class DepartmentWithTerminalUsers(
    @Embedded val department: Department,
    @Relation(
        parentColumn = "dept_id",
        entityColumn = "terminal_dept_id"
    )
    val terminalUsers: List<TerminalUsers>
)
