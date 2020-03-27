package com.daimler.mbnetworkkit.header

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class RisHeaderInterceptor(
    private val headerService: RisHeaderService,
    private val trackingIdProvider: TrackingIdProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .apply {
                val headerKeys = request.headers().names()
                addDefaultHeaders()
                addAuthModeHeaderIfExists()
                setOptionalHeadersIfNotExists(headerKeys)
            }
            .build()
        return chain.proceed(newRequest)
    }

    private fun Request.Builder.addDefaultHeaders() = apply {
        header(HEADER_APPLICATION_NAME, headerService.applicationName)
        header(HEADER_APPLICATION_VERSION, headerService.applicationVersion)
        header(HEADER_OS_NAME, headerService.osName)
        header(HEADER_OS_VERSION, headerService.osVersion)
        header(HEADER_SDK_VERSION, headerService.sdkVersion)
    }

    private fun Request.Builder.addAuthModeHeaderIfExists() = apply {
        headerService.authMode?.let { header(HEADER_AUTH_MODE, it) }
    }

    private fun Request.Builder.setOptionalHeadersIfNotExists(headers: Set<String>) = apply {
        setHeaderIfNotExists(
            headers,
            HEADER_LOCALE,
            headerService.networkLocale
        )
        headerService.sessionId?.let {
            setHeaderIfNotExists(
                headers,
                HEADER_SESSION_ID,
                it
            )
        }
        setHeaderIfNotExists(
            headers,
            HEADER_TRACKING_ID
        ) { trackingIdProvider.newTrackingId() }
    }

    private fun Request.Builder.setHeaderIfNotExists(
        headerKeys: Set<String>,
        name: String,
        valueProvider: () -> String
    ) = apply {
        if (!headerKeys.contains(name)) {
            header(name, valueProvider())
        }
    }

    private fun Request.Builder.setHeaderIfNotExists(
        headerKeys: Set<String>,
        name: String,
        value: String
    ): Request.Builder = apply {
        if (!headerKeys.contains(name)) {
            header(name, value)
        }
    }

    private companion object {
        private const val HEADER_APPLICATION_NAME = "X-ApplicationName"
        private const val HEADER_LOCALE = "X-Locale"
        private const val HEADER_AUTH_MODE = "X-AuthMode"
        private const val HEADER_APPLICATION_VERSION = "ris-application-version"
        private const val HEADER_OS_VERSION = "ris-os-version"
        private const val HEADER_OS_NAME = "ris-os-name"
        private const val HEADER_SDK_VERSION = "ris-sdk-version"
        private const val HEADER_SESSION_ID = "X-SessionId"
        private const val HEADER_TRACKING_ID = "X-TrackingId"
    }
}
