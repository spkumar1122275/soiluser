package com.campuscoders.posterminalapp.domain.model

sealed class AnylocalUser {
    data class MainUser(
        val empNo: Int,
        val terminalId: String,
        val departmentId: Int,
        val taxId: String,
        val firstName: String,
        val lastName: String,
        val cellphone: String?,
        val role: String,
        val isAdmin: Boolean
    ): AnylocalUser()

    data class TerminalUser(
        val empNo: Int?,
        val terminalId: String,
        val deptId: Int?,
        val taxId: String?,
        val firstName: String?,
        val lastName: String?,
        val role: String?
    ): AnylocalUser()
}
