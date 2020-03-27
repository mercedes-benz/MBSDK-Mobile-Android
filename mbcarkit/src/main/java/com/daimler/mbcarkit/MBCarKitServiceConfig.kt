package com.daimler.mbcarkit

import android.content.Context
import android.net.ConnectivityManager
import com.daimler.mbcarkit.business.AccountLinkageService
import com.daimler.mbcarkit.business.AssignmentService
import com.daimler.mbcarkit.business.CarVehicleApiCommandManager
import com.daimler.mbcarkit.business.CommandCapabilitiesCache
import com.daimler.mbcarkit.business.GeofencingService
import com.daimler.mbcarkit.business.OnboardGeofencingService
import com.daimler.mbcarkit.business.OutletsService
import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcarkit.business.SendToCarCache
import com.daimler.mbcarkit.business.SendToCarService
import com.daimler.mbcarkit.business.ServiceCache
import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.SpeedAlertService
import com.daimler.mbcarkit.business.SpeedfenceService
import com.daimler.mbcarkit.business.UserManagementService
import com.daimler.mbcarkit.business.ValetProtectService
import com.daimler.mbcarkit.business.VehicleImageCache
import com.daimler.mbcarkit.business.VehicleImageService
import com.daimler.mbcarkit.business.VehicleService
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiService
import com.daimler.mbcarkit.implementation.CachedAccountLinkageService
import com.daimler.mbcarkit.implementation.CachedAssignmentService
import com.daimler.mbcarkit.implementation.CachedGeofencingService
import com.daimler.mbcarkit.implementation.CachedMerchantsService
import com.daimler.mbcarkit.implementation.CachedOnboardGeofencingService
import com.daimler.mbcarkit.implementation.CachedSendToCarProvider
import com.daimler.mbcarkit.implementation.CachedServiceService
import com.daimler.mbcarkit.implementation.CachedSpeedAlertService
import com.daimler.mbcarkit.implementation.CachedSpeedfenceService
import com.daimler.mbcarkit.implementation.CachedUserManagementService
import com.daimler.mbcarkit.implementation.CachedValetProtectService
import com.daimler.mbcarkit.implementation.CachedVehicleImageService
import com.daimler.mbcarkit.implementation.CachedVehicleService
import com.daimler.mbcarkit.implementation.FilteredServiceService
import com.daimler.mbcarkit.implementation.PinCarVehicleApiCommandManager
import com.daimler.mbcarkit.implementation.SDKSendToCarService
import com.daimler.mbcarkit.implementation.ServiceActivationStateProcessorImpl
import com.daimler.mbcarkit.network.RetrofitServiceProviderImpl
import com.daimler.mbcarkit.persistance.MBCarKitRealmModule
import com.daimler.mbcarkit.persistance.RealmCommandCapabilitiesCache
import com.daimler.mbcarkit.persistance.RealmSendToCarCache
import com.daimler.mbcarkit.persistance.RealmServiceCache
import com.daimler.mbcarkit.persistance.RealmStatusCache
import com.daimler.mbcarkit.persistance.RealmUserManagementCache
import com.daimler.mbcarkit.persistance.RealmUtil
import com.daimler.mbcarkit.persistance.RealmVehicleCache
import com.daimler.mbcarkit.persistance.RealmVehicleImageCache
import com.daimler.mbcarkit.persistance.SelectedVehiclePreferencesStorage
import com.daimler.mbcarkit.persistance.SharedSelectedVehiclePreferencesStorage
import com.daimler.mbcarkit.socket.UserManagementCache
import com.daimler.mbcarkit.socket.VehicleCache
import com.daimler.mbcarkit.socket.VehicleStatusCache
import com.daimler.mbnetworkkit.certificatepinning.CertificateConfiguration
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningErrorProcessor
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.createHttpLoggingInterceptor
import com.daimler.mbrealmkit.MBRealmKit
import com.daimler.mbrealmkit.RealmServiceConfig
import okhttp3.logging.HttpLoggingInterceptor

