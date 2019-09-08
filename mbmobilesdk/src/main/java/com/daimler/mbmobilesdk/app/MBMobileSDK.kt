package com.daimler.mbmobilesdk.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.lifecycle.ProcessLifecycleOwner
import com.daimler.mbmobilesdk.BuildConfig
import com.daimler.mbmobilesdk.app.MBMobileSDK.init
import com.daimler.mbmobilesdk.app.foreground.MBMobileSDKForegroundObserver
import com.daimler.mbmobilesdk.app.foreground.ForegroundObservable
import com.daimler.mbmobilesdk.app.userswap.UserChangeProcessor
import com.daimler.mbmobilesdk.app.userswap.id.IngressUserIdProvider
import com.daimler.mbmobilesdk.app.userswap.id.PrivateCiamIdCache
import com.daimler.mbmobilesdk.app.userswap.id.SharedCiamIdCache
import com.daimler.mbmobilesdk.app.userswap.id.UserIdCache
import com.daimler.mbmobilesdk.biometric.BiometricPinProviderM
import com.daimler.mbmobilesdk.biometric.auth.BiometricAuthenticator
import com.daimler.mbmobilesdk.biometric.auth.privateMBMobileSDKBiometrics
import com.daimler.mbmobilesdk.biometric.auth.sharedMBMobileSDKBiometrics
import com.daimler.mbmobilesdk.biometric.pincache.PinCache
import com.daimler.mbmobilesdk.business.NotificationsService
import com.daimler.mbmobilesdk.featuretoggling.*
import com.daimler.mbmobilesdk.implementation.NotificationsServiceProxy
import com.daimler.mbmobilesdk.networking.RetrofitService
import com.daimler.mbmobilesdk.notificationcenter.InboxMessageService
import com.daimler.mbmobilesdk.notificationcenter.NotificationCenterConfig
import com.daimler.mbmobilesdk.preferences.PreferencesProxy
import com.daimler.mbmobilesdk.preferences.PushSettings
import com.daimler.mbmobilesdk.preferences.TrackingSettings
import com.daimler.mbmobilesdk.preferences.mbmobilesdk.MBMobileSDKSettings
import com.daimler.mbmobilesdk.preferences.jumio.JumioSettings
import com.daimler.mbmobilesdk.preferences.support.SupportSettings
import com.daimler.mbmobilesdk.preferences.user.UserSettings
import com.daimler.mbmobilesdk.push.storage.PushDataPreferencesStorage
import com.daimler.mbmobilesdk.push.storage.PushDataStorage
import com.daimler.mbmobilesdk.tracking.MyCarAppMetricaTrackingService
import com.daimler.mbmobilesdk.tracking.MyCarTrackingMemoryParametersHandler
import com.daimler.mbmobilesdk.utils.extensions.toUserLocale
import com.daimler.mbmobilesdk.utils.extensions.userLocale
import com.daimler.mbmobilesdk.utils.isMarshmallow
import com.daimler.mbcommonkit.preferences.PreferenceObserver
import com.daimler.mbcommonkit.tracking.MBTrackingService
import com.daimler.mbdeeplinkkit.MBDeepLinkKit
import com.daimler.mbdeeplinkkit.DeepLinkServiceConfig
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.IngressServiceConfig
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.MBCarKitServiceConfig
import com.daimler.mbcarkit.proto.ProtoMessageParser
import com.daimler.mbcarkit.proto.ServiceProtoMessageParser
import com.daimler.mbnetworkkit.MBNetworkKit
import com.daimler.mbnetworkkit.NetworkServiceConfig
import com.daimler.mbnetworkkit.SocketServiceConfig
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.socket.SocketService
import com.daimler.mbsupportkit.MBSupportKit
import com.daimler.mbsupportkit.SupportServiceConfig
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import io.realm.exceptions.RealmError
import java.util.*

/**
 * Initializer object for this library.
 * Just call [init] from your application subclass.
 */
object MBMobileSDK {

    @SuppressLint("StaticFieldLeak")
    private var mobileSDKConfig: MBMobileSDKConfig? = null

    private lateinit var featureToggleService: FeatureService
    private lateinit var pushService: NotificationsService
    private lateinit var headerService: HeaderService

    private lateinit var preferencesProxy: PreferencesProxy

    private lateinit var pushDataStorage: PushDataStorage

    private var biometrics: BiometricAuthenticator? = null

    private var appSessionId: String? = null

    private const val SHARED_USER_ID = "com.daimler"
    private const val INGRESS_KEYSTORE_ALIAS = "com.daimler.ingress.sso.keystore.alias"

    private val DEFAULT_USER_CONTEXT = UserContext("DEFAULT_LOGGED_OUT")
    private var trackingObserver: PreferenceObserver<Boolean>? = null

    private var initialized = false
    private lateinit var foregroundObservable: ForegroundObservable

