package com.daimler.mbmobilesdk.configuration

import android.app.Application
import android.util.Log
import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.socket.PinCommandVehicleApiStatusCallback
import com.daimler.mbingresskit.common.authentication.AuthenticationConfiguration
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.login.SessionExpiredHandler
import com.daimler.mbnetworkkit.certificatepinning.CertificateConfiguration
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningErrorProcessor
import com.daimler.mbnetworkkit.common.TokenProvider
import com.daimler.mbnetworkkit.common.TokenProviderCallback
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MBMobileSDKConfigurationTest {

    companion object {
        private const val CLIENT_ID = "clientId"
        private const val APP_IDENTIFIER = "appIdentifier"
        private const val INGRESS_KEY_STORE_ALIAS = "keyStoreAlias"
    }

    private val application = Application()
    private lateinit var builder: MBMobileSDKConfiguration.Builder

    private val authenticationConfigurations = listOf(
        AuthenticationConfiguration(
            AuthenticationType.KEYCLOAK,
            "KEYCLOAK_CLIENT_ID"
        ),
        AuthenticationConfiguration(
            AuthenticationType.CIAM,
            "CIAM_CLIENT_ID"
        )
    )

    @Before
    fun before() {
        builder = MBMobileSDKConfiguration.Builder(
            application,
            APP_IDENTIFIER,
            INGRESS_KEY_STORE_ALIAS,
            AuthenticationType.KEYCLOAK,
            authenticationConfigurations
        )
    }

    @Test
    fun test_configBuilder_basicValuesShouldBeSet() {
        val config = builder.build()
        assertEquals(application, config.application)
        assertEquals(APP_IDENTIFIER, config.appIdentifier)
        assertEquals(authenticationConfigurations, config.authenticationConfigs)
        assertEquals(INGRESS_KEY_STORE_ALIAS, config.ingressKeyStoreAlias)
    }

    @Test
    fun test_configBuilder_useExpiredHandler_configShouldUseExpiredHandler() {
        val sessionExpiredHandler = object : SessionExpiredHandler {
            override fun onSessionExpired(statusCode: Int, errorBody: String?) {
                errorBody?.let {
                    Log.e(statusCode.toString(), it)
                }
            }
        }
        builder.useSessionExpiredHandler(sessionExpiredHandler)
        val config = builder.build()
        assertEquals(sessionExpiredHandler, config.expiredHandler)
    }

    @Test
    fun test_configBuilder_usePeriodicReconnect_configShouldUsePeriodicReconnect() {
        val periodicInSeconds = 3
        val maxRetries = 5
        builder.usePeriodicReconnect(periodicInSeconds, maxRetries)
        val config = builder.build()
        assertEquals(periodicInSeconds, config.reconnectConfig?.first)
        assertEquals(maxRetries, config.reconnectConfig?.second)
    }

    @Test
    fun test_configBuilder_setDefaultRegionAndStage_configShouldUseAppropriateUrlProvider() {
        val defaultRegion = Region.ECE
        val defaultStage = Stage.PROD
        builder.defaultRegionAndStage(defaultRegion, defaultStage)
        val config = builder.build()
        assertTrue(config.urlProvider.isProductiveEnvironment)
    }

    @Test
    fun test_configBuilder_setDefaultRegionAndStageMock_configShouldUseAppropriateUrlProvider() {
        val defaultRegion = Region.ECE
        val defaultStage = Stage.INT
        builder.defaultRegionAndStage(defaultRegion, defaultStage)
        val config = builder.build()
        assertFalse(config.urlProvider.isProductiveEnvironment)
    }

    @Test
    fun test_configBuilder_shouldUseDefaultUrlProvider() {
        val config = builder.build()
        assertTrue(config.urlProvider.isProductiveEnvironment)
    }

    @Test
    fun test_configBuilder_enableCertificatePinning_configShouldUseCertificateConfigAndErrorProcessor() {
        val host = "host"
        val publicKeyHashes = listOf("1", "2", "3")
        val certificateConfiguration = CertificateConfiguration(host, publicKeyHashes)
        val certificateList = listOf(certificateConfiguration)
        val errorProcessor = object : CertificatePinningErrorProcessor {
            override fun onCertificatePinningError(error: Throwable) {
                Log.e(null, null, error)
            }
        }

        builder.enableCertificatePinning(certificateList, errorProcessor)
        val config = builder.build()
        assertEquals(errorProcessor, config.errorProcessor)
        assertNotNull(config.certificateConfiguration)
        assertEquals(1, config.certificateConfiguration?.size)
        assertEquals(certificateConfiguration, config.certificateConfiguration?.firstOrNull())
    }

    @Test
    fun test_configBuilder_enableSso_configShouldUseCorrectSharedUserIdAndKeystoreAlias() {
        val sharedUserId = "sharedUserId"
        builder.enableSso(sharedUserId)
        val config = builder.build()
        assertEquals(sharedUserId, config.sharedUserId)
    }

    @Test
    fun test_configBuilder_useCustomUrlProvider_configShouldUseCustomUrlProvider() {
        val authUrl = "authUrl"
        val bffUrl = "bffUrl"
        val socketUrl = "socketUrl"
        val customUrlProvider = object : EndpointUrlProvider {
            override val isProductiveEnvironment: Boolean
                get() = false

            override fun authUrl(authenticationType: AuthenticationType) = authUrl
            override val bffUrl: String
                get() = bffUrl
            override val socketUrl: String
                get() = socketUrl
        }

        builder.useCustomUrlProvider(customUrlProvider)
        val config = builder.build()
        assertEquals(customUrlProvider, config.urlProvider)
        assertEquals(authUrl, config.urlProvider.authUrl(AuthenticationType.KEYCLOAK))
        assertEquals(bffUrl, config.urlProvider.bffUrl)
        assertEquals(socketUrl, config.urlProvider.socketUrl)
        assertFalse(config.urlProvider.isProductiveEnvironment)
    }

    @Test
    fun test_configBuilder_useDeviceId_configShouldUseDeviceId() {
        val deviceId = "deviceId"
        builder.useDeviceId(deviceId)
        val config = builder.build()
        assertEquals(deviceId, config.deviceId)
    }

    @Test
    fun test_configBuilder_useMobileSdkVersion_configShouldUseMobileSdkVersion() {
        val mobileSdkVersion = "1.2.3"
        builder.useMobileSdkVersion(mobileSdkVersion)
        val config = builder.build()
        assertEquals(mobileSdkVersion, config.mobileSdkVersion)
    }

    @Test
    fun test_configBuilder_usePinCallback_configShouldUsePinCallback() {
        val pinCallback = object : PinCommandVehicleApiStatusCallback {
            override fun onPinAccepted(commandStatus: CommandVehicleApiStatus, pin: String) {
                Log.d(pin, "Accepted")
            }

            override fun onPinInvalid(
                commandStatus: CommandVehicleApiStatus,
                pin: String,
                attempts: Int
            ) {
                Log.d(pin, "Invalid")
            }

            override fun onUserBlocked(
                commandStatus: CommandVehicleApiStatus,
                pin: String,
                attempts: Int,
                blockingTimeSeconds: Int
            ) {
                Log.d(pin, "User blocked")
            }
        }
        builder.usePinCallback(pinCallback)
        val config = builder.build()
        assertEquals(pinCallback, config.pinCommandVehicleApiStatusCallback)
    }

    @Test
    fun test_configBuilder_usePinProvider_configShouldUsePinProvider() {
        val pinProvider = object : PinProvider {
            override fun requestPin(pinRequest: PinRequest) {
                Log.d(null, "Pin requested")
            }
        }
        builder.usePinProvider(pinProvider)
        val config = builder.build()
        assertEquals(pinProvider, config.pinProvider)
    }

    @Test
    fun test_configBuilder_useTokenProvider_configShouldUseTokenProvider() {
        val tokenProvider = object : TokenProvider {
            override fun requestToken(callback: TokenProviderCallback) {
                Log.d(null, "Token requested")
            }
        }
        builder.useTokenProvider(tokenProvider)
        val config = builder.build()
        assertEquals(tokenProvider, config.tokenProvider)
    }
}
