package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal class NoContentRequestExecutorTest {

    private lateinit var subject: NoContentRequestExecutor

    @BeforeEach
    fun setup() {
        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should return success if response was successful`(
        successful: Boolean,
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val response = mockk<Response<Unit>>().also {
            every { it.isSuccessful } returns successful
        }
        scope.runBlockingTest {
            val call = suspend { response }
            val result = subject.execute(call)
            softly.assertThat(result).isInstanceOf(
                if (successful) RequestResult.Success::class.java else RequestResult.Error::class.java
            )
        }
    }

    private fun createSubject() = NoContentRequestExecutor()
}
