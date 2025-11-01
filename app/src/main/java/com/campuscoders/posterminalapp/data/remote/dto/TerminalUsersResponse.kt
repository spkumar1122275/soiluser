package com.campuscoders.posterminalapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TerminalUsersResponse(
    @SerializedName("terminal_users")
    val terminalUsers: List<TerminalUserData>? = null
)

data class TerminalUserData(
    @SerializedName("terminal_user_id")
    val terminalUserId: String? = null,
    
    @SerializedName("terminal_id")
    val terminalId: String? = null,
    
    @SerializedName("tax_id")
    val taxId: String? = null,
    
    @SerializedName("member_store")
    val memberStore: String? = null,
    
    @SerializedName("full_name")
    val fullName: String? = null,
    
    @SerializedName("password")
    val password: String? = null,
    
    @SerializedName("created_date")
    val createdDate: String? = null,
    
    @SerializedName("created_time")
    val createdTime: String? = null,
    
    @SerializedName("permissions")
    val permissions: PermissionsData? = null
)

data class PermissionsData(
    @SerializedName("can_cancel_refund")
    val canCancelRefund: Boolean? = null,

    @SerializedName("can_collect_payment")
    val canCollectPayment: Boolean? = null,

    @SerializedName("can_view_cashiers")
    val canViewCashiers: Boolean? = null,

    @SerializedName("can_add_edit_cashiers")
    val canAddEditCashiers: Boolean? = null,

    @SerializedName("can_delete_cashiers")
    val canDeleteCashiers: Boolean? = null,

    @SerializedName("can_view_products")
    val canViewProducts: Boolean? = null,

    @SerializedName("can_add_edit_products")
    val canAddEditProducts: Boolean? = null,

    @SerializedName("can_delete_products")
    val canDeleteProducts: Boolean? = null,

    @SerializedName("can_view_all_reports")
    val canViewAllReports: Boolean? = null,

    @SerializedName("can_save_send_reports")
    val canSaveSendReports: Boolean? = null,

    @SerializedName("can_manage_pos")
    val canManagePos: Boolean? = null,

    @SerializedName("is_admin")
    val isAdmin: Boolean? = null
)

