package com.campuscoders.posterminalapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Company")
data class Company(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "store_id") val storeId: Int,

    @ColumnInfo(name = "company_name") val companyName: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "tax_id") val taxId: String?
)

