package nom.mvvm.structure.network.repository

import com.truebilling.truechargecapture.drs.data.database.providerfacility.CountryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import nom.mvvm.structure.data.database.providerfacility.Country
import nom.mvvm.structure.network.api.ApiService
import nom.mvvm.structure.network.repository.NetworkHelper.getResponse
import nom.mvvm.structure.utils.Result
import javax.inject.Inject

class CountriesRepo @Inject constructor(
    private val countryDao: CountryDao,
    private val apiService: ApiService
) {
    suspend fun getAllCountries(): Flow<Result<List<Country>>> {
        return flow {
            val result = fetchCountriesFromApi()
            when (result.status) {
                Result.Status.SUCCESS -> {
                    saveCountriesToDatabase(result.data!!)
                    emit(Result.success(result.data))
                }

                Result.Status.ERROR -> {
                    emit(Result.error(result.message))
                }

            }
        }.flowOn(Dispatchers.IO)
    }


    private fun fetchCountriesFromDatabase(): Flow<Result<List<Country>>> {
        return countryDao.getAllCountries()
            .map { countries -> Result.success(countries) }
    }

    private suspend fun fetchCountriesFromApi(): Result<List<Country>> {
        return getResponse(
            request = { apiService.getPopularData() },
            defaultErrorMessage = "Error fetching countries data"
        )
    }

    private suspend fun saveCountriesToDatabase(countries: List<Country>) {
        countryDao.insertAll(countries)
    }

}
