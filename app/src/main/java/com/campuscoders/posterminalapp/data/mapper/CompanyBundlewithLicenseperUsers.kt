package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.data.remote.dto.CompanyApiResponse
import com.campuscoders.posterminalapp.domain.model.*

data class TerminalUserWithLicenses(
    val terminalUser: TerminalUsers,
    val licenses: List<License>
)

data class CompanyBundleWithLicensesPerUser(
    val company: Company,
    val licenses: List<License>,
    val departments: List<Department>,
    val mainUsers: List<MainUser>,
    val terminalUsersWithLicenses: List<TerminalUserWithLicenses>
)
fun CompanyApiResponse.toBundleWithLicensesPerUser(): CompanyBundleWithLicensesPerUser {
    val apiCompany = company ?: throw Exception("Company missing")

    // Base entities
    val companyEntity = apiCompany.toCompanyEntity()
    val licenseEntities = apiCompany.toLicenseEntities()
    val departmentEntities = apiCompany.toDepartmentEntities()
    val mainUserEntities = apiCompany.toMainUserEntities()
    val terminalUserEntities = apiCompany.toTerminalUserEntities()

    // Join-table: TerminalUserLicense list
    val joinEntities = apiCompany.toTerminalUserLicenseEntities()

    // Map LicenseId → License object (fast lookup)
    val licenseMap = licenseEntities.associateBy { it.licenseId }

    // Build "TerminalUserWithLicenses"
    val terminalUsersWithLicenses = terminalUserEntities.map { terminalUser ->

        // Get licenses assigned to this terminalUser
        val userLicenses = joinEntities
            .filter { it.terminalUserId == terminalUser.terminalUserempNo }
            .mapNotNull { licenseMap[it.licenseId] }

        TerminalUserWithLicenses(
            terminalUser = terminalUser,
            licenses = userLicenses
        )
    }

    return CompanyBundleWithLicensesPerUser(
        company = companyEntity,
        licenses = licenseEntities,
        departments = departmentEntities,
        mainUsers = mainUserEntities,
        terminalUsersWithLicenses = terminalUsersWithLicenses
    )
}

//usage at below
//TerminalUserWithLicenses(
//terminalUser = TerminalUsers(...),
//licenses = listOf(License, License, ...)
//)
//suspend fun fetchCompanyBundle(): CompanyBundleWithLicensesPerUser

