package com.campuscoders.posterminalapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Licenses",
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["store_id"],
            childColumns = ["store_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["store_id"])]
)
data class License(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "license_id") val licenseId: Int,     // NEW from API

    @ColumnInfo(name = "store_id") val storeId: Int?,
    @ColumnInfo(name = "license_name") val licenseName: String?,
    @ColumnInfo(name = "license_ref_no") val licenseRefNo: String?,
    @ColumnInfo(name = "valid_till") val validTill: String?
)
