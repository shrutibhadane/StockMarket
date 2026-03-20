package com.assignments.stockmarket.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompanyDao {

    @Query("SELECT * FROM companies ORDER BY name ASC")
    suspend fun getAllCompanies(): List<CompanyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(companies: List<CompanyEntity>)

    @Query("DELETE FROM companies")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM companies")
    suspend fun getCount(): Int
}

