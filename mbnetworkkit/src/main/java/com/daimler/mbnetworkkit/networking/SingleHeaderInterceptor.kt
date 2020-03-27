package com.daimler.mbnetworkkit.networking

import okhttp3.Interceptor
import okhttp3.Response

/**
 * An [Interceptor] that adds a single fixed header to the request.
 *
 * @param headerKey the key for the header
 * @param headerValue the value for the header
 */
class SingleHeaderInterceptor(
    private val headerKey: String,
    private val headerValue: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(headerKey, headerValue)
            .build()
        return chain.proceed(newRequest)
    }
}
