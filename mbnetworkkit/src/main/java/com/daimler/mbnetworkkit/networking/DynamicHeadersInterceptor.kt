package com.daimler.mbnetworkkit.networking

import okhttp3.Interceptor
import okhttp3.Response

/**
 * An [Interceptor] that adds a multiple dynamic headers to a request.
 *
 * @param headers a map containing the header keys as keys and a [HeaderContentReceiver] as values
 */
class DynamicHeadersInterceptor(
    private val headers: Map<String, HeaderContentReceiver>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .apply { headers.forEach { addHeader(it.key, it.value.invoke()) } }
            .build()
        return chain.proceed(newRequest)
    }
}
