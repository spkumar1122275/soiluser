package com.campuscoders.posterminalapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.campuscoders.posterminalapp.domain.model.Company

@Dao
interface CompanyDao {

    @Insert
    suspend fun insertCompany(company: Company)

    @Query("SELECT * FROM Company WHERE store_id = :storeId")
    suspend fun getCompany(storeId: Int): Company?


    @Query("SELECT * FROM Company WHERE store_id = :storeId LIMIT 1")
    suspend fun getCompanyByStoreId(storeId: Int): Company?

    @Query("DELETE FROM Company")
    suspend fun deleteAll()

    @Query("DELETE FROM Company WHERE store_id = :storeId")
    suspend fun deleteByStoreId(storeId: Int)
}
