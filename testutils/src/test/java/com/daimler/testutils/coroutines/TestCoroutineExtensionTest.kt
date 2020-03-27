package com.daimler.testutils.coroutines

import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class)
internal class TestCoroutineExtensionTest {

    private val coroutineTest = mockk<CoroutineTest>(relaxUnitFun = true)

    private lateinit var subject: TestCoroutineExtension

    @BeforeEach
    fun setup() {
        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should attach CoroutineScope`() {
        subject.postProcessTestInstance(coroutineTest, mockk())
        verify {
            coroutineTest.attachScope(any())
        }
    }

    private fun createSubject() = TestCoroutineExtension()
}
