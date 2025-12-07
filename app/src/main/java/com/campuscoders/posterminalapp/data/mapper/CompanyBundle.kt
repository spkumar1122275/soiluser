package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.data.remote.dto.CompanyApiResponse
import com.campuscoders.posterminalapp.domain.model.*

data class CompanyBundle(
    val company: Company,
    val licenses: List<License>,
    val departments: List<Department>,
    val mainUsers: List<MainUser>,
    val terminalUsers: List<TerminalUsers>,
    val terminalUserLicenses: List<TerminalUserLicense>
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

    // Flattened Terminal User Licenses (from all departments/terminal users)
    val terminalUserLicenseEntities = apiCompany.toTerminalUserLicenseEntities()

    return CompanyBundle(
        company = companyEntity,
        licenses = licenseEntities,
        departments = departmentEntities,
        mainUsers = mainUserEntities,
        terminalUsers = terminalUserEntities,
        terminalUserLicenses = terminalUserLicenseEntities
    )
}
