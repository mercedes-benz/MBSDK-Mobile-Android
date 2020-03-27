package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import com.daimler.testutils.coroutines.CoroutineTest
import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
internal class TopViewImageTaskTest : CoroutineTest {

    private val api = mockk<VehicleApi>()
    private val responseBody = mockk<Response<ResponseBody>>()

    private lateinit var subject: TopViewImageTask

    private lateinit var scope: CoroutineScope

    override fun attachScope(scope: CoroutineScope) {
        this.scope = scope
    }

    @BeforeEach
    fun setup() {
        responseBody.apply {
            every { isSuccessful } returns true
            every { body() } returns null
        }
        coEvery { api.fetchTopViewImages(any(), any()) } returns responseBody

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should fail if API call fails`(softly: SoftAssertions) {
        coEvery { api.fetchTopViewImages(any(), any()) } throws RuntimeException("")
        runBlocking {
            val result = subject.execute(
                TopViewImageTask.TopViewImageRequest("", 0, 0),
            )
            softly.assertThat(result).isInstanceOf(RequestResult.Error::class.java)
        }
    }

    @Test
    fun `should return success result if API call was successful`(softly: SoftAssertions) {
        runBlocking {
            val result = subject.execute(
                TopViewImageTask.TopViewImageRequest("", 0, 0),
            )
            softly.assertThat(result).isInstanceOf(RequestResult.Success::class.java)
        }
    }

    private fun createSubject(parallel: Boolean = false) =
        TopViewImageTask.create(
            "",
            api,
            parallel,
            scope
        )
}
