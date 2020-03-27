package com.daimler.mbmobilesdk.configuration

import android.app.Application
import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.socket.PinCommandVehicleApiStatusCallback
import com.daimler.mbingresskit.common.authentication.AuthenticationConfiguration
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.login.SessionExpiredHandler
import com.daimler.mbmobilesdk.BuildConfig
import com.daimler.mbmobilesdk.implementation.DefaultPinCommandVehicleApiStatusCallback
import com.daimler.mbmobilesdk.implementation.IngressTokenProvider
import com.daimler.mbmobilesdk.implementation.StageBasedEndpointUrlProvider
import com.daimler.mbnetworkkit.certificatepinning.CertificateConfiguration
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningErrorProcessor
import com.daimler.mbnetworkkit.common.TokenProvider
import java.lang.IllegalArgumentException

class MBMobileSDKConfiguration private constructor(
    val application: Application,
    val expiredHandler: SessionExpiredHandler?,
    val appIdentifier: String,
    val preferredAuthenticationType: AuthenticationType,
    val sharedUserId: String,
    val urlProvider: EndpointUrlProvider,
    val pinProvider: PinProvider?,
    val ingressKeyStoreAlias: String,
    val reconnectConfig: Pair<Int, Int>?,
    val tokenProvider: TokenProvider,
    val pinCommandVehicleApiStatusCallback: PinCommandVehicleApiStatusCallback,
    val mobileSdkVersion: String,
    val certificateConfiguration: List<CertificateConfiguration>?,
    val errorProcessor: CertificatePinningErrorProcessor?,
    val deviceId: String?,
    val authenticationConfigs: List<AuthenticationConfiguration>,
    val logHttpBody: Boolean
) {

    class Builder(
        /**
         * Android application.
         */
        private val application: Application,
        /**
         * Application identifier.
         */
        private var appIdentifier: String,
        /**
         * Key store alias for authentication module
         */
        private var ingressKeyStoreAlias: String,
        /**
         * Your preferred authentication method.
         * One of [AuthenticationType.KEYCLOAK] (this is the default value) or [AuthenticationType.CIAM]
         */
        private var preferredAuthenticationType: AuthenticationType = AuthenticationType.KEYCLOAK,
        /**
         *  List of configurations for both [AuthenticationType.KEYCLOAK] and [AuthenticationType.CIAM]
         */
        private var authenticationConfigurations: List<AuthenticationConfiguration>
    ) {
        // optional / default initialized parameter
        private var sharedUserId: String = ""
        private var reconnectConfig: Pair<Int, Int>? = null
        private var pinProvider: PinProvider? = null
        private var pinCommandVehicleApiStatusCallback: PinCommandVehicleApiStatusCallback? = null
        private var sessionExpiredHandler: SessionExpiredHandler? = null
        private var urlProvider: EndpointUrlProvider = StageBasedEndpointUrlProvider(
            Region.ECE,
            Stage.PROD
        )
        private var mobileSdkVersion: String = BuildConfig.MOBILE_SDK_VERSION
        private var tokenProvider: TokenProvider =
            IngressTokenProvider()
        private var certificateConfiguration: List<CertificateConfiguration>? = null
        private var errorProcessor: CertificatePinningErrorProcessor? = null
        private var deviceId: String? = null
        private var logHttpBody: Boolean = false

        /**
         * Enables or disables the SSO feature as well as all sharing of files and information
         * for family apps. If nothing is set SSO is not enabled by default. If multiple apps
         * have the same sharedUserId they must all be signed with the same signature.
         */
        fun enableSso(sharedUserId: String) = apply {
            this.sharedUserId = sharedUserId
        }

        /**
         * Specifies parameters for the socket reconnection in case of connection failures.
         */
        fun usePeriodicReconnect(periodInSeconds: Int, maxRetries: Int) = apply {
            this.reconnectConfig = periodInSeconds to maxRetries
        }

        /**
         * Sets the default [PinProvider] that shall be used for car commands that
         * require pin authentication.
         */
        fun usePinProvider(pinProvider: PinProvider) = apply {
            this.pinProvider = pinProvider
        }

        /**
         * Sets the default [SessionExpiredHandler] that shall be used for the case that the
         * current tokens are in an invalid state.
         */
        fun useSessionExpiredHandler(sessionExpiredHandler: SessionExpiredHandler) = apply {
            this.sessionExpiredHandler = sessionExpiredHandler
        }

        /**
         * Sets the token provider which is used to provide the user's authentication token. E.g. while trying to
         * automatically reconnect to the web socket.
         */
        fun useTokenProvider(tokenProvider: TokenProvider) = apply {
            this.tokenProvider = tokenProvider
        }

        /**
         * Use custom SDK version.
         * Used for identification of requests to the backend.
         * Default value is the current version of the MobileSDK.
         */
        fun useMobileSdkVersion(mobileSdkVersion: String) = apply {
            this.mobileSdkVersion = mobileSdkVersion
        }

        /**
         * Enables certificate pinning for networking operations within the MobileSDK ecosystem.
         */
        fun enableCertificatePinning(
            certificateConfiguration: List<CertificateConfiguration>,
            errorProcessor: CertificatePinningErrorProcessor?
        ) = apply {
            this.certificateConfiguration = certificateConfiguration
            this.errorProcessor = errorProcessor
        }

        /**
         * [Region] and [Stage] that is used for the initialization of the underlying modules.
         * Default is [Region.ECE] and [Stage.PROD].
         * Any calls that were prior made to [useCustomUrlProvider] will be discarded.
         */
        fun defaultRegionAndStage(region: Region, stage: Stage) = apply {
            urlProvider = StageBasedEndpointUrlProvider(region, stage)
        }

        /**
         * Sets a custom provider for endpoint URLs. Any calls that were prior made to
         * [defaultRegionAndStage] will be discarded.
         */
        fun useCustomUrlProvider(urlProvider: EndpointUrlProvider) = apply {
            this.urlProvider = urlProvider
        }

        /**
         * In order to allow critical command (e.g. Unlock vehicle doors) [PinCommandVehicleApiStatusCallback] must be set.
         */
        fun usePinCallback(
            vehicleApiStatusCallback: PinCommandVehicleApiStatusCallback
        ) = apply {
            this.pinCommandVehicleApiStatusCallback = vehicleApiStatusCallback
        }

        /**
         * Overrides the device ID that is used to uniquely identify the user's device within
         * the MobileSDK ecosystem.
         * It is also possible and recommended to just let the SDK generate a device ID.
         */
        fun useDeviceId(deviceId: String) = apply {
            this.deviceId = deviceId
        }

        /**
         * Configures the HTTP interceptor to also log the HTTP bodies
         */
        fun logHttpBody() = apply {
            logHttpBody = true
        }

        /**
         * Creates and returns an [MBMobileSDKConfiguration] object.
         */
        fun build(): MBMobileSDKConfiguration {
            val pinProvider = pinProvider
            val pinCommandStatusCallback =
                pinCommandVehicleApiStatusCallback
                    ?: if (pinProvider is PinCommandVehicleApiStatusCallback) {
                        pinProvider
                    } else {
                        DefaultPinCommandVehicleApiStatusCallback()
                    }
            if (!isNecessaryAuthenticationConfigsProvided(authenticationConfigurations)) {
                throw IllegalArgumentException("MBMobileSDK needs AuthenticationConfig for KEYCLOAK and CIAM")
            }
            return MBMobileSDKConfiguration(
                application,
                sessionExpiredHandler,
                appIdentifier,
                preferredAuthenticationType,
                sharedUserId,
                urlProvider,
                pinProvider,
                ingressKeyStoreAlias,
                reconnectConfig,
                tokenProvider,
                pinCommandStatusCallback,
                mobileSdkVersion,
                certificateConfiguration,
                errorProcessor,
                deviceId,
                authenticationConfigurations,
                logHttpBody
            )
        }

        private fun isNecessaryAuthenticationConfigsProvided(config: List<AuthenticationConfiguration>) =
            config.any {
                it.authenticationType == AuthenticationType.CIAM
            } && config.any {
                it.authenticationType == AuthenticationType.KEYCLOAK
            }
    }
}
