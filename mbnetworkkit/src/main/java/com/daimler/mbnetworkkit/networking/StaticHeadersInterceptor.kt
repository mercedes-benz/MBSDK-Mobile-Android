package com.daimler.mbnetworkkit.networking

import okhttp3.Interceptor
import okhttp3.Response

/**
 * An [Interceptor] that adds a multiple fixed headers to the request.
 *
 * @param headers a map containing that header keys as keys and the header content as values
 */
class StaticHeadersInterceptor(
    private val headers: Map<String, String>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .apply { headers.forEach { addHeader(it.key, it.value) } }
            .build()
        return chain.proceed(newRequest)
    }
}
