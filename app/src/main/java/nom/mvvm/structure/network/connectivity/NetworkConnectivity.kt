package nom.mvvm.structure.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import nom.mvvm.structure.utils.extensions.common.connectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectivity @Inject constructor(private val context: Context) {
    fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        context.connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    fun unregisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        context.connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
