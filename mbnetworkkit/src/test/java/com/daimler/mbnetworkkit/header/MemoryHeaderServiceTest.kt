package com.daimler.mbnetworkkit.header

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class MemoryHeaderServiceTest {

    private val trackingIdProvider = mockk<TrackingIdProvider>()

    private lateinit var subject: MemoryHeaderService

    @BeforeEach
    fun setup() {
        every { trackingIdProvider.newTrackingId() } returns ""

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should use given values`(softly: SoftAssertions) {
        softly.assertThat(subject.applicationName).isEqualTo(HeaderValues.APP_NAME)
        softly.assertThat(subject.applicationVersion).isEqualTo(HeaderValues.APP_VERSION)
        softly.assertThat(subject.sdkVersion).isEqualTo(HeaderValues.SDK_VERSION)
        softly.assertThat(subject.osName).isEqualTo(HeaderValues.OS_NAME)
        softly.assertThat(subject.osVersion).isEqualTo(HeaderValues.OS_VERSION)
        softly.assertThat(subject.sessionId).isEqualTo(HeaderValues.SESSION_ID)
        softly.assertThat(subject.networkLocale).isEqualTo(HeaderValues.LOCALE)
        softly.assertThat(subject.authMode).isEqualTo(HeaderValues.AUTH_MODE)
    }

    @Test
    fun `should update values`(softly: SoftAssertions) {
        subject.networkLocale = "en-GB"
        softly.assertThat(subject.networkLocale).isEqualTo("en-GB")

        subject.authMode = "empty"
        softly.assertThat(subject.authMode).isEqualTo("empty")

        subject.updateAuthMode("val")
        softly.assertThat(subject.authMode).isEqualTo("val")
    }

    @Test
    fun `createRisHeaderInterceptor() should be correct instance`(softly: SoftAssertions) {
        val interceptor = subject.createRisHeaderInterceptor()
        softly.assertThat(interceptor).isInstanceOf(RisHeaderInterceptor::class.java)
    }

    private fun createSubject() =
        MemoryHeaderService(
            HeaderValues.APP_NAME,
            HeaderValues.APP_VERSION,
            HeaderValues.SDK_VERSION,
            HeaderValues.OS_NAME,
            HeaderValues.OS_VERSION,
            HeaderValues.SESSION_ID,
            HeaderValues.LOCALE,
            HeaderValues.AUTH_MODE,
            trackingIdProvider
        )
}
