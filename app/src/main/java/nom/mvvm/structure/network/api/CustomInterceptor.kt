package nom.mvvm.structure.network.api

import android.content.Context
import nom.mvvm.structure.utils.NoConnectivityException
import nom.mvvm.structure.utils.Util
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomInterceptor @Inject constructor(@ApplicationContext private val context: Context) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Util.isNetworkAvailable(context)) {
            throw NoConnectivityException("Please check your internet connection")
        }

        val request = chain.request()
        val requestBuilder = request.newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .method(request.method, request.body)
            .build()

        return chain.proceed(requestBuilder)
    }
}