class MBCarKitServiceConfig private constructor(
    val context: Context,
    val vehicleCache: VehicleCache,
    val vehicleRepository: VehicleService,
    val geofencingService: GeofencingService,
    val onboardGeofencingService: OnboardGeofencingService,
    val speedfenceService: SpeedfenceService,
    val valetProtectService: ValetProtectService,
    val merchantsRepository: OutletsService,
    val assignmentService: AssignmentService,
    val serviceService: ServiceService,
    val speedAlertService: SpeedAlertService,
    val imageCache: VehicleImageCache,
    val imageService: VehicleImageService,
    var vehicleStatusCache: VehicleStatusCache,
    val serviceCache: ServiceCache,
    val commandVehicleApiManager: CarVehicleApiCommandManager,
    val sendToCarService: SendToCarService,
    val commandVehicleApiService: CommandVehicleApiService,
    val selectedVehicleStorage: SelectedVehicleStorage,
    val userManagementService: UserManagementService,
    val userManagementCache: UserManagementCache,
    val commandCapabilitiesCache: CommandCapabilitiesCache,
    val sendToCarCache: SendToCarCache,
    val accountLinkageService: AccountLinkageService
) {

    class Builder(
        /**
         * Android Context
         */
        private val context: Context,
        /**
         * Base url to connect to vehicle socket
         */
        private val vehicleUrl: String,
        /**
         * Service that intecepts network requests and adds necessary headers
         */
        private val headerService: HeaderService
    ) {

        private var pinProvider: PinProvider? = null
        private var sharedUserId: String? = null
        private var pinningConfigurations: List<CertificateConfiguration> = emptyList()
        private var pinningErrorProcessor: CertificatePinningErrorProcessor? = null

        /**
         * Set a custom PinProvider
         */
        fun usePinProvider(pinProvider: PinProvider): Builder {
            this.pinProvider = pinProvider
            return this
        }

        /**
         * Configures certificate pinning
         */
        fun useCertificatePinning(
            pinningConfigurations: List<CertificateConfiguration>,
            errorProcessor: CertificatePinningErrorProcessor? = null
        ): Builder {
            this.pinningConfigurations = pinningConfigurations
            this.pinningErrorProcessor = errorProcessor
            return this
        }

        /**
         * Share the vehicle for Single Sign-On
         */
        fun shareSelectedVehicle(sharedUserId: String): Builder {
            this.sharedUserId = sharedUserId
            return this
        }

        fun build(): MBCarKitServiceConfig {
            setupRealm()
            val encryptedRealm = MBRealmKit.realm(RealmUtil.ID_ENCRYPTED_REALM)
            val realm = MBRealmKit.realm(RealmUtil.ID_REALM)
            val toUsedVehicleCache = RealmVehicleCache(encryptedRealm)
            val toUsedVehicleStatusCache = RealmStatusCache(encryptedRealm)
            val toUsedImageCache = RealmVehicleImageCache(realm)
            val toUsedServiceCache = RealmServiceCache(encryptedRealm)
            val toUsedUserManagementCache = RealmUserManagementCache(encryptedRealm)
            val toUsedCommandCapabilitiesCache = RealmCommandCapabilitiesCache(encryptedRealm)
            val toUsedSendToCarCache = RealmSendToCarCache(encryptedRealm)

            val loggingInterceptor = createHttpLoggingInterceptor(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.HEADERS
                },
                com.daimler.mbloggerkit.Priority.INFO
            )

            val retrofitServiceProvider = RetrofitServiceProviderImpl(
                headerService,
                loggingInterceptor,
                pinningConfigurations,
                pinningErrorProcessor,
                pinProvider,
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
                vehicleUrl
            )

            val selectedVehicleStorage: SelectedVehicleStorage = sharedUserId?.let {
                SharedSelectedVehiclePreferencesStorage(context, it)
            } ?: SelectedVehiclePreferencesStorage(context)

            val vehicleRepo = CachedVehicleService(
                toUsedVehicleCache,
                retrofitServiceProvider.createVehicleService(),
                selectedVehicleStorage,
                toUsedCommandCapabilitiesCache
            )

            val geofencingService =
                CachedGeofencingService(retrofitServiceProvider.createGeofencingService())

            val onboardGeofencingService =
                CachedOnboardGeofencingService(retrofitServiceProvider.createOnboardGeofencingService())

            val speedfenceService =
                CachedSpeedfenceService(retrofitServiceProvider.createSpeedfenceService())

            val valetProtectService =
                CachedValetProtectService(retrofitServiceProvider.createValetProtectService())

            val merchantsRepo =
                CachedMerchantsService(retrofitServiceProvider.createOutletsService())

            val assignmentRepo =
                CachedAssignmentService(
                    vehicleRepo,
                    retrofitServiceProvider.createAssignmentService()
                )

            val imageCache =
                CachedVehicleImageService(
                    toUsedImageCache,
                    retrofitServiceProvider.createVehicleImageService()
                )

            val sendToCarRepo =
                SDKSendToCarService(
                    CachedSendToCarProvider(
                        retrofitServiceProvider.createSendToCarProvider(),
                        toUsedSendToCarCache
                    )
                )

            val speedAlertRepo =
                CachedSpeedAlertService(retrofitServiceProvider.createSpeedAlertService())

            val cachedServiceService = CachedServiceService(
                retrofitServiceProvider.createServiceService(),
                toUsedServiceCache,
                ServiceActivationStateProcessorImpl()
            )

            val serviceRepo = FilteredServiceService(cachedServiceService)

            val userManagementRepo =
                CachedUserManagementService(
                    toUsedUserManagementCache,
                    retrofitServiceProvider.createUserManagementService()
                )

            val accountLinkageRepo =
                CachedAccountLinkageService(retrofitServiceProvider.createAccountLinkageService())

            val commandVehicleApiService = CommandVehicleApiService()
            val commandVehicleApiManager =
                PinCarVehicleApiCommandManager(commandVehicleApiService, pinProvider)

            return MBCarKitServiceConfig(
                context,
                toUsedVehicleCache,
                vehicleRepo,
                geofencingService,
                onboardGeofencingService,
                speedfenceService,
                valetProtectService,
                merchantsRepo,
                assignmentRepo,
                serviceRepo,
                speedAlertRepo,
                toUsedImageCache,
                imageCache,
                toUsedVehicleStatusCache,
                toUsedServiceCache,
                commandVehicleApiManager,
                sendToCarRepo,
                commandVehicleApiService,
                selectedVehicleStorage,
                userManagementRepo,
                toUsedUserManagementCache,
                toUsedCommandCapabilitiesCache,
                toUsedSendToCarCache,
                accountLinkageRepo
            )
        }

        private fun setupRealm() {
            MBRealmKit.apply {
                createRealmInstance(
                    RealmUtil.ID_ENCRYPTED_REALM,
                    RealmServiceConfig.Builder(
                        context,
                        RealmUtil.REALM_ENCRYPTED_SCHEMA_VERSION,
                        MBCarKitRealmModule()
                    )
                        .encrypt()
                        .useDbName(RealmUtil.ENCRYPTED_FILE_NAME)
                        .build()
                )
                createRealmInstance(
                    RealmUtil.ID_REALM,
                    RealmServiceConfig.Builder(
                        context,
                        RealmUtil.REALM_SCHEMA_VERSION,
                        MBCarKitRealmModule()
                    )
                        .useDbName(RealmUtil.REALM_FILE_NAME)
                        .build()
                )
            }
        }
    }
}
