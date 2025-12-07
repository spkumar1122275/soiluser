package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.domain.model.AnylocalUser
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers

// Convert MainUser → AnylocalUser.MainUser
fun MainUser.toAnylocalUser(): AnylocalUser.MainUser {
    return AnylocalUser.MainUser(
        empNo = this.mainUserEmpNo ?: 0,
        terminalId = this.mainUserTerminalId ?: "",
        departmentId = this.mainUserDepartmentId ?: 0,
        taxId = this.mainUserTaxId ?: "",
        firstName = this.mainUserFirstName ?: "",
        lastName = this.mainUserLastName ?: "",
        cellphone = this.mainUserCellphoneNumber,
        role = this.mainUserRole ?: "",
        isAdmin = this.mainUserIsAdmin ?: false
    )
}

// Convert TerminalUsers → AnylocalUser.TerminalUser
fun TerminalUsers.toAnylocalUser(): AnylocalUser.TerminalUser {
    return AnylocalUser.TerminalUser(
        empNo = this.terminalUserempNo,
        terminalId = this.terminalUserTerminalId ?: "",
        deptId = this.terminalUserDeptId,
        taxId = this.terminalUsertaxId,
        firstName = this.terminalUserfirstName,
        lastName = this.terminalUserlastName,
        role = this.terminalUserrole
    )
}
