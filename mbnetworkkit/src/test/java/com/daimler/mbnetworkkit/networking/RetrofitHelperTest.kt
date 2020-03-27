package com.daimler.mbnetworkkit.networking

import android.content.Context
import android.net.ConnectivityManager
import com.daimler.mbnetworkkit.certificatepinning.CertificateConfiguration
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinnerProvider
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningErrorProcessor
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningInterceptor
import com.daimler.mbnetworkkit.header.HeaderService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class RetrofitHelperTest {

    private val mockContext: Context = mockk(relaxed = true)
    private val mockHeaderService: HeaderService = mockk(relaxed = true)
    private val mockCertificatePinningErrorProcessor: CertificatePinningErrorProcessor =
        mockk(relaxed = true)
    private val mockCertificatePinnerProvider: CertificatePinnerProvider = mockk(relaxed = true)

    private val mockConnectivityManager: ConnectivityManager = mockk(relaxed = true)

    private lateinit var subject: RetrofitHelper

    @BeforeEach
    fun setup() {
        every { mockContext.getSystemService(any()) } returns mockConnectivityManager

        mockkConstructor(OkHttpClient.Builder::class)
    }

    @AfterEach
    fun teardown() {
        clearAllMocks()
    }

    fun createSubject(pinningConfigs: List<CertificateConfiguration> = emptyList()) =
        spyk(
            RetrofitHelper(
                mockContext,
                mockHeaderService,
                mockCertificatePinningErrorProcessor,
                mockCertificatePinnerProvider,
                pinningConfigs,
                true
            ),
            recordPrivateCalls = true
        )

    @Test
    fun `should create instance of Retrofit API`(softly: SoftAssertions) {
        subject = createSubject()

        val api = subject.createRetrofit(
            SampleApi::class.java,
            BASE_URL,
            true
        )

        softly.assertThat(api).isNotNull
        verify {
            subject["okHttpClient"](
                mockContext,
                true,
                mockHeaderService,
                mockCertificatePinningErrorProcessor,
                mockCertificatePinnerProvider,
                any<List<CertificateConfiguration>>(),
                any<Long>()
            )
        }
        verify { anyConstructed<OkHttpClient.Builder>().addInterceptor(any<HttpLoggingInterceptor>()) }
    }

    @Test
    fun `should create instance of Retrofit API without logging and custom timeout`(softly: SoftAssertions) {
        subject = createSubject()
        val api = subject.createRetrofit(
            SampleApi::class.java,
            BASE_URL,
            false,
            CUSTOM_TIMEOUT
        )

        softly.assertThat(api).isNotNull
        verify {
            subject["okHttpClient"](
                mockContext,
                false,
                mockHeaderService,
                mockCertificatePinningErrorProcessor,
                mockCertificatePinnerProvider,
                any<List<CertificateConfiguration>>(),
                CUSTOM_TIMEOUT
            )
        }
        verify(exactly = 0) { subject["loggingInterceptor"]() }
    }

    @Test
    fun `should create instance of Retrofit API with cert pinning`(softly: SoftAssertions) {
        subject = createSubject(listOf(mockk()))

        val api = subject.createRetrofit(
            SampleApi::class.java,
            BASE_URL,
            true,
            CUSTOM_TIMEOUT
        )

        softly.assertThat(api).isNotNull
        verify { anyConstructed<OkHttpClient.Builder>().addInterceptor(any<Interceptor>()) }
        verify { anyConstructed<OkHttpClient.Builder>().addInterceptor(any<CertificatePinningInterceptor>()) }
    }

    interface SampleApi

    private companion object {
        const val BASE_URL = "https://myurl.com/"
        const val CUSTOM_TIMEOUT = 60L
    }
}
