package com.assignments.stockmarket.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
data class CompanyEntity(
    @PrimaryKey
    val symbol: String,
    val name: String,
    val logo: String
)

