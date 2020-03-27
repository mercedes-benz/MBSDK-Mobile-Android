package com.daimler.mbnetworkkit.header

import okhttp3.Interceptor

internal class MemoryHeaderService(
    override val applicationName: String,
    override val applicationVersion: String,
    override val sdkVersion: String,
    override val osName: String,
    override val osVersion: String,
    override val sessionId: String?,
    override var networkLocale: String,
    override var authMode: String?,
    private val trackingIdProvider: TrackingIdProvider
) : RisHeaderService {

    override fun updateNetworkLocale(locale: String) {
        this.networkLocale = locale
    }

    override fun updateAuthMode(authMode: String?) {
        this.authMode = authMode
    }

    override fun currentNetworkLocale(): String = networkLocale

    override fun createRisHeaderInterceptor(): Interceptor =
        RisHeaderInterceptor(
            this,
            trackingIdProvider
        )

    override fun createTokenHeaderInterceptor(): Interceptor = TokenHeaderInterceptor()
}