    internal val appInForeground: Boolean
        get() = if (initialized) foregroundObservable.appInForeground else false

    /**
     * Initializes this SDK including the sub-modules.
     *
     * @see MBMobileSDKConfig.Builder for available configuration parameters
     */
    fun init(mobileSDKConfig: MBMobileSDKConfig) {
        this.mobileSDKConfig = mobileSDKConfig
        preferencesProxy = PreferencesProxy(mobileSDKConfig.app, mobileSDKConfig.enableSso, SHARED_USER_ID, mobileSDKConfig.endpoint)
        biometrics = createMobileSdkBiometrics(mobileSDKConfig)
        try {
            initModules(preferencesProxy, preferencesProxy)
            initialized = true
        } catch (e: RealmError) {
            // Needs catch because of AppMetrica.
            MBLoggerKit.e("Skipped init.", throwable = e)
        }
    }

    /**
     * Returns the [FeatureService].
     */
    fun featureToggleService(): FeatureService = featureToggleService

    internal fun notificationsService() = pushService

    internal fun mobileSdkSettings(): MBMobileSDKSettings = preferencesProxy

    internal fun pushSettings(): PushSettings = preferencesProxy

    internal fun userSettings(): UserSettings = preferencesProxy

    internal fun userPinCache(): PinCache = preferencesProxy

    internal fun trackingSettings(): TrackingSettings = preferencesProxy

    internal fun supportSettings(): SupportSettings = preferencesProxy

    internal fun jumioSettings(): JumioSettings = preferencesProxy

    internal fun headerService(): HeaderService = headerService

    internal fun pushDataStorage(): PushDataStorage = pushDataStorage

    internal fun foregroundObservable(): ForegroundObservable = foregroundObservable

    /**
     * Returns the [BiometricAuthenticator] or null if biometrics are not supported on the device.
     */
    internal fun biometrics(): BiometricAuthenticator? = biometrics

    /**
     * Returns true if stage selection is enabled.
     */
    internal fun endpointSelectionEnabled(): Boolean = mobileSDKConfig?.enableStageSelection == true

    /**
     * Returns the session ID used by the modules.
     */
    fun appSessionId(): String = appSessionId!!

    /**
     * Returns the app identifier provided by the configuration.
     */
    fun appIdentifier(): String = mobileSDKConfig!!.appIdentifier

    /**
     * Returns the FCM token as received by Firebase.
     */
    fun fcmToken(): String = pushSettings().fcmToken.get()

    /**
     * Returns country and language code as selected by the user or the device's default locale
     * if there is no user available.
     */
    fun userLocale(): UserLocale {
        val default = Locale.getDefault().toUserLocale()
        return if (initialized) {
            MBIngressKit.userLocale(default)
        } else {
            default
        }
    }

    /**
     * Returns the user selected region, e.g. ECE.
     */
    fun selectedRegion(): Region = mobileSdkSettings().endPoint.get().region

    /**
     * Returns the user selected stage, e.g. PROD.
     */
    fun selectedStage(): Stage = mobileSdkSettings().endPoint.get().stage

    internal fun initModules(mbMobileSDKSettings: MBMobileSDKSettings, trackingSettings: TrackingSettings) {
        val config = mobileSDKConfig
            ?: throw IllegalStateException("MBMobileSDK must be initialized first!")
        val sessionId = UUID.randomUUID()
        appSessionId = sessionId.toString()

        val endpoint = mbMobileSDKSettings.endPoint.get()
        val deviceId = mbMobileSDKSettings.mobileSdkDeviceId.get()

        MBLoggerKit.d("Endpoint = $endpoint")

        headerService = initMBNetworkKit(mobileSDKConfig?.appIdentifier.orEmpty(), userLocale())
        initMBIngressKit(config, sessionId, endpoint, deviceId, headerService)
        initMyCarAndSocketService(config, sessionId, endpoint, headerService)
        initMBDeepLinkKit(config, sessionId, endpoint, headerService)
        initMobileSdkServices(config, sessionId, endpoint, headerService)
        initTracking(config, trackingSettings)
        initSettingsObserver(config, trackingSettings)
        initSupportModule(config, endpoint)
    }

    internal fun appVersion(): String {
        val app = mobileSDKConfig?.app ?: return ""
        return app.packageManager.getPackageInfo(app.packageName, 0).versionName
    }

    // Should be called after a logout.
    internal fun destroy() {
        clearMyCar()
        clearIngress()
        clearDeepLink()

        userSettings().reset()
        userPinCache().clear()
        supportSettings().resetSupportSettings()
    }

    private fun clearMyCar() {
        if (!MBCarKit.isSocketDisposed()) {
            MBLoggerKit.d("Already connected while logout was called. Socket connection will be closed")
            MBCarKit.disconnectFromWebSocket()
        }
        MBCarKit.clearLocalCache()
    }

