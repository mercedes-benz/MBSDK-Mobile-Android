package com.daimler.mbingresskit.implementation.etag

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ETagProviderTest {

    private val subject = mockk<ETagProvider>(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        every { subject.get(any()) } returns null
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `verify profilePicture extension property`() {
        subject.profilePicture
        verify { subject.get(ETags.PROFILE_PICTURE) }

        subject.profilePicture = ""
        verify { subject.set(ETags.PROFILE_PICTURE, "") }
    }
}
