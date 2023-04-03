package com.example.flickhunt.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

/**
 *
 * Monitors network changes and triggers connectivity status using live data.
 */
class ConnectionLiveStatus(context: Context) : LiveData<Boolean>() {

    private val TAG = "connection_manager"
    private lateinit var networkCallback: NetworkCallback
    private var networkAvailable = false

    private val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks : MutableSet<Network> = HashSet()

    private fun checkValidNetworks(){
        postValue(validNetworks.size > 0)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback(){

        override fun onAvailable(network: Network) {

            networkAvailable = true
            Log.d(TAG , "onAvailable $network")
            Log.d(TAG , "validNetwork Size ${validNetworks.size}")

            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val isInternet = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)

            if (isInternet == true) validNetworks.add(network)
            checkValidNetworks()
        }

        override fun onLost(network: Network) {
            Log.d(TAG , "onLost $network")
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }

    override fun onActive() {

        if (!networkAvailable) checkValidNetworks() //variable used to check if the network connectivity is on during the startup

        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest , networkCallback)
        Log.d(TAG , "validNetwork Size in on Active${validNetworks.size}")
    }

    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}