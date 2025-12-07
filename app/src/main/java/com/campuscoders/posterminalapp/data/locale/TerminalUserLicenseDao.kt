package com.campuscoders.posterminalapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.campuscoders.posterminalapp.domain.model.TerminalUserLicense

@Dao
interface TerminalUserLicenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(licenses: List<TerminalUserLicense>)
}
