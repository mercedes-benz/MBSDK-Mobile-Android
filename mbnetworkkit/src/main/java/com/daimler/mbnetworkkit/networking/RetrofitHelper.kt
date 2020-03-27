package com.daimler.mbnetworkkit.networking

import android.content.Context
import android.net.ConnectivityManager
import com.daimler.mbloggerkit.Priority
import com.daimler.mbnetworkkit.certificatepinning.CertificateConfiguration
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinnerProvider
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningErrorProcessor
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningInterceptor
import com.daimler.mbnetworkkit.header.HeaderService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper(
    private val context: Context,
    private val headerService: HeaderService,
    private val certificatePinningErrorProcessor: CertificatePinningErrorProcessor?,
    private val certificatePinnerProvider: CertificatePinnerProvider,
    private val pinningConfigurations: List<CertificateConfiguration>,
    private val shouldLogBody: Boolean
) {

    fun <T> createRetrofit(
        api: Class<T>,
        baseUrl: String,
        enableLogging: Boolean,
        timeoutInSeconds: Long = DEFAULT_TIMEOUT
    ): T =
        Retrofit.Builder()
            .client(
                okHttpClient(
                    context,
                    enableLogging,
                    headerService,
                    certificatePinningErrorProcessor,
                    certificatePinnerProvider,
                    pinningConfigurations,
                    timeoutInSeconds
                )
            ).addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build().create(api)

    private fun okHttpClient(
        context: Context,
        enableLogging: Boolean,
        headerService: HeaderService,
        certificatePinningErrorProcessor: CertificatePinningErrorProcessor?,
        certificatePinnerProvider: CertificatePinnerProvider,
        pinningConfigurations: List<CertificateConfiguration>,
        timeoutInSeconds: Long
    ) = OkHttpClient.Builder().apply {
        readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        addInterceptor(ConnectivityInterceptor(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager))
        addInterceptor(headerService.createRisHeaderInterceptor())
        addInterceptor(headerService.createTokenHeaderInterceptor())
        if (enableLogging) addInterceptor(loggingInterceptor())
        if (pinningConfigurations.isNotEmpty()) {
            certificatePinner(
                certificatePinnerProvider.createCertificatePinner(
                    pinningConfigurations
                )
            )
            certificatePinningErrorProcessor?.let { addInterceptor(CertificatePinningInterceptor(it)) }
        }
    }.build()

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return createHttpLoggingInterceptor(
            if (shouldLogBody) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.HEADERS,
            Priority.INFO
        )
    }

    companion object {
        const val DEFAULT_TIMEOUT = 10L
        const val LONG_TIMEOUT = 60L
    }
}
