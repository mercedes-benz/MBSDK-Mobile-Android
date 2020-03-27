package com.daimler.mbnetworkkit.header

import okhttp3.Interceptor
import okhttp3.Response

internal class TokenHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request.headers(HEADER_AUTHORIZATION).firstOrNull { it.isNotEmpty() }?.let {
            if (!it.contains(BEARER)) {
                request = request.newBuilder()
                    .removeHeader(HEADER_AUTHORIZATION)
                    .addHeader(HEADER_AUTHORIZATION, "$BEARER $it")
                    .build()
            }
        }
        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
