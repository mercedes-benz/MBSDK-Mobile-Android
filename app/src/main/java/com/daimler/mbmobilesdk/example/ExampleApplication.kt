package com.daimler.mbmobilesdk.example

import android.app.Application
import com.daimler.mbingresskit.common.authentication.AuthenticationConfiguration
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.PrinterConfig
import com.daimler.mbloggerkit.adapter.AndroidLogAdapter
import com.daimler.mbloggerkit.adapter.PersistingLogAdapter
import com.daimler.mbloggerkit.shake.LogOverlay
import com.daimler.mbmobilesdk.MBMobileSDK
import com.daimler.mbmobilesdk.configuration.MBMobileSDKConfiguration
import com.daimler.mbmobilesdk.configuration.Region
import com.daimler.mbmobilesdk.configuration.Stage
import com.daimler.mbmobilesdk.example.utils.SamplePinProvider

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupMobileSdk()
    }

    private fun setupMobileSdk() {

        // Enable logging in the modules
        MBLoggerKit.usePrinterConfig(
            PrinterConfig.Builder()
                .showLogMenuWithShake(this, LogOverlay.Order.CHRONOLOGICAL_DESCENDING)
                .addAdapter(
                    AndroidLogAdapter.Builder()
                        .setLoggingEnabled(BuildConfig.DEBUG)
                        .build()
                )
                .addAdapter(
                    PersistingLogAdapter.Builder(this)
                        .useMemoryLogging()
                        .setLoggingEnabled(BuildConfig.DEBUG)
                        .build()
                )
                .build()
        )

        val region = Region.ECE
        val stage = Stage.PROD

        // Basic setup of the modules
        val config = MBMobileSDKConfiguration.Builder(
            application = this,
            appIdentifier = "reference",
            ingressKeyStoreAlias = INGRESS_KEY_STORE,
            preferredAuthenticationType = AuthenticationType.KEYCLOAK,
            authenticationConfigurations = listOf(
                AuthenticationConfiguration(
                    AuthenticationType.KEYCLOAK,
                    BuildConfig.KEYCLOAK_CLIENT_ID
                ),
                AuthenticationConfiguration(
                    AuthenticationType.CIAM,
                    BuildConfig.CIAM_CLIENT_ID
                )
            )
        ).apply {
            defaultRegionAndStage(region, stage)
            usePinProvider(
                SamplePinProvider().also {
                    it.register(this@ExampleApplication)
                }
            )
            if (BuildConfig.DEBUG) {
                logHttpBody()
            }
        }
            .build()

        MBMobileSDK.setup(config)
    }

    companion object {

        private const val INGRESS_KEY_STORE = "com.daimler.mbmobilesdk.ingress.sso.keystore.alias"
    }
}
