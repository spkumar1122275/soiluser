package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.data.remote.dto.CompanyApiResponse
import com.campuscoders.posterminalapp.domain.model.Company
import com.campuscoders.posterminalapp.domain.model.License
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers

data class CompanyBundle(
    val company: Company,
    val licenses: List<License>,
    val mainUser: MainUser,
    val terminalUsers: List<TerminalUsers>
)

fun CompanyApiResponse.toBundle(): CompanyBundle {
    val companyEntity = company?.toEntity() ?: throw Exception("Company missing")
    return CompanyBundle(
        company = companyEntity,
        licenses = licenses?.map { it.toEntity(companyEntity.storeId) } ?: emptyList(),
        mainUser = mainUser?.toEntity() ?: throw Exception("Main user missing"),
        terminalUsers = terminalUsers?.map { it.toEntity() } ?: emptyList()
    )
}


