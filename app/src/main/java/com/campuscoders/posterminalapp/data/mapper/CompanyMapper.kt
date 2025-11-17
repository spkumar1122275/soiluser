package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.data.remote.dto.ApiCompany
import com.campuscoders.posterminalapp.data.remote.dto.ApiLicense
import com.campuscoders.posterminalapp.data.remote.dto.ApiMainUser
import com.campuscoders.posterminalapp.data.remote.dto.ApiTerminalUser
import com.campuscoders.posterminalapp.domain.model.Company
import com.campuscoders.posterminalapp.domain.model.License
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers


fun ApiCompany.toEntity(): Company {
    return Company(
        storeId = store_id,
        companyName = name,
        address = address,
        phone = phone,
        taxId = tax_id
    )
}

fun ApiLicense.toEntity(storeId: Int): License {
    return License(
        storeId = storeId,
        licenseName = license_name,
        licenseRefNo = license_ref_no,
        validTill = valid_till
    )
}

fun List<ApiLicense>.toEntityList(storeId: Int) =
    map { it.toEntity(storeId) }

fun ApiMainUser.toEntity(): MainUser {
    return MainUser(
        mainUserTerminalId = terminal_id,
        mainUserTaxId = pan,
        mainUserStoreId = store_id,
        mainUserPassword = password,
        mainUserCellphoneNumber = cellphone_number,
        mainUserFirstName = first_name,
        mainUserLastName = last_name,
        role = role,
        empNo = emp_no,
        departmentName = department_name,
        departmentLocation = department_location
    )
}


fun ApiTerminalUser.toEntity(): TerminalUsers {
    return TerminalUsers (
        terminalUserTerminalId = terminal_id,
        terminalUsertaxId = pan,
        terminalUserStoreId = store_id,
        terminalUserPassword = password,
        terminalUserphoneNumber = cellphone_number,
        terminalUserfirstName = first_name,
        terminalUserlastName = last_name,
        terminalUserrole = role,
        terminalUserempNo = emp_no,
        terminalUserdepartmentName = department_name,
        terminalUserdepartmentLocation = department_location,

        // Permissions
        canCancelRefund = permissions.can_cancel_refund,
        canCollectPayment = permissions.can_collect_payment,
        canViewCashiers = permissions.can_view_cashiers,
        canAddEditCashiers = permissions.can_add_edit_cashiers,
        canDeleteCashiers = permissions.can_delete_cashiers,
        canViewProducts = permissions.can_view_products,
        canAddEditProducts = permissions.can_add_edit_products,
        canDeleteProducts = permissions.can_delete_products,
        canViewAllReports = permissions.can_view_all_reports,
        canSaveSendReports = permissions.can_save_send_reports,
        canManagePos = permissions.can_manage_pos,
        terminalUserAdmin = permissions.is_admin
    )
}

fun List<ApiTerminalUser>.toEntityList() =
    map { it.toEntity() }


