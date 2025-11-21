package com.campuscoders.posterminalapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "TerminalUserLicenses",
    foreignKeys = [
        ForeignKey(
            entity = TerminalUsers::class,
            parentColumns = ["terminalUserId"],
            childColumns = ["terminal_user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TerminalUserLicense(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "terminal_user_id") val terminalUserId: Int,

    @ColumnInfo(name = "license_id") val licenseId: Int,
    @ColumnInfo(name = "license_name") val licenseName: String
)
