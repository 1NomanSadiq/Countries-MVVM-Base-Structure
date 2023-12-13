package nom.mvvm.structure.utils.extensions.common

import android.content.Context
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import nom.mvvm.structure.network.connectivity.NetworkConnectivity
import nom.mvvm.structure.network.connectivity.NetworkStatusCallback
import nom.mvvm.structure.network.model.Error
import nom.mvvm.structure.utils.Result
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean

fun Context.isNetworkConnected(): Boolean {
    return connectivityManager.run {
        getNetworkCapabilities(activeNetwork)
            ?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) == true
    }
}


class ApiStatusObserver<T> {
    var onSuccess: (suspend (T) -> Unit)? = null
    var onError: (suspend (String) -> Unit)? = null
}

suspend fun <T> Flow<Result<T>>.observeData(block: ApiStatusObserver<T>.() -> Unit) {
    val observer = ApiStatusObserver<T>().apply(block)
    collectLatest { result ->
        when (result) {
            is Result.Success -> observer.onSuccess?.invoke(result.data)
            is Result.Error -> observer.onError?.invoke(result.message)
            is Result.Loading -> {} // Handle loading state if needed
        }
    }
}

suspend fun <T> executeRequest(
    request: suspend () -> Response<T>,
    defaultErrorMessage: String
): Result<T> {
    return try {
        val response = request()
        if (response.isSuccessful) {
            response.body()?.let { Result.Success(it) } ?: Result.Error("Response body is null")
        } else {
            val errorResponse = Error(response.code(), response.message())
            Result.Error("${errorResponse.status_code} ".trim() + errorResponse.status_message.ifNullOrBlank { defaultErrorMessage })
        }
    } catch (e: Throwable) {
        Result.Error(e.message ?: defaultErrorMessage)
    }
}

suspend fun <T> ProducerScope<Result<T>>.fetchFromApiAndSave(
    apiCall: suspend () -> Result<T>,
    saveToDatabase: (suspend (T) -> Unit)? = null,
    apiCalled: AtomicBoolean
) {
    if (!apiCalled.get()) {
        when (val result = apiCall()) {
            is Result.Success -> {
                apiCalled.set(true)
                saveToDatabase?.invoke(result.data)
                if (result.data is List<*> && result.data.isEmpty()) send(Result.Success(result.data))
                else if (saveToDatabase == null) send(result)
            }
            is Result.Error -> send(Result.Error(result.message))
            is Result.Loading -> {} // Handle loading state if needed
        }
    }
}

suspend fun <T> fetchData(
    networkConnectivity: NetworkConnectivity,
    fetchFromApi: suspend () -> Result<T>,
    saveToDatabase: (suspend (T) -> Unit)? = null,
    fetchFromDatabase: (suspend () -> Flow<Result<T>>)? = null
): Flow<Result<T>> {
    val apiCalled = AtomicBoolean(false)
    return channelFlow {
        val networkCallback = NetworkStatusCallback(
            onNetworkAvailable = {
                launch {
                    fetchFromApiAndSave(
                        fetchFromApi,
                        saveToDatabase,
                        apiCalled
                    )
                }
            }
        )

        networkConnectivity.registerNetworkCallback(networkCallback)

        launch {
            fetchFromDatabase?.invoke()?.collectLatest { result ->
                if (result is Result.Success
                    && result.data is List<*>
                    && result.data.isNotEmpty())
                    send(result)
            }
        }

        awaitClose {
            networkConnectivity.unregisterNetworkCallback(networkCallback)
        }
    }.flowOn(Dispatchers.IO)
}