package com.daimler.mbnetworkkit.header

import okhttp3.Interceptor

interface HeaderService {

    var networkLocale: String
    var authMode: String?

    fun createRisHeaderInterceptor(): Interceptor

    fun createTokenHeaderInterceptor(): Interceptor

    @Deprecated("Use property networkLocale.")
    fun updateNetworkLocale(locale: String)

    @Deprecated("Use property authMode.")
    fun updateAuthMode(authMode: String?)

    @Deprecated("Use property networkLocale.")
    fun currentNetworkLocale(): String
}
