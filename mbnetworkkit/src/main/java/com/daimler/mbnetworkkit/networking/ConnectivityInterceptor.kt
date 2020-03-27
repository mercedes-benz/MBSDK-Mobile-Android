package com.daimler.mbnetworkkit.networking

import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val connectivityManager: ConnectivityManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (isConnectedOrConnecting(connectivityManager.activeNetworkInfo).not()) {
            throw NotConnectedException("Not connected or connecting to network")
        }
        return chain.proceed(chain.request())
    }

    private fun isConnectedOrConnecting(networkInfo: NetworkInfo?): Boolean {
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    class NotConnectedException(message: String) : IOException(message)
}
