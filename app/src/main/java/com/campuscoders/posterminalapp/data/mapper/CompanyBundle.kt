package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.data.remote.dto.CompanyApiResponse
import com.campuscoders.posterminalapp.domain.model.Company
import com.campuscoders.posterminalapp.domain.model.Department
import com.campuscoders.posterminalapp.domain.model.License
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers

data class CompanyBundle(
    val company: Company,
    val licenses: List<License>,
    val departments: List<Department>,
    val mainUsers: List<MainUser>,
    val terminalUsers: List<TerminalUsers>
)

fun CompanyApiResponse.toBundle(): CompanyBundle {
    val apiCompany = company ?: throw Exception("Company missing")

    // Company
    val companyEntity = apiCompany.toCompanyEntity()

    // Licenses
    val licenseEntities = apiCompany.toLicenseEntities()

    // Departments
    val departmentEntities = apiCompany.toDepartmentEntities()

    // Flattened Main Users (from all departments)
    val mainUserEntities = apiCompany.toMainUserEntities()

    // Flattened Terminal Users (from all departments)
    val terminalUserEntities = apiCompany.toTerminalUserEntities()

    return CompanyBundle(
        company = companyEntity,
        licenses = licenseEntities,
        departments = departmentEntities,
        mainUsers = mainUserEntities,
        terminalUsers = terminalUserEntities
    )
}
