package com.campuscoders.posterminalapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.campuscoders.posterminalapp.domain.model.Customers

@Dao
interface CustomersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customers): Long

    @Query("SELECT * FROM Customers WHERE customer_vkn_tckn = :customertaxId")
    suspend fun queryCustomerBytaxId(customertaxId: String): Customers?

    @Query("SELECT * FROM Customers")
    suspend fun queryAllCustomers(): List<Customers>?

    @Update
    suspend fun updateCustomer(customer: Customers): Int
}