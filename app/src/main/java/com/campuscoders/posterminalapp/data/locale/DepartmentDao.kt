package com.campuscoders.posterminalapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.campuscoders.posterminalapp.domain.model.Department
import com.campuscoders.posterminalapp.domain.model.DepartmentWithTerminalUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(departments: List<Department>)

    @Transaction
    @Query("SELECT * FROM Department")
    fun getDepartmentsWithTerminalUsers(): List<DepartmentWithTerminalUsers>

    /**
     * Inserts a new department. If a department with the same ID already exists,
     * it will be replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDepartment(department: Department)

    /**
     * Inserts a list of departments.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDepartments(departments: List<Department>)

    /**
     * Updates an existing department.
     */
    @Update
    suspend fun updateDepartment(department: Department)

    /**
     * Fetches a single department by its ID.
     * Room will automatically use the TypeConverters to populate the lists.
     * Returns a Flow for reactive updates.
     */
    @Query("SELECT * FROM Department WHERE dept_id = :departmentId")
    fun getDepartmentById(departmentId: Int): Flow<Department?>

    /**
     * Fetches all departments from the database.
     * Returns a Flow for reactive updates.
     */
    @Query("SELECT * FROM Department")
    fun getAllDepartments(): Flow<List<Department>>

    /**
     * Deletes a department by its ID.
     */
    @Query("DELETE FROM Department WHERE dept_id = :departmentId")
    suspend fun deleteDepartmentById(departmentId: Int)

    /**
     * Deletes all departments from the table.
     */
    @Query("DELETE FROM Department")
    suspend fun deleteAllDepartments()

    @Query("DELETE FROM Department WHERE deptStoreId = :storeId")
    suspend fun deleteByStoreId(storeId: Int)
}




