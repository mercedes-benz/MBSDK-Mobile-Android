package com.daimler.mbcarkit.network

import android.net.ConnectivityManager
import com.daimler.mbcarkit.business.AccountLinkageService
import com.daimler.mbcarkit.business.AssignmentService
import com.daimler.mbcarkit.business.GeofencingService
import com.daimler.mbcarkit.business.OnboardGeofencingService
import com.daimler.mbcarkit.business.OutletsService
import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.SendToCarProvider
import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.SpeedAlertService
import com.daimler.mbcarkit.business.SpeedfenceService
import com.daimler.mbcarkit.business.UserManagementService
import com.daimler.mbcarkit.business.ValetProtectService
import com.daimler.mbcarkit.business.VehicleImageService
import com.daimler.mbcarkit.business.VehicleService
import com.daimler.mbcarkit.network.service.RetrofitAccountLinkageService
import com.daimler.mbcarkit.network.service.RetrofitAssignmentService
import com.daimler.mbcarkit.network.service.RetrofitGeofencingService
import com.daimler.mbcarkit.network.service.RetrofitOnboardGeofencingService
import com.daimler.mbcarkit.network.service.RetrofitOutletsService
import com.daimler.mbcarkit.network.service.RetrofitSendToCarProvider
import com.daimler.mbcarkit.network.service.RetrofitServiceService
import com.daimler.mbcarkit.network.service.RetrofitSpeedAlertService
import com.daimler.mbcarkit.network.service.RetrofitSpeedfenceService
import com.daimler.mbcarkit.network.service.RetrofitUserManagementService
import com.daimler.mbcarkit.network.service.RetrofitValetProtectService
import com.daimler.mbcarkit.network.service.RetrofitVehicleImageService
import com.daimler.mbcarkit.network.service.RetrofitVehicleService
import com.daimler.mbnetworkkit.certificatepinning.CertificateConfiguration
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinnerFactory
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningErrorProcessor
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningInterceptor
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.ConnectivityInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitServiceProviderImpl(
    private val headerService: HeaderService,
    private val loggingInterceptor: HttpLoggingInterceptor,
    private val pinningConfigurations: List<CertificateConfiguration>,
    private val pinningErrorProcessor: CertificatePinningErrorProcessor?,
    private val pinProvider: PinProvider?,
    private val connectivityManager: ConnectivityManager,
    private val vehicleUrl: String
) : RetrofitServiceProvider {

    private val vehicleApi: VehicleApi by lazy { createVehicleApi() }

    override fun createAccountLinkageService(): AccountLinkageService =
        RetrofitAccountLinkageService(vehicleApi)

    override fun createAssignmentService(): AssignmentService =
        RetrofitAssignmentService(vehicleApi)

    override fun createGeofencingService(): GeofencingService =
        RetrofitGeofencingService(vehicleApi)

    override fun createOutletsService(): OutletsService =
        RetrofitOutletsService(vehicleApi)

    override fun createSendToCarProvider(): SendToCarProvider =
        RetrofitSendToCarProvider(vehicleApi, headerService)

    override fun createServiceService(): ServiceService =
        RetrofitServiceService(vehicleApi, headerService)

    override fun createSpeedAlertService(): SpeedAlertService =
        RetrofitSpeedAlertService(vehicleApi)

    override fun createOnboardGeofencingService(): OnboardGeofencingService =
        RetrofitOnboardGeofencingService(vehicleApi)

    override fun createSpeedfenceService(): SpeedfenceService =
        RetrofitSpeedfenceService(vehicleApi)

    override fun createUserManagementService(): UserManagementService =
        RetrofitUserManagementService(vehicleApi)

    override fun createValetProtectService(): ValetProtectService =
        RetrofitValetProtectService(vehicleApi)

    override fun createVehicleImageService(): VehicleImageService =
        RetrofitVehicleImageService(vehicleApi)

    override fun createVehicleService(): VehicleService =
        RetrofitVehicleService(vehicleApi, pinProvider)

    private fun createVehicleApi() = createApi(VehicleApi::class.java, vehicleUrl)

    private fun <T> createApi(clazz: Class<T>, url: String) =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder().apply {
                    addInterceptor(ConnectivityInterceptor(connectivityManager))
                    addInterceptor(headerService.createRisHeaderInterceptor())
                    addInterceptor(loggingInterceptor)
                    if (pinningConfigurations.isNotEmpty()) {
                        certificatePinner(
                            CertificatePinnerFactory().createCertificatePinner(
                                pinningConfigurations
                            )
                        )
                        pinningErrorProcessor?.let { addInterceptor(CertificatePinningInterceptor(it)) }
                    }
                }.build()
            ).addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(clazz)
}
