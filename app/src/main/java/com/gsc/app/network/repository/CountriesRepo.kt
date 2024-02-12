package com.gsc.app.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.gsc.app.network.api.ApiService
import com.gsc.app.network.model.response.countries.Country
import com.gsc.app.network.repository.NetworkHelper.getResponse
import com.gsc.app.utils.Result
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
                        result.data!! // in success case it's not null so it's safe to use !!
                        Result.success(result.data)
                    }

                    Result.Status.ERROR -> Result.error(result.message)
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}
