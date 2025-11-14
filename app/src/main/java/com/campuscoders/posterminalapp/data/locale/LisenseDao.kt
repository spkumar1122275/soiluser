package com.campuscoders.posterminalapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.campuscoders.posterminalapp.domain.model.License

@Dao
interface LicenseDao {
    @Insert
    suspend fun insertLicense(license: License)

    @Query("SELECT * FROM Licenses WHERE store_id = :storeId")
    suspend fun getLicenses(storeId: Int): List<License>
}
