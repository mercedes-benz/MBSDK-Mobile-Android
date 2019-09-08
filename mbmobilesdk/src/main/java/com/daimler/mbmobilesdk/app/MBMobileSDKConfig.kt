package com.daimler.mbmobilesdk.app

import android.app.Activity
import android.app.Application
import com.daimler.mbmobilesdk.business.model.PushDistributionProfile
import com.daimler.mbmobilesdk.featuretoggling.FeatureDefaults
import com.daimler.mbingresskit.login.SessionExpiredHandler
import com.daimler.mbcarkit.business.PinProvider

/**
 * Config class for the [MBMobileSDK] module.
 */
class MBMobileSDKConfig private constructor(
    val app: Application,
    val enableSso: Boolean,
    val reconnectConfig: Pair<Int, Int>?,
    val sessionExpiredHandler: SessionExpiredHandler?,
    val enableFeatureToggling: Boolean = false,
    val featureDefaults: FeatureDefaults? = null,
    val appIdentifier: String = "",
    val pushDistributionProfile: PushDistributionProfile = PushDistributionProfile.DEV,
    val initialScreen: Class<out Activity>?,
    val appMetricaKey: String? = null,
    val enableStageSelection: Boolean,
    internal val endpoint: Endpoint,
    val clientId: String
) {

    /**
     * Builder class to create the [MBMobileSDKConfig].
     *
     * @param app application
     * @param enableStageSelection determines whether the selector for different regions and stages
     * is visible on the login screen
     * @param appIdentifier a unique identifier for your application
     * @param usePushDebugProfile distribution type for push notifications
     */
    class Builder(
        private val app: Application,
        private val enableStageSelection: Boolean,
        private val appIdentifier: String = "",
        private val clientId: String,
        private val usePushDebugProfile: Boolean = true
    ) {

        private var enableSso = true
        private var reconnectConfig: Pair<Int, Int>? = null
        private var pinProvider: PinProvider? = null
        private var sessionExpiredHandler: SessionExpiredHandler? = null
        private var enableFeatureToggling = false
        private var featureDefaults: FeatureDefaults? = null
        private var activityClass: Class<out Activity>? = null
        private var appMetricaKey: String? = null
        private var endpoint: Endpoint = Endpoint(Region.ECE, Stage.PROD)

        /**
         * Enables or disables the SSO feature as well as all sharing of files and information
         * for family apps. Default is `true`.
         */
        fun enableSso(ssoEnabled: Boolean): Builder {
            enableSso = ssoEnabled
            return this
        }

        /**
         * Specifies parameters for the socket reconnection in case of connection failures.
         */
        fun usePeriodicReconnect(periodInSeconds: Int, maxRetries: Int): Builder {
            reconnectConfig = periodInSeconds to maxRetries
            return this
        }

        /**
         * Sets the default [PinProvider] that shall be used for car commands that
         * require pin authentication.
         */
        @Deprecated("The custom PinProvider is no longer used.")
        fun useDefaultPinProvider(pinProvider: PinProvider): Builder {
            this.pinProvider = pinProvider
            return this
        }

        /**
         * Sets the default [SessionExpiredHandler] that shall be used for the case that the
         * current tokens are in an invalid state.
         */
        fun useSessionExpiredHandler(sessionExpiredHandler: SessionExpiredHandler): Builder {
            this.sessionExpiredHandler = sessionExpiredHandler
            return this
        }

        /**
         * Enables the feature toggling. Default is false.
         */
        fun enableFeatureToggling(
            enableFeatureToggling: Boolean,
            defaults: FeatureDefaults? = null
        ): Builder {
            this.enableFeatureToggling = enableFeatureToggling
            this.featureDefaults = defaults
            return this
        }

        /**
         * Sets the activity that shall be started after the splashscreen.
         */
        fun useInitialActivity(activityClass: Class<out Activity>): Builder {
            this.activityClass = activityClass
            return this
        }

        /**
         * Enables AppMetrica tracking with the given API key.
         */
        fun enableAppMetricaTracking(apiKey: String): Builder {
            this.appMetricaKey = apiKey
            return this
        }

        /**
         * [Region] and [Stage] that is used if the user is not logged in and no stage
         * was selected previously.
         * Default is [Region.ECE] and [Stage.PROD].
         */
        fun defaultRegionAndStage(region: Region, stage: Stage): Builder {
            this.endpoint = Endpoint(region, stage)
            return this
        }

        /**
         * Creates and returns an [MBMobileSDKConfig] object.
         */
        fun build(): MBMobileSDKConfig {
            val profile = if (usePushDebugProfile) {
                PushDistributionProfile.DEV
            } else {
                PushDistributionProfile.STORE
            }
            return MBMobileSDKConfig(
                app,
                enableSso,
                reconnectConfig,
                sessionExpiredHandler,
                enableFeatureToggling,
                featureDefaults,
                appIdentifier,
                profile,
                activityClass,
                appMetricaKey,
                enableStageSelection,
                endpoint,
                clientId
            )
        }
    }
}