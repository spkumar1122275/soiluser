package com.campuscoders.posterminalapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "Department",
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["store_id"],
            childColumns = ["deptStoreId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["deptStoreId"])]
)
data class Department(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "dept_id") val deptId: Int,
    @ColumnInfo(name = "dept_name") val deptName: String?,
    @ColumnInfo(name = "dept_location") val deptLocation: String?,
    @ColumnInfo(name = "deptStoreId") val deptStoreId: Int,
    val mainUsers: List<MainUser>,
    val terminalUsers: List<TerminalUsers>
)
