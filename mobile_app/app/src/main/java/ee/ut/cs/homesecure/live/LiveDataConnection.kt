package ee.ut.cs.homesecure.live

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class LiveDataConnection(context: Context) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val connectionManager =
        context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val availableNetworks: MutableSet<Network> = HashSet()

    private fun networkCallback() = object : ConnectivityManager.NetworkCallback() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onAvailable(network: Network) {
            val netCapability = connectionManager.getNetworkCapabilities(network)
            val isInternetCapable = netCapability?.hasCapability(NET_CAPABILITY_INTERNET)
            Log.d(TAG, "**** onAvailable network : $network - netCapability: $isInternetCapable")
            if (isInternetCapable == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    val hasNetwork = PingNetwork.execute(network.socketFactory)
                    if (hasNetwork) {
                        withContext(Dispatchers.Main) {
                            Log.d(TAG, "adding available network. $network")
                            availableNetworks.add(network)
                            postValue(availableNetworks.size > 0)
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "***** onLost network: $network")
            availableNetworks.remove(network)
            postValue(availableNetworks.size > 0)
        }
    }

    override fun onActive() {
        networkCallback = networkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        connectionManager.registerNetworkCallback(networkRequest, networkCallback)
    }


    override fun onInactive() {
        connectionManager.unregisterNetworkCallback(networkCallback)
    }

    object PingNetwork {
        fun execute(socketFactory: SocketFactory): Boolean {
            return try {
                Log.d(TAG, "PINGING google.")
                val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                socket.close()
                Log.d(TAG, "PING successful.")
                true
            } catch (e: IOException) {
                Log.d(TAG, "No internet connection. ${e.stackTrace}")
                false
            }
        }
    }
}
