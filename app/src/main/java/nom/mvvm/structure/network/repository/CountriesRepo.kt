package nom.mvvm.structure.network.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nom.mvvm.structure.data.database.country.Country
import nom.mvvm.structure.data.database.country.CountryDao
import nom.mvvm.structure.network.api.ApiService
import nom.mvvm.structure.network.connectivity.NetworkConnectivity
import nom.mvvm.structure.utils.Result
import nom.mvvm.structure.utils.extensions.common.executeRequest
import nom.mvvm.structure.utils.extensions.common.fetchData
import javax.inject.Inject

class CountriesRepo @Inject constructor(
    private val countryDao: CountryDao,
    private val apiService: ApiService,
    private val networkConnectivity: NetworkConnectivity
) {
    suspend fun getAllCountries() = fetchData(
        networkConnectivity,
        ::fetchCountriesFromApi,
        ::saveCountriesToDatabase,
        ::fetchCountriesFromDatabase
    )

    private fun fetchCountriesFromDatabase(): Flow<Result<List<Country>>> {
        return countryDao.getAllCountries()
            .map { countries -> Result.Success(countries) }
    }

    private suspend fun fetchCountriesFromApi(): Result<List<Country>> {
        return executeRequest(
            request = { apiService.getPopularData() },
            defaultErrorMessage = "Error fetching countries data"
        )
    }

    private suspend fun saveCountriesToDatabase(countries: List<Country>) {
        countryDao.insertAll(countries)
    }
}