package com.gsc.app.network.api

import com.gsc.app.network.model.response.countries.Country
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(ApiConstants.ALL)
    suspend fun getPopularData(): Response<List<Country>>
}
