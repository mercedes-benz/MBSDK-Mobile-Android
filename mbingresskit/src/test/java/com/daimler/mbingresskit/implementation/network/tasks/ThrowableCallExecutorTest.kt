package com.daimler.mbingresskit.implementation.network.tasks

import com.daimler.mbnetworkkit.networking.exception.ResponseException
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
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal class ThrowableCallExecutorTest {

    private val apiModel = ApiModel(1)
    private lateinit var successResponse: Response<ApiModel>

    private lateinit var subject: ThrowableCallExecutor<ApiModel>

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
    fun `should return response`(softly: SoftAssertions, scope: TestCoroutineScope) {
        scope.runBlockingTest {
            val call = suspend { successResponse }
            val result = subject.execute(call)
            val success = result as? ThrowableCallExecutor.Result.Success
            softly.assertThat(success).isNotNull
            softly.assertThat(success?.body).isEqualTo(apiModel)
        }
    }

    @Test
    fun `should return error on exception`(softly: SoftAssertions, scope: TestCoroutineScope) {
        scope.runBlockingTest {
            val exception = RuntimeException("test")
            val call = suspend { throw exception }
            val result = subject.execute(call)
            val error = result as? ThrowableCallExecutor.Result.Error
            softly.assertThat(error?.error).isEqualTo(exception)
        }
    }

    @Test
    fun `should return error if response was not successful`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val response = mockk<Response<ApiModel>>().also {
            every { it.isSuccessful } returns false
            every { it.code() } returns 400
            every { it.errorBody() } returns null
        }
        scope.runBlockingTest {
            val call = suspend { response }
            val result = subject.execute(call)
            val error = result as? ThrowableCallExecutor.Result.Error
            softly.assertThat(error).isNotNull
            val responseException = error?.error as? ResponseException
            softly.assertThat(responseException).isNotNull
            softly.assertThat(responseException?.responseCode).isEqualTo(400)
        }
    }

    @Test
    fun `should return error if response body is null`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val response = mockk<Response<ApiModel>>().also {
            every { it.isSuccessful } returns true
            every { it.code() } returns 200
            every { it.body() } returns null
        }
        scope.runBlockingTest {
            val call = suspend { response }
            val result = subject.execute(call)
            val error = result as? ThrowableCallExecutor.Result.Error
            softly.assertThat(error).isNotNull
            val responseException = error?.error as? ResponseException
            softly.assertThat(responseException).isNotNull
            softly.assertThat(responseException?.responseCode).isEqualTo(200)
        }
    }

    private fun createSubject() = ThrowableCallExecutor<ApiModel>()

    private data class ApiModel(val value: Int)
}
