package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.data.remote.dto.ApiCompany
import com.campuscoders.posterminalapp.data.remote.dto.ApiDepartment
import com.campuscoders.posterminalapp.data.remote.dto.ApiLicense
import com.campuscoders.posterminalapp.data.remote.dto.ApiMainUser
import com.campuscoders.posterminalapp.data.remote.dto.ApiTerminalUser
import com.campuscoders.posterminalapp.domain.model.Company
import com.campuscoders.posterminalapp.domain.model.Department
import com.campuscoders.posterminalapp.domain.model.License
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUserLicense
import com.campuscoders.posterminalapp.domain.model.TerminalUsers


/* -------------------------------------------------------
   COMPANY
---------------------------------------------------------- */

fun ApiCompany.toCompanyEntity(): Company {
    return Company(
        companyId = id,
        companyName = name ?: "",
        address = address ?: "",
        phone = phone ?: "",
        taxId = tax_id ?: "",
        storeId = store_id
    )
}

/* -------------------------------------------------------
   LICENSE
---------------------------------------------------------- */

fun ApiCompany.toLicenseEntities(): List<License> =
    licenses?.map { it.toLicenseEntity(store_id) } ?: emptyList()

fun ApiLicense.toLicenseEntity(storeId: Int): License {
    return License(
        licenseId = license_id,
        licenseName = license_name ?: "",
        licenseRefNo = license_ref_no ?: "",
        storeId = storeId,
        validTill = valid_till
    )
}

/* -------------------------------------------------------
   DEPARTMENTS
---------------------------------------------------------- */

fun ApiCompany.toDepartmentEntities(): List<Department> {
    return departments?.map { it.toDepartmentEntity(store_id) } ?: emptyList()
}

fun ApiDepartment.toDepartmentEntity(storeId: Int): Department {
    return Department(
        deptId = dept_id,
        deptName = dept_name,
        deptLocation = dept_location,
        deptStoreId = storeId,
        mainUsers = emptyList(),
        terminalUsers = emptyList()
    )
}


/* -------------------------------------------------------
   MAIN USERS
---------------------------------------------------------- */

fun ApiCompany.toMainUserEntities(): List<MainUser> {
    return departments?.flatMap { dept ->
        dept.mainUsers?.map { it.toMainUserEntity(dept.dept_id, store_id) } ?: emptyList()
    } ?: emptyList()
}

fun ApiMainUser.toMainUserEntity(departmentId: Int, storeId: Int): MainUser {
    return MainUser(
        mainUserEmpNo = emp_no ?: 0,
        mainUserTerminalId = terminal_id ?: "",
        mainUserDepartmentId = departmentId,
        mainUserStoreId = storeId.toString(),
        mainUserTaxId = pan ?: "",
        mainUserPassword = password ?: "",
        mainUserFirstName = first_name ?: "",
        mainUserLastName = last_name ?: "",
        mainUserCellphoneNumber = cellphone_number ?: "",
        mainUserRole = role ?: "",
        mainUserIsAdmin = is_admin ?: false,
        mainUserIsActive = null
    )
}

/* -------------------------------------------------------
   TERMINAL USERS
---------------------------------------------------------- */

fun ApiCompany.toTerminalUserEntities(): List<TerminalUsers> {
    return departments?.flatMap { dept ->
        dept.terminalUsers?.map { it.toTerminalUserEntity(dept.dept_id, store_id) } ?: emptyList()
    } ?: emptyList()
}

fun ApiTerminalUser.toTerminalUserEntity(departmentId: Int, storeId: Int): TerminalUsers {
    return TerminalUsers(
        terminalUserempNo = emp_no,
        terminalUserDeptId = departmentId,
        terminalUserStoreId = storeId.toString(),
        terminalUserTerminalId = terminal_id,
        terminalUsertaxId = pan,
        terminalUserPassword = password,
        terminalUserfirstName = first_name,
        terminalUserlastName = last_name,
        terminalUserphoneNumber = cellphone_number,
        terminalUserrole = role,
        terminalUserFullName = "${first_name ?: ""} ${last_name ?: ""}",
        canCancelRefund = null,
        canCollectPayment = null,
        canViewCashiers = null,
        canAddEditCashiers = null,
        canDeleteCashiers = null,
        canViewProducts = null,
        canAddEditProducts = null,
        canDeleteProducts = null,
        canViewAllReports = null,
        canSaveSendReports = null,
        canManagePos = null,
        terminalUserAdmin = null,
        terminalUserDate = null,
        terminalUserTime = null
    )
}

/* -------------------------------------------------------
   TERMINAL USER LICENSE JOIN TABLE
---------------------------------------------------------- */

fun ApiCompany.toTerminalUserLicenseEntities(): List<TerminalUserLicense> {
    return departments?.flatMap { dept ->
        dept.terminalUsers?.flatMap { terminalUser ->
            terminalUser.licenses?.map { license ->
                TerminalUserLicense(
                    terminalUserId = terminalUser.emp_no ?: 0,
                    licenseId = license.license_id,
                    licenseName = license.license_name ?: ""
                )
            } ?: emptyList()
        } ?: emptyList()
    } ?: emptyList()
}
