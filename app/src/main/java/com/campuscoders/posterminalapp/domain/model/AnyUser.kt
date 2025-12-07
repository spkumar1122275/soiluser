package com.campuscoders.posterminalapp.domain.model

sealed class AnyUser {
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
    ): AnyUser()

    data class TerminalUser(
        val empNo: Int?,
        val terminalId: String,
        val deptId: Int?,
        val taxId: String?,
        val firstName: String?,
        val lastName: String?,
        val role: String?
    ): AnyUser()
}
