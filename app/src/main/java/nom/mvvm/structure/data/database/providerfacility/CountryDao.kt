package com.truebilling.truechargecapture.drs.data.database.providerfacility

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nom.mvvm.structure.data.database.providerfacility.Country

@Dao
interface CountryDao {
    @Insert
    suspend fun insert(country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<Country>)

    @Query("SELECT * FROM country")
    fun getAllCountries(): Flow<List<Country>>
}