package com.campuscoders.posterminalapp.data.mapper

import com.campuscoders.posterminalapp.data.remote.dto.UserData
import com.campuscoders.posterminalapp.domain.model.AnyUser

// Domain -> Prefs Model
fun AnyUser.toUserData(): UserData {
    return when (this) {

        is AnyUser.MainUser -> UserData(
            terminalId = terminalId,
            taxId = taxId,
            memberStore = departmentId.toString(), // or null if you don't store this
            role = role,
            firstName = firstName,
            lastName = lastName,
            cellphoneNumber = cellphone,
            accessToken = null  // filled after login only
        )

        is AnyUser.TerminalUser -> UserData(
            terminalId = terminalId,
            taxId = taxId,
            memberStore = deptId?.toString(),
            role = role,
            firstName = firstName,
            lastName = lastName,
            cellphoneNumber = null,
            accessToken = null
        )
    }
}

// Prefs Model -> Domain
fun UserData.toAnyUser(): AnyUser {
    return if (role == "manager" || role == "admin" || role == "main") {
        AnyUser.MainUser(
            empNo = 0,                        // no data in prefs – keep default
            terminalId = terminalId ?: "",
            departmentId = memberStore?.toIntOrNull() ?: 0,
            taxId = taxId ?: "",
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            cellphone = cellphoneNumber,
            role = role ?: "",
            isAdmin = role == "admin"
        )
    } else {
        AnyUser.TerminalUser(
            empNo = null,
            terminalId = terminalId ?: "",
            deptId = memberStore?.toIntOrNull(),
            taxId = taxId,
            firstName = firstName,
            lastName = lastName,
            role = role
        )
    }
}
