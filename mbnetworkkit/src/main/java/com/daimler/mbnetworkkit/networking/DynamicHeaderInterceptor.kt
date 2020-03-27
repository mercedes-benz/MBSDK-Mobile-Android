package com.daimler.mbnetworkkit.networking

import okhttp3.Interceptor
import okhttp3.Response

/**
 * An [Interceptor] that adds a multiple dynamic headers to a request.
 *
 * @param headerKey the key for the header
 * @param contentReceiver the [HeaderContentReceiver] for the header value
 */
class DynamicHeaderInterceptor(
    private val headerKey: String,
    private val contentReceiver: HeaderContentReceiver
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(headerKey, contentReceiver.invoke())
            .build()
        return chain.proceed(newRequest)
    }
}

/**
 * Type alias for the receiver function for header contents.
 */
typealias HeaderContentReceiver = () -> String
