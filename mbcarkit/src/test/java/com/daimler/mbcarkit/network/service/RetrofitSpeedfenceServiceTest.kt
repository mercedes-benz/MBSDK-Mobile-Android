package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.network.model.ApiSpeedFence
import com.daimler.mbcarkit.network.model.ApiSpeedFenceViolation
import com.daimler.mbcarkit.network.model.ApiSpeedFenceViolations
import com.daimler.mbcarkit.network.model.ApiSpeedFences
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class RetrofitSpeedfenceServiceTest :
    BaseRetrofitServiceTest<RetrofitSpeedfenceService>() {

    private val fencesResponse = mockk<Response<ApiSpeedFences>>()
    private val violationsResponse = mockk<Response<ApiSpeedFenceViolations>>()

    override fun createSubject(scope: CoroutineScope): RetrofitSpeedfenceService =
        RetrofitSpeedfenceService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery { fetchSpeedFences(any(), any()) } returns fencesResponse
            coEvery {
                createSpeedFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                updateSpeedFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                deleteSpeedFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                fetchSpeedFenceViolations(
                    any(),
                    any()
                )
            } returns violationsResponse
            coEvery {
                deleteSpeedFenceViolations(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
        }
    }

    @Test
    fun `fetchSpeedFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(fencesResponse) {
            subject.fetchSpeedFences("", "")
        }
        val api = emptyApiFence().copy(
            geoFenceId = 1,
            speedFenceId = 1
        )
        scope.runBlockingTest {
            case.finish(ApiSpeedFences(listOf(api)))
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                softly.assertThat(this).isNotNull
                softly.assertThat(this?.size).isEqualTo(1)
                softly.assertThat(this?.get(0)?.geoFenceId).isEqualTo(api.geoFenceId)
                softly.assertThat(this?.get(0)?.speedFenceId).isEqualTo(api.speedFenceId)
            }
        }
    }

    @Test
    fun `fetchSpeedFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        fencesResponse.mockError()
        val case = ResponseTaskTestCase(fencesResponse) {
            subject.fetchSpeedFences("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `createSpeedFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.createSpeedFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `createSpeedFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.createSpeedFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `updateSpeedFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateSpeedFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `updateSpeedFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateSpeedFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteSpeedFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteSpeedFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteSpeedFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteSpeedFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchSpeedFenceViolations() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(violationsResponse) {
            subject.fetchSpeedFenceViolations("", "")
        }
        val api = ApiSpeedFenceViolation(1, 0, null, null)
        scope.runBlockingTest {
            case.finish(ApiSpeedFenceViolations(listOf(api)))
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                softly.assertThat(this?.size).isEqualTo(1)
                softly.assertThat(this?.get(0)?.violationId).isEqualTo(api.violationId)
            }
        }
    }

    @Test
    fun `fetchSpeedFenceViolations() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        violationsResponse.mockError()
        val case = ResponseTaskTestCase(violationsResponse) {
            subject.fetchSpeedFenceViolations("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteSpeedFenceViolations() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteSpeedFenceViolations("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteSpeedFenceViolations() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteSpeedFenceViolations("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    private fun emptyApiFence() = ApiSpeedFence(
        null, null, null, null, null,
        null, null, null, null, null
    )
}
