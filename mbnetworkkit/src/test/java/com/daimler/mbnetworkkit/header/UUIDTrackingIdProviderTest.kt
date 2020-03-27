package com.daimler.mbnetworkkit.header

import io.mockk.clearAllMocks
import io.mockk.mockkStatic
import io.mockk.verify
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(SoftAssertionsExtension::class)
internal class UUIDTrackingIdProviderTest {

    private lateinit var subject: UUIDTrackingIdProvider

    @BeforeEach
    fun setup() {
        mockkStatic(UUID::class)

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should return randomly generated tracking id`() {
        subject.newTrackingId()
        verify {
            UUID.randomUUID()
        }
    }

    private fun createSubject() = UUIDTrackingIdProvider()
}
