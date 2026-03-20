package com.assignments.stockmarket.db

import android.content.Context
import android.util.Log
import com.assignments.stockmarket.fetchCompaniesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository that handles companies data.
 * - Fetches from API only once and caches into Room.
 * - Subsequent reads come from Room.
 * - On logout, the database is cleared.
 */
object CompanyRepository {

    private const val TAG = "CompanyRepository"

    /**
     * Load companies from Room cache only.
     * Returns empty list if nothing is cached.
     */
    suspend fun getCompanies(context: Context): List<CompanyEntity> = withContext(Dispatchers.IO) {
        Log.i(TAG, "getCompanies() called")
        val dao = AppDatabase.getInstance(context).companyDao()
        val cachedCount = dao.getCount()
        Log.i(TAG, "Cached count in Room: $cachedCount")
        if (cachedCount > 0) {
            return@withContext dao.getAllCompanies()
        }
        emptyList()
    }

    /**
     * Save pre-parsed companies into Room and return the full list from DB.
     */
    suspend fun saveAndLoad(context: Context, companies: List<CompanyEntity>): List<CompanyEntity> = withContext(Dispatchers.IO) {
        Log.i(TAG, "saveAndLoad() — inserting ${companies.size} companies")
        val dao = AppDatabase.getInstance(context).companyDao()
        dao.insertAll(companies)
        val stored = dao.getAllCompanies()
        Log.i(TAG, "saveAndLoad() — Room now has ${stored.size} companies")
        stored
    }

    /**
     * Clear all company data from Room (called on logout).
     */
    suspend fun clearAll(context: Context) = withContext(Dispatchers.IO) {
        val dao = AppDatabase.getInstance(context).companyDao()
        dao.deleteAll()
        AppDatabase.clearInstance()
        Log.i(TAG, "Companies database cleared (logout)")
    }
}

