package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.header.HeaderValues
import io.mockk.clearAllMocks
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class MBNetworkKitTest {

    private lateinit var config: NetworkServiceConfig
    private lateinit var subject: MBNetworkKit

    @BeforeEach
    fun setup() {
        config = NetworkServiceConfig.Builder(
            HeaderValues.APP_NAME,
            HeaderValues.APP_VERSION,
            HeaderValues.SDK_VERSION
        ).apply {
            useAuthMode(HeaderValues.AUTH_MODE)
            useLocale(HeaderValues.LOCALE)
            useOSVersion(HeaderValues.OS_VERSION)
        }.build()

        subject = createSubject().also {
            it.init(config)
        }
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should create correct header service`(softly: SoftAssertions) {
        val headerService = subject.headerService()
        softly.assertThat(headerService.authMode).isEqualTo(HeaderValues.AUTH_MODE)
        softly.assertThat(headerService.networkLocale).isEqualTo(HeaderValues.LOCALE)

        val custom = "custom"
        subject.headerService().authMode = custom
        subject.headerService().networkLocale = custom
        softly.assertThat(headerService.authMode).isEqualTo(custom)
        softly.assertThat(headerService.networkLocale).isEqualTo(custom)

        subject.headerService().authMode = null
        softly.assertThat(headerService.authMode).isNull()
    }

    private fun createSubject() = MBNetworkKit
}
