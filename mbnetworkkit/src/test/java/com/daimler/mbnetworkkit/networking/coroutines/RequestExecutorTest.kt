package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.mbnetworkkit.common.Mappable
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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal class RequestExecutorTest {

    private val apiModel = ApiModel(1)
    private lateinit var successResponse: Response<ApiModel>

    private lateinit var subject: RequestExecutor<ApiModel, Model>

    @BeforeEach
    fun setup() {
        successResponse = mockk<Response<ApiModel>>().also {
            every { it.isSuccessful } returns true
            every { it.code() } returns 200
            every { it.body() } returns apiModel
        }

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should return mapped result`(softly: SoftAssertions, scope: TestCoroutineScope) {
        scope.runBlockingTest {
            val call = suspend { successResponse }
            val result = subject.execute(call)
            val success = result as? RequestResult.Success
            softly.assertThat(success).isNotNull
            softly.assertThat(success?.body?.value).isEqualTo(apiModel.value)
        }
    }

    @Test
    fun `should return error if body is not available`(softly: SoftAssertions, scope: TestCoroutineScope) {
        every { successResponse.body() } returns null
        scope.runBlockingTest {
            val call = suspend { successResponse }
            val result = subject.execute(call)
            softly.assertThat(result).isInstanceOf(RequestResult.Error::class.java)
        }
    }

    @Test
    fun `should return error if body mapping failed`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val api = mockk<ApiModel>().also {
            every { it.map() } throws RuntimeException("Test")
        }
        every { successResponse.body() } returns api
        scope.runBlockingTest {
            val call = suspend { successResponse }
            val result = subject.execute(call)
            softly.assertThat(result).isInstanceOf(RequestResult.Error::class.java)
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should return error if response was not successful`(
        successful: Boolean,
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        every { successResponse.isSuccessful } returns successful
        scope.runBlockingTest {
            val call = suspend { successResponse }
            val result = subject.execute(call)
            softly.assertThat(result).isInstanceOf(
                if (successful) RequestResult.Success::class.java else RequestResult.Error::class.java
            )
        }
    }

    private fun createSubject(): RequestExecutor<ApiModel, Model> = RequestExecutor()

    private data class ApiModel(val value: Int) : Mappable<Model> {

        override fun map(): Model = Model(value)
    }

    private data class Model(val value: Int)
}
