package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.domain.model.AnylocalUser
import com.campuscoders.posterminalapp.domain.model.MainUser as MainUserEntity
import com.campuscoders.posterminalapp.domain.model.TerminalUsers as TerminalUserEntity

fun MainUserEntity.toDomainUser(): AnylocalUser.MainUser {
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

fun TerminalUserEntity.toDomainUser(): AnylocalUser.TerminalUser {
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
