package com.campuscoders.posterminalapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "TerminalUsers")
data class TerminalUsers(
    @ColumnInfo(name = "terminal_user_terminal_id") var terminalUserTerminalId: String? = null,  //FK
    @ColumnInfo(name = "terminal_user_pan") var terminalUsertaxId: String? = null,
    @ColumnInfo(name = "terminal_user_store_id") var terminalUserStoreId: String? = null,
    @ColumnInfo(name = "terminal_user_full_name") var terminalUserFullName: String? = null,
    @ColumnInfo(name = "terminal_user_password") var terminalUserPassword: String? = null,
    @ColumnInfo(name = "terminal_user_date") var terminalUserDate: String? = null,
    @ColumnInfo(name = "terminal_user_time") var terminalUserTime: String? = null,

    // Permission flags
    @ColumnInfo(name = "can_cancel_refund") var terminalUserIptalIade: Boolean? = null,
    @ColumnInfo(name = "can_collect_payment") var terminalUserTahsilat: Boolean? = null,
    @ColumnInfo(name = "can_view_cashiers") var terminalUserKasiyerGoruntuleme: Boolean? = null,
    @ColumnInfo(name = "can_add_edit_cashiers") var terminalUserKasiyerEklemeDuzenleme: Boolean? = null,
    @ColumnInfo(name = "can_delete_cashiers") var terminalUserKasiyerSilme: Boolean? = null,
    @ColumnInfo(name = "can_view_products") var terminalUserUrunGoruntuleme: Boolean? = null,
    @ColumnInfo(name = "can_add_edit_products") var terminalUserUrunEklemeDuzenleme: Boolean? = null,
    @ColumnInfo(name = "can_delete_products") var terminalUserUrunSilme: Boolean? = null,
    @ColumnInfo(name = "can_view_all_reports") var terminalUserTumRaporlariGoruntule: Boolean? = null,
    @ColumnInfo(name = "can_save_send_reports") var terminalUserRaporKaydetGonder: Boolean? = null,
    @ColumnInfo(name = "can_manage_pos") var terminalUserPosYonetimi: Boolean? = null,
    @ColumnInfo(name = "is_admin") var terminalUserAdmin: Boolean? = null,

    @ColumnInfo(name = "cellphone_number") val cellphoneNumber: String? = null,
    @ColumnInfo(name = "first_name") val firstName: String? = null,
    @ColumnInfo(name = "last_name") val lastName: String? = null,
    @ColumnInfo(name = "role") val role: String? = null,
    @ColumnInfo(name = "emp_no") val empNo: Int? = null,
    @ColumnInfo(name = "department_name") val departmentName: String? = null,
    @ColumnInfo(name = "department_location") val departmentLocation: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var terminalUserId: Int = 0
}
