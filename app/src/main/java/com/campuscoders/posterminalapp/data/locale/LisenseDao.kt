package com.campuscoders.posterminalapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.campuscoders.posterminalapp.domain.model.License

@Dao
interface LicenseDao {
    @Insert
    suspend fun insertLicense(license: License)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(licenses: List<License>)


    @Query("DELETE FROM Licenses")
    suspend fun deleteAll()

    @Query("DELETE FROM Licenses WHERE store_id = :storeId")
    suspend fun deleteByStoreId(storeId: Int)



    @Query("SELECT * FROM Licenses WHERE store_id = :storeId")
    suspend fun getLicenses(storeId: Int): List<License>
}