    private fun clearIngress() {
        MBIngressKit.clearLocalCache()
    }

    private fun clearDeepLink() {
        MBDeepLinkKit.clearCache()
    }

    private fun userChangedWhileAppRunning() {
        // We do not need to clear shared settings here, since they were cleared after a logout by
        // another app. We just delete relevant app-specific data here.
        clearMyCar()
        clearIngress()
        clearDeepLink()
    }

    internal fun initialActivity(): Class<out Activity>? = mobileSDKConfig?.initialScreen

    private fun initMBNetworkKit(appIdentifier: String, userLocale: UserLocale): HeaderService {
        MBNetworkKit.init(
            NetworkServiceConfig.Builder(
                appIdentifier,
                appVersion(),
                BuildConfig.RIS_SDK_VERSION
            ).apply {
                useOSVersion(Build.VERSION.RELEASE)
                useLocale(userLocale.format())
            }.build()
        )
        return MBNetworkKit.headerService()
    }

    private fun initMBIngressKit(config: MBMobileSDKConfig, sessionId: UUID, endpoint: Endpoint, deviceId: String, headerService: HeaderService) {
        val serviceConfigBuilder = IngressServiceConfig.Builder(config.app,
            endpoint.authUrl(), endpoint.bffUrl(), ingressStage(endpoint),
            INGRESS_KEYSTORE_ALIAS, headerService, config.clientId)
            .apply {
                if (config.enableSso) {
                    enableSso(SHARED_USER_ID)
                }
                useAppSessionId(sessionId)
                useDeviceId(deviceId)
                config.sessionExpiredHandler?.let {
                    useSessionExpiredHandler(it)
                }
            }
        MBIngressKit.init(serviceConfigBuilder.build())
    }

    private fun initMyCarAndSocketService(config: MBMobileSDKConfig, sessionId: UUID, endpoint: Endpoint, headerService: HeaderService) {
        val pinProvider = createPinProviderChain(config.app)
        val carKitConfigBuilder = MBCarKitServiceConfig.Builder(config.app,
            endpoint.bffUrl(), headerService)
            .apply {
                usePinProvider(pinProvider)
                useAppSessionId(sessionId)
                if (config.enableSso) {
                    shareSelectedVehicle(SHARED_USER_ID)
                }
            }
        MBCarKit.init(carKitConfigBuilder.build())
        val socketServiceBuilder = SocketServiceConfig.Builder(endpoint.socketUrl(),
            MBCarKit.createMycarMessageProcessor(ProtoMessageParser(),
                pinProvider,
                pinProvider,
                MBCarKit.createServiceMessageProcessor(ServiceProtoMessageParser())))
            .apply {
                mobileSDKConfig!!.reconnectConfig?.let { reconnect ->
                    tryPeriodicReconnect(reconnect.first.toLong(), reconnect.second)
                }
                useAppSessionId(sessionId)
            }
        SocketService.init(socketServiceBuilder.create())
    }

    private fun initMBDeepLinkKit(
        config: MBMobileSDKConfig,
        sessionId: UUID,
        endpoint: Endpoint,
        headerService: HeaderService
    ) {
        MBDeepLinkKit.init(
            DeepLinkServiceConfig.Builder(
                config.app,
                endpoint.bffUrl(),
                headerService
            ).apply {
                useSessionId(sessionId.toString())
            }.build()
        )
    }

    /*
    Several MBMobileSDK services rely on other services, so make sure to call this method after
    the other services are initialized.
     */
    private fun initMobileSdkServices(
        config: MBMobileSDKConfig,
        sessionId: UUID,
        endpoint: Endpoint,
        headerService: HeaderService
    ) {
        val retrofitService = RetrofitService(config.app, endpoint.bffUrl(),
            NotificationCenterConfig(NotificationCenterConfig.Stage.INT).url(),
            preferencesProxy.mobileSdkDeviceId.get(), config.pushDistributionProfile,
            sessionId.toString(), headerService)

        // FeatureToggle
        val defaults = config.featureDefaults
        val toggleService = if (config.enableFeatureToggling) {
            createLaunchDarklyService(config.app, DEFAULT_USER_CONTEXT, endpoint.stage)
        } else {
            FallbackFeatureService()
        }
        val mergedDefaults = mergeFeatureDefaults(FEATURE_DEFAULTS, defaults)
        mergedDefaults?.let { toggleService.registerFeatureDefaults(it) }
        featureToggleService = toggleService

        // Push Notifications
        pushService = NotificationsServiceProxy(retrofitService)
        pushDataStorage = PushDataPreferencesStorage(config.app)

        if (!initialized) {
            // User swap
            val userIdCache: UserIdCache = if (config.enableSso) {
                SharedCiamIdCache(config.app, SHARED_USER_ID)
            } else {
                PrivateCiamIdCache(config.app)
            }
            val userIdProvider = IngressUserIdProvider(userIdCache, MBIngressKit.authenticationService())
            ProcessLifecycleOwner.get().lifecycle.addObserver(
                UserChangeProcessor(config.app, userIdProvider) { userChangedWhileAppRunning() }
            )

            // Foreground
            val observer = MBMobileSDKForegroundObserver(config.app)
            foregroundObservable = observer
            ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
        }

        observeBluetoothFeatureToggle()
    }

