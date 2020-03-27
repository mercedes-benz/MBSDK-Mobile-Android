package com.daimler.mbingresskit.implementation.network.executor

import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.Headers
import okhttp3.ResponseBody
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal class ProfilePictureRequestExecutorTest {

    private val scope = TestCoroutineScope()

    private val bytes = ByteArray(0)

    private lateinit var response: Response<ResponseBody>
    private lateinit var responseBody: ResponseBody
    private lateinit var headers: Headers

    private lateinit var subject: ProfilePictureRequestExecutor

    @BeforeEach
    fun setup() {
        headers = mockk()
        every { headers[HEADER_E_TAG] } returns E_TAG

        responseBody = mockk()
        every { responseBody.bytes() } returns bytes

        response = mockk<Response<ResponseBody>>().apply {
            every { body() } returns responseBody
            every { errorBody() } returns null
            every { headers() } returns headers
            every { isSuccessful } returns true
        }

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        scope.cleanupTestCoroutines()
        clearAllMocks()
    }

    @Test
    fun `should complete with bytes and ETag on 200`(softly: SoftAssertions) {
        every { response.code() } returns 200

        scope.runBlockingTest {
            val result = subject.execute { response }
            val success = result as? RequestResult.Success
            softly.assertThat(success).isNotNull
            val picture = success?.body as? ProfilePictureRequestExecutor.Result.Picture
            softly.assertThat(picture).isNotNull
            softly.assertThat(picture?.bytes).isEqualTo(bytes)
            softly.assertThat(picture?.eTag).isEqualTo(E_TAG)
        }
    }

    @Test
    fun `should complete with NotModified on 304`(softly: SoftAssertions) {
        every { response.code() } returns 304

        scope.runBlockingTest {
            val result = subject.execute { response }
            val success = result as? RequestResult.Success
            softly.assertThat(success).isNotNull
            val notModified = success?.body as? ProfilePictureRequestExecutor.Result.NotModified
            softly.assertThat(notModified).isNotNull
        }
    }

    @Test
    fun `should fail if response not successful`(softly: SoftAssertions) {
        scope.runBlockingTest {
            val result = subject.execute { throw RuntimeException("test") }
            val error = result as? RequestResult.Error
            softly.assertThat(error).isNotNull
        }
    }

    private fun createSubject() = ProfilePictureRequestExecutor()

    private companion object {

        private const val HEADER_E_TAG = "ETag"
        private const val E_TAG = "ETag"
    }
}
