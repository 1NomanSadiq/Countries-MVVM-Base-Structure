package nom.mvvm.structure.network.api

import nom.mvvm.structure.data.database.country.Country
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(ApiConstants.ALL)
    suspend fun getPopularData(): Response<List<Country>>
}
