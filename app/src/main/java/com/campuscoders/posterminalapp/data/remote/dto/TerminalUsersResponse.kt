package com.campuscoders.posterminalapp.data.remote.dto

data class TerminalUsersResponse(
    val terminal_users: List<TerminalUserDto>
)

data class TerminalUserDto(
    val terminal_user_terminal_id: String?,
    val terminal_user_tax_id: String?,
    val terminal_user_full_name: String?,
    val terminal_user_password: String?,
    val terminal_user_date: String?,
    val terminal_user_time: String?,
    val permissions: PermissionsDto?
)

data class PermissionsDto(
    val can_cancel_refund: Boolean?,
    val can_collect_payment: Boolean?,
    val can_view_cashiers: Boolean?,
    val can_add_edit_cashiers: Boolean?,
    val can_delete_cashiers: Boolean?,
    val can_view_products: Boolean?,
    val can_add_edit_products: Boolean?,
    val can_delete_products: Boolean?,
    val can_view_all_reports: Boolean?,
    val can_save_send_reports: Boolean?,
    val can_manage_pos: Boolean?,
    val is_admin: Boolean?
)
