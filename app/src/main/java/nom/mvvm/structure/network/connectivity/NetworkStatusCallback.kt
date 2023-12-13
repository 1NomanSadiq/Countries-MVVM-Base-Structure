package nom.mvvm.structure.network.connectivity

import android.net.ConnectivityManager
import android.net.Network

class NetworkStatusCallback(
    private val onNetworkAvailable: () -> Unit,
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        onNetworkAvailable()
    }
}
