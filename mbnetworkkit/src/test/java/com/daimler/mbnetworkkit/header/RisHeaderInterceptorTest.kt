package com.daimler.mbnetworkkit.header

import com.daimler.mbnetworkkit.CustomHeadersChain
import com.daimler.mbnetworkkit.DummyChain
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import okhttp3.Interceptor
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class RisHeaderInterceptorTest {

    private val trackingIdProvider = mockk<TrackingIdProvider>()
    private lateinit var chain: Interceptor.Chain

    private lateinit var service: RisHeaderService

    private lateinit var subject: RisHeaderInterceptor

    @BeforeEach
    fun setup() {
        every { trackingIdProvider.newTrackingId() } returns HeaderValues.TRACKING_ID

        chain = DummyChain()

        service = TestHeaderService(
            HeaderValues.APP_NAME,
            HeaderValues.APP_VERSION,
            HeaderValues.SDK_VERSION,
            HeaderValues.OS_NAME,
            HeaderValues.OS_VERSION,
            HeaderValues.SESSION_ID,
            HeaderValues.LOCALE,
            HeaderValues.AUTH_MODE
        )
        subject = createSubject(service)
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should add correct default headers`(softly: SoftAssertions) {
        subject.intercept(chain)
        val request = chain.request()
        val headers = request.headers
        softly.assertThat(headers[HEADER_APPLICATION_NAME]).isEqualTo(HeaderValues.APP_NAME)
        softly.assertThat(headers[HEADER_APPLICATION_VERSION]).isEqualTo(HeaderValues.APP_VERSION)
        softly.assertThat(headers[HEADER_SDK_VERSION]).isEqualTo(HeaderValues.SDK_VERSION)
        softly.assertThat(headers[HEADER_OS_NAME]).isEqualTo(HeaderValues.OS_NAME)
        softly.assertThat(headers[HEADER_OS_VERSION]).isEqualTo(HeaderValues.OS_VERSION)
        softly.assertThat(headers[HEADER_SESSION_ID]).isEqualTo(HeaderValues.SESSION_ID)
        softly.assertThat(headers[HEADER_LOCALE]).isEqualTo(HeaderValues.LOCALE)
        softly.assertThat(headers[HEADER_AUTH_MODE]).isEqualTo(HeaderValues.AUTH_MODE)
        softly.assertThat(headers[HEADER_TRACKING_ID]).isEqualTo(HeaderValues.TRACKING_ID)
    }

    @Test
    fun `should use changed locale`(softly: SoftAssertions) {
        service.networkLocale = "newLocale"
        subject.intercept(chain)
        val headers = chain.request().headers
        softly.assertThat(headers[HEADER_LOCALE]).isEqualTo("newLocale")
    }

    @Test
    fun `should use changed authMode`(softly: SoftAssertions) {
        service.authMode = "newAuthMode"
        subject.intercept(chain)
        val headers = chain.request().headers
        softly.assertThat(headers[HEADER_AUTH_MODE]).isEqualTo("newAuthMode")
    }

    @Test
    fun `should use values from TrackingIdProvider`(softly: SoftAssertions) {
        subject.intercept(chain)
        softly.assertThat(chain.request().headers[HEADER_TRACKING_ID])
            .isEqualTo(HeaderValues.TRACKING_ID)
        verify(exactly = 1) {
            trackingIdProvider.newTrackingId()
        }

        chain = DummyChain()
        every { trackingIdProvider.newTrackingId() } returns "newTrackingId"
        subject.intercept(chain)
        softly.assertThat(chain.request().headers[HEADER_TRACKING_ID]).isEqualTo("newTrackingId")
        verify(exactly = 2) {
            trackingIdProvider.newTrackingId()
        }
    }

    @Test
    fun `should use already available headers`(softly: SoftAssertions) {
        val key1 = "key1"
        val key2 = "key2"
        val map = mapOf(key1 to "1", key2 to "2")
        chain = CustomHeadersChain(map)
        subject.intercept(chain)
        val headers = chain.request().headers
        softly.assertThat(headers[key1]).isEqualTo(map[key1])
        softly.assertThat(headers[key2]).isEqualTo(map[key2])
    }

    @Test
    fun `should not overwrite locale if already exists`(softly: SoftAssertions) {
        assertHeaderNotOverwritten(HEADER_LOCALE, softly)
    }

    @Test
    fun `should not overwrite sessionId if already exists`(softly: SoftAssertions) {
        assertHeaderNotOverwritten(HEADER_SESSION_ID, softly)
    }

    @Test
    fun `should not overwrite trackingId if already exists`(softly: SoftAssertions) {
        assertHeaderNotOverwritten(HEADER_TRACKING_ID, softly)
    }

    @Test
    fun `should not include authMode if null`(softly: SoftAssertions) {
        service.authMode = null
        subject.intercept(chain)
        softly.assertThat(chain.request().header(HEADER_AUTH_MODE)).isNull()
    }

    @Test
    fun `should not include sessionId if null`(softly: SoftAssertions) {
        val testService = spyk(service).also {
            every { it.sessionId } returns null
        }
        subject = createSubject(testService)
        subject.intercept(chain)
        softly.assertThat(chain.request().header(HEADER_SESSION_ID)).isNull()
    }

    private fun assertHeaderNotOverwritten(
        key: String,
        softly: SoftAssertions
    ) {
        val custom = "custom"
        chain = CustomHeadersChain(mapOf(key to custom))
        subject.intercept(chain)
        softly.assertThat(chain.request().header(key)).isEqualTo(custom)
    }

    private fun createSubject(service: RisHeaderService) =
        RisHeaderInterceptor(
            service,
            trackingIdProvider
        )

    private class TestHeaderService(
        override val applicationName: String,
        override val applicationVersion: String,
        override val sdkVersion: String,
        override val osName: String,
        override val osVersion: String,
        override val sessionId: String?,
        override var networkLocale: String,
        override var authMode: String?
    ) : RisHeaderService {

        override fun updateNetworkLocale(locale: String) {
            this.networkLocale = locale
        }

        override fun updateAuthMode(authMode: String?) {
            this.authMode = authMode
        }

        override fun currentNetworkLocale(): String = networkLocale

        override fun createRisHeaderInterceptor(): Interceptor {
            throw RuntimeException("Call not supported!")
        }

        override fun createTokenHeaderInterceptor(): Interceptor {
            throw RuntimeException("Call not supported!")
        }
    }

    private companion object {
        private const val HEADER_APPLICATION_NAME = "X-ApplicationName"
        private const val HEADER_LOCALE = "X-Locale"
        private const val HEADER_AUTH_MODE = "X-AuthMode"
        private const val HEADER_APPLICATION_VERSION = "ris-application-version"
        private const val HEADER_OS_VERSION = "ris-os-version"
        private const val HEADER_OS_NAME = "ris-os-name"
        private const val HEADER_SDK_VERSION = "ris-sdk-version"
        private const val HEADER_SESSION_ID = "X-SessionId"
        private const val HEADER_TRACKING_ID = "X-TrackingId"
    }
}