    private fun initTracking(config: MBMobileSDKConfig, trackingSettings: TrackingSettings) {
        if (!initialized) {
            MBTrackingService.trackingEnabled = trackingSettings.trackingEnabled.get()

            // AppMetrica
            config.appMetricaKey?.let {
                val metricaConfig = YandexMetricaConfig
                    .newConfigBuilder(it)
                    .withStatisticsSending(trackingSettings.trackingEnabled.get())
                    .build()
                YandexMetrica.activate(config.app, metricaConfig)
                YandexMetrica.enableActivityAutoTracking(config.app)

                MBTrackingService.registerService(MyCarAppMetricaTrackingService(MyCarTrackingMemoryParametersHandler()))
            } ?: MBLoggerKit.w("App metrica tracking disabled because no API key was given.")
        }
    }

    private fun initSettingsObserver(
        config: MBMobileSDKConfig,
        trackingSettings: TrackingSettings
    ) {
        trackingObserver?.let { trackingSettings.trackingEnabled.stopObserving(it) }
        trackingObserver = object : PreferenceObserver<Boolean> {
            override fun onChanged(newValue: Boolean) {
                MBTrackingService.trackingEnabled = newValue
                config.appMetricaKey?.let {
                    YandexMetrica.setStatisticsSending(config.app, newValue)
                }
            }
        }.apply { trackingSettings.trackingEnabled.observe(this) }
    }

    private fun initSupportModule(config: MBMobileSDKConfig, endpoint: Endpoint) {
        val stage = when (endpoint.stage) {
            Stage.MOCK -> SupportStage.TEST
            Stage.INT -> SupportStage.INT
            Stage.PROD -> SupportStage.PROD
        }

        val supportServiceConfig = SupportServiceConfig.Builder(config.app, endpoint.supportUrl(), stage.identifier, MBNetworkKit.headerService()).build()
        MBSupportKit.init(supportServiceConfig)
    }

    private fun mergeFeatureDefaults(
        first: FeatureDefaults?,
        second: FeatureDefaults?
    ): FeatureDefaults? =
        when {
            first == null -> second
            second == null -> first
            else -> first.apply { merge(second) }
        }

    private fun ingressStage(endpoint: Endpoint): String {
        val ingressStage = when (endpoint.stage) {
            Stage.INT -> IngressStage.INT
            Stage.PROD -> IngressStage.PROD
            Stage.MOCK -> IngressStage.INT
        }
        return ingressStage.identifier
    }

    private fun createMobileSdkBiometrics(mobileSDKConfig: MBMobileSDKConfig): BiometricAuthenticator? {
        return if (isMarshmallow()) {
            if (mobileSDKConfig.enableSso) {
                sharedMBMobileSDKBiometrics(mobileSDKConfig.app, SHARED_USER_ID)
            } else {
                privateMBMobileSDKBiometrics(mobileSDKConfig.app)
            }
        } else {
            null
        }
    }

    private fun createPinProviderChain(app: Application): BasePinProvider {
        PopupPinProvider.init(app)
        val biometrics = this.biometrics
        return if (isMarshmallow() && biometrics != null) {
            BiometricPinProviderM(app, biometrics, userPinCache(), userSettings(), PopupPinProvider)
        } else {
            PopupPinProvider
        }
    }

    private fun createLaunchDarklyService(app: Application, userContext: UserContext, stage: Stage): FeatureService {
        LaunchDarklyFeatureService.init(
            app,
            userContext,
            if (stage == Stage.PROD) LD_ENVIRONMENT_PROD else LD_ENVIRONMENT_TEST,
            if (stage == Stage.PROD) LD_ENVIRONMENT_TEST else LD_ENVIRONMENT_PROD
        )
        return LaunchDarklyFeatureService
    }

    private fun observeBluetoothFeatureToggle() {
        featureToggleService().registerFeatureListener(FLAG_BLUETOOTH_ENABLEMENT, object : OnFeatureChangedListener {
            override fun onFeatureChanged(key: String) {
                MBCarKit.sendToCarService().enableBluetooth(isFeatureToggleEnabled(FLAG_BLUETOOTH_ENABLEMENT))
            }
        })
        MBCarKit.sendToCarService().enableBluetooth(isFeatureToggleEnabled(FLAG_BLUETOOTH_ENABLEMENT))
    }
}