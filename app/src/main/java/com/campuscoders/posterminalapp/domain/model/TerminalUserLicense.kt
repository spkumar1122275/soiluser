package com.campuscoders.posterminalapp.domain.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["terminalUserId", "licenseId"],
    indices = [Index(value = ["terminalUserId"])]
)
data class TerminalUserLicense(
    val terminalUserId: Int,
    val licenseId: Int,
    val licenseName: String
)
