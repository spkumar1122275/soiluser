package com.campuscoders.posterminalapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CompanyApiResponse(
    @SerializedName("company") val company: ApiCompany?,
    @SerializedName("licenses") val licenses: List<ApiLicense>?,
    @SerializedName("main_user") val mainUser: ApiMainUser?,
    @SerializedName("terminal_users") val terminalUsers: List<ApiTerminalUser>?
)


data class ApiCompany(
    val id: Int,
    val name: String?,
    val address: String?,
    val phone: String?,
    val tax_id: String?,
    val store_id: Int
)

data class ApiLicense(
    val license_name: String?,
    val license_ref_no: String?,
    val valid_till: String?
)

data class ApiMainUser(
    val terminal_id: String?,
    val pan: String?,
    val store_id: String?,
    val password: String?,
    val cellphone_number: String?,
    val first_name: String?,
    val last_name: String?,
    val role: String?,
    val emp_no: Int?,
    val department_name: String?,
    val department_location: String?
)

data class ApiTerminalUser(
    val terminal_id: String?,
    val pan: String?,
    val store_id: String?,
    val password: String?,
    val cellphone_number: String?,
    val first_name: String?,
    val last_name: String?,
    val role: String?,
    val emp_no: Int?,
    val department_name: String?,
    val department_location: String?,
    val permissions: ApiPermissions
)

data class ApiPermissions(
    val can_cancel_refund: Boolean,
    val can_collect_payment: Boolean,
    val can_view_cashiers: Boolean,
    val can_add_edit_cashiers: Boolean,
    val can_delete_cashiers: Boolean,
    val can_view_products: Boolean,
    val can_add_edit_products: Boolean,
    val can_delete_products: Boolean,
    val can_view_all_reports: Boolean,
    val can_save_send_reports: Boolean,
    val can_manage_pos: Boolean,
    val is_admin: Boolean
)
