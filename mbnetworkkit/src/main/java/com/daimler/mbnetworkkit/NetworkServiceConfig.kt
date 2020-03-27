package com.daimler.mbnetworkkit

import android.os.Build
import java.util.Locale

class NetworkServiceConfig private constructor(
    val applicationName: String,
    val applicationVersion: String,
    val sdkVersion: String,
    val osName: String,
    val osVersion: String,
    val authMode: String?,
    val locale: String,
    val sessionId: String?
) {

    class Builder(
        /**
         * Custom app identifier.
         */
        private val applicationName: String,
        /**
         * version String of your app
         */
        private val applicationVersion: String,
        /**
         * version of MBMobileSDK
         */
        private val sdkVersion: String
    ) {

        private var osVersion: String? = null
        private var locale: String? = null
        private var authMode: String? = null
        private var sessionId: String? = null

        /**
         * Sets a specific operating system version that is passed as a header with network requests.
         */
        fun useOSVersion(osVersion: String) = apply {
            this.osVersion = osVersion
        }

        /**
         * Sets a specific locale to be used by the network service
         */
        fun useLocale(locale: String) = apply {
            this.locale = locale
        }

        /**
         * Configures an authMode. (KEYCLOAK or CIAM)
         */
        fun useAuthMode(authMode: String?) = apply {
            this.authMode = authMode
        }

        /**
         * Sets a Session-ID to be included as a header in network requests
         */
        fun useSessionId(sessionId: String) = apply {
            this.sessionId = sessionId
        }

        fun build(): NetworkServiceConfig =
            NetworkServiceConfig(
                applicationName,
                applicationVersion,
                sdkVersion,
                OS_NAME,
                osVersion ?: Build.VERSION.RELEASE,
                authMode,
                locale ?: defaultLocale(),
                sessionId
            )

        private fun defaultLocale(): String {
            val locale = Locale.getDefault()
            return "${locale.language}-${locale.country}"
        }

        private companion object {
            private const val OS_NAME = "android"
        }
    }
}
