package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.data.remote.dto.ApiUser
import com.campuscoders.posterminalapp.domain.model.AnyUser

fun ApiUser.toDomainUser(): AnyUser {
    return if (this.role.equals("main_user", ignoreCase = true)) {

        AnyUser.MainUser(
            empNo = 0,                 // API does NOT send emp_no
            terminalId = this.terminal_id,
            departmentId = 0,         // Not provided
            taxId = this.tax_id,
            firstName = this.first_name,
            lastName = this.last_name,
            cellphone = this.cellphone_number,
            role = this.role,
            isAdmin = false              // API does NOT send is_admin
        )

    } else {

        AnyUser.TerminalUser(
            empNo = 0,               // API does NOT send emp_no
            terminalId = this.terminal_id,
            deptId = 0,
            taxId = this.tax_id,
            firstName = this.first_name,
            lastName = this.last_name,
            role = this.role
        )
    }
}

