package nom.mvvm.structure.utils.extensions.common

import android.content.Context
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import nom.mvvm.structure.utils.Result

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

suspend fun <T> Flow<Result<T>>.getData(block: ApiStatusObserver<T>.() -> Unit) {
    val builder = ApiStatusObserver<T>().apply(block)
    collectLatest {
        when (it.status) {
            Result.Status.SUCCESS -> {
                builder.onSuccess?.invoke(it.data!!)
            }
            Result.Status.ERROR -> {
                builder.onError?.invoke(it.message)
            }
        }
    }
}