package com.truebilling.truechargecapture.drs.data.database.repository


import com.truebilling.truechargecapture.drs.data.database.providerfacility.CountryDao
import kotlinx.coroutines.flow.Flow
import nom.mvvm.structure.data.database.providerfacility.Country
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val countryDao: CountryDao,
) {
    fun getAllCountries(): Flow<List<Country>> = countryDao.getAllCountries()

    suspend fun insert(country: Country) = countryDao.insert(country)

    suspend fun insertAll(countries: List<Country>) = countryDao.insertAll(countries)
}