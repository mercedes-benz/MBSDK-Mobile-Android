package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.ResponseTaskObject
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
internal class BaseRequestExecutorTest {

    private val apiModel = ApiModel(1)
    private lateinit var successResponse: Response<ApiModel>

    private lateinit var subject: Impl

    @BeforeEach
    fun setup() {
        successResponse = mockk<Response<ApiModel>>().also {
            every { it.isSuccessful } returns true
            every { it.code() } returns 200
            every { it.body() } returns apiModel
        }

        subject = createSubject(true)
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should return mapped response`(softly: SoftAssertions, scope: TestCoroutineScope) {
        scope.runBlockingTest {
            val call = suspend { successResponse }
            val result = subject.execute(call)
            val success = result as? RequestResult.Success
            softly.assertThat(success).isNotNull
            softly.assertThat(success?.body?.value).isEqualTo(apiModel.value)
        }
    }

    @Test
    fun `should return error on exception`(softly: SoftAssertions, scope: TestCoroutineScope) {
        scope.runBlockingTest {
            val call = suspend { throw RuntimeException("Test") }
            val result = subject.execute(call)
            val error = result as? RequestResult.Error
            softly.assertThat(error).isNotNull
        }
    }

    @Test
    fun `should return error if response can not be handled`(softly: SoftAssertions, scope: TestCoroutineScope) {
        subject = createSubject(false)
        scope.runBlockingTest {
            val call = suspend { successResponse }
            val result = subject.execute(call)
            softly.assertThat(result).isInstanceOf(RequestResult.Error::class.java)
        }
    }

    @Test
    fun `executeWithTask should dispatch mapped result`(softly: SoftAssertions, scope: TestCoroutineScope) {
        var result: Model? = null
        scope.runBlockingTest {
            val call = suspend { successResponse }
            val task = ResponseTaskObject<Model>()
            task.onComplete { result = it }
            subject.executeWithTask(task, call)
            softly.assertThat(result).isNotNull
            softly.assertThat(result?.value).isEqualTo(apiModel.value)
        }
    }

    @Test
    fun `executeWithTask should dispatch mapped error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        var error: ResponseError<out RequestError>? = null
        scope.runBlockingTest {
            val call = suspend { throw RuntimeException("Test") }
            val task = ResponseTaskObject<Model>()
            task.onFailure { error = it }
            subject.executeWithTask(task, call)
            softly.assertThat(error).isNotNull
        }
    }

    private fun createSubject(shouldHandleResponse: Boolean) = Impl(shouldHandleResponse)

    private data class ApiModel(val value: Int)

    private data class Model(val value: Int)

    private class Impl(
        private val shouldHandleResponse: Boolean = true,
    ) : BaseRequestExecutor<ApiModel, Model>(ErrorMapStrategy.Default) {

        override fun shouldHandleResponse(response: Response<ApiModel>): Boolean =
            shouldHandleResponse

        override suspend fun onHandleResponse(response: Response<ApiModel>): RequestResult<Model> =
            RequestResult.Success(Model(response.body()!!.value))
    }
}
