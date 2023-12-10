package nom.mvvm.structure.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nom.mvvm.structure.network.api.ApiService
import nom.mvvm.structure.network.model.response.countries.Country
import nom.mvvm.structure.network.repository.NetworkHelper.getResponse
import nom.mvvm.structure.utils.Result
import javax.inject.Inject

class CountriesRepo @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllCountries(): Flow<Result<List<Country>>> {
        return flow {
            val result = getResponse(
                request = { apiService.getPopularData() },
                defaultErrorMessage = "Error fetching countries data"
            )
            emit(
                when (result.status) {
                    Result.Status.SUCCESS -> {
                        /* cache data here using */ result.data!! // in success case it's not null so it's safe to use !!
                        Result.success(result.data)
                    }

                    Result.Status.ERROR -> Result.error(result.message)
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}
