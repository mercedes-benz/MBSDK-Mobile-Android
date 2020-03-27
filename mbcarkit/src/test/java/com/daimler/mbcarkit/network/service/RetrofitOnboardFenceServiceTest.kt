package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.network.model.ApiCustomerFence
import com.daimler.mbcarkit.network.model.ApiCustomerFenceViolation
import com.daimler.mbcarkit.network.model.ApiCustomerFenceViolations
import com.daimler.mbcarkit.network.model.ApiCustomerFences
import com.daimler.mbcarkit.network.model.ApiGeoCoordinates
import com.daimler.mbcarkit.network.model.ApiOnboardFence
import com.daimler.mbcarkit.network.model.ApiOnboardFences
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
internal class RetrofitOnboardFenceServiceTest :
    BaseRetrofitServiceTest<RetrofitOnboardGeofencingService>() {

    private val onboardFencesResponse = mockk<Response<ApiOnboardFences>>()
    private val fencesResponse = mockk<Response<ApiCustomerFences>>()
    private val violationsResponse = mockk<Response<ApiCustomerFenceViolations>>()

    override fun createSubject(scope: CoroutineScope): RetrofitOnboardGeofencingService =
        RetrofitOnboardGeofencingService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery {
                fetchOnboardFences(any(), any())
            } returns onboardFencesResponse
            coEvery {
                createOnboardFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                updateOnboardFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                deleteOnboardFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                fetchCustomerFences(any(), any())
            } returns fencesResponse
            coEvery {
                createCustomerFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                updateCustomerFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                deleteCustomerFences(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                fetchCustomerFenceViolations(
                    any(),
                    any()
                )
            } returns violationsResponse
            coEvery {
                deleteCustomerFenceViolations(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
        }
    }

    @Test
    fun `fetchOnboardFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(onboardFencesResponse) {
            subject.fetchOnboardFences("", "")
        }
        val api = emptyApiOnboardFence().copy(
            geoFenceId = 1
        )
        scope.runBlockingTest {
            case.finish(ApiOnboardFences(listOf(api)))
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                softly.assertThat(this).isNotNull
                softly.assertThat(this?.size).isEqualTo(1)
                softly.assertThat(this?.get(0)?.geoFenceId).isEqualTo(api.geoFenceId)
            }
        }
    }

    @Test
    fun `fetchOnboardFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        onboardFencesResponse.mockError()
        val case = ResponseTaskTestCase(onboardFencesResponse) {
            subject.fetchOnboardFences("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `createOnboardFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.createOnboardFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `createOnboardFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.createOnboardFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `updateOnboardFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateOnboardFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `updateOnboardFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateOnboardFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteOnboardFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteOnboardFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteOnboardFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteOnboardFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchCustomerFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(fencesResponse) {
            subject.fetchCustomerFences("", "")
        }
        val api = emptyApiCustomerFence().copy(
            geoFenceId = 1
        )
        scope.runBlockingTest {
            case.finish(ApiCustomerFences(listOf(api)))
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                softly.assertThat(this).isNotNull
                softly.assertThat(this?.size).isEqualTo(1)
                softly.assertThat(this?.get(0)?.geoFenceId).isEqualTo(api.geoFenceId)
            }
        }
    }

    @Test
    fun `fetchCustomerFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        fencesResponse.mockError()
        val case = ResponseTaskTestCase(fencesResponse) {
            subject.fetchCustomerFences("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `createCustomerFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.createCustomerFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `createCustomerFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.createCustomerFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `updateCustomerFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateCustomerFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `updateCustomerFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateCustomerFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteCustomerFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteCustomerFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteCustomerFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteCustomerFences("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchCustomerFenceViolations() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(violationsResponse) {
            subject.fetchCustomerFenceViolations("", "")
        }
        val api = ApiCustomerFenceViolation(1, 0, null, null, null)
        scope.runBlockingTest {
            case.finish(ApiCustomerFenceViolations(listOf(api)))
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                softly.assertThat(this?.size).isEqualTo(1)
                softly.assertThat(this?.get(0)?.violationId).isEqualTo(api.violationId)
            }
        }
    }

    @Test
    fun `fetchCustomerFenceViolations() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        violationsResponse.mockError()
        val case = ResponseTaskTestCase(violationsResponse) {
            subject.fetchCustomerFenceViolations("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteCustomerFenceViolations() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteCustomerFenceViolations("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteCustomerFenceViolations() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteCustomerFenceViolations("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    private fun emptyApiOnboardFence() = ApiOnboardFence(
        null,
        null,
        null,
        ApiGeoCoordinates(1.0, 2.0),
        null,
        null,
        null,
        null,
        null
    )

    private fun emptyApiCustomerFence() = ApiCustomerFence(
        null,
        null,
        null,
        emptyList(),
        0,
        1,
        null,
        null
    )
}
