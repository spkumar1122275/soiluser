package com.campuscoders.posterminalapp.data.remote.dto


import com.google.gson.annotations.SerializedName



data class CompanyApiResponse(
    @SerializedName("company") val company: ApiCompany?
)


data class ApiCompany(
    val id: Int,
    val name: String?,
    val address: String?,
    val phone: String?,
    val tax_id: String?,
    val store_id: Int,

    @SerializedName("licenses")
    val licenses: List<ApiLicense>?,

    @SerializedName("departments")
    val departments: List<ApiDepartment>?
)


data class ApiLicense(
    val license_id: Int,
    val license_name: String?,
    val license_ref_no: String?,
    val valid_till: String?
)


data class ApiDepartment(
    val dept_id: Int,
    val dept_name: String,
    val dept_location: String,

    @SerializedName("main_users")
    val mainUsers: List<ApiMainUser>?,

    @SerializedName("terminal_users")
    val terminalUsers: List<ApiTerminalUser>?
)


data class ApiMainUser(
    val emp_no: Int?,
    val terminal_id: String?,
    val pan: String?,
    val password: String?,
    val first_name: String?,
    val last_name: String?,
    val cellphone_number: String?,
    val role: String?,
    val is_admin: Boolean
)


data class ApiTerminalUser(
    val emp_no: Int?,
    val terminal_id: String?,
    val pan: String?,
    val password: String?,
    val first_name: String?,
    val last_name: String?,
    val cellphone_number: String?,
    val role: String?,

    @SerializedName("licenses")
    val licenses: List<ApiTerminalUserLicense>?
)


data class ApiTerminalUserLicense(
    val license_id: Int,
    val license_name: String?
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


