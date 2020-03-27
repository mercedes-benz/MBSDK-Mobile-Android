package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.geofencing.ActiveTimes
import com.daimler.mbcarkit.business.model.geofencing.Fence
import com.daimler.mbcarkit.business.model.geofencing.Shape
import com.daimler.mbcarkit.network.model.ApiActiveTimes
import com.daimler.mbcarkit.network.model.ApiFence
import com.daimler.mbcarkit.network.model.ApiGeofenceViolation
import com.daimler.mbcarkit.network.model.ApiShape
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
internal class RetrofitGeofencingServiceTest :
    BaseRetrofitServiceTest<RetrofitGeofencingService>() {

    private val fencesResponse = mockk<Response<List<ApiFence>>>()
    private val fenceResponse = mockk<Response<ApiFence>>()
    private val violationsResponse = mockk<Response<List<ApiGeofenceViolation>>>()

    override fun createSubject(scope: CoroutineScope): RetrofitGeofencingService =
        RetrofitGeofencingService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery { fetchAllFences(any(), any()) } returns fencesResponse
            coEvery { createFence(any(), any(), any()) } returns fenceResponse
            coEvery { fetchFenceById(any(), any()) } returns fenceResponse
            coEvery { updateFence(any(), any(), any()) } returns noContentResponse
            coEvery { deleteFence(any(), any()) } returns noContentResponse
            coEvery { fetchAllVehicleFences(any(), any()) } returns fencesResponse
            coEvery {
                activateVehicleFence(
                    any(),
                    any(),
                    any(),
                )
            } returns noContentResponse
            coEvery {
                deleteVehicleFence(
                    any(),
                    any(),
                    any(),
                )
            } returns noContentResponse
            coEvery { fetchAllViolations(any(), any()) } returns violationsResponse
            coEvery { deleteAllViolations(any(), any()) } returns noContentResponse
            coEvery {
                deleteGeofencingViolation(
                    any(),
                    any(),
                    any(),
                )
            } returns noContentResponse
        }
    }

    @Test
    fun `fetchAllFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(fencesResponse) {
            subject.fetchAllFences("", "")
        }
        val api = listOf(emptyApiFence())
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `fetchAllFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        fencesResponse.mockError()
        val case = ResponseTaskTestCase(fencesResponse) {
            subject.fetchAllFences("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `createFence() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(fenceResponse) {
            subject.createFence("", emptyFence(), "")
        }
        val api = emptyApiFence()
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `createFence() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        fenceResponse.mockError()
        val case = ResponseTaskTestCase(fenceResponse) {
            subject.createFence("", emptyFence(), "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchFenceById() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(fenceResponse) {
            subject.fetchFenceById("", 0)
        }
        val api = emptyApiFence()
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `fetchFenceById() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        fenceResponse.mockError()
        val case = ResponseTaskTestCase(fenceResponse) {
            subject.fetchFenceById("", 0)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `updateFence() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateFence("", 0, emptyFence())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `updateFence() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateFence("", 0, emptyFence())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteFence() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteFence("", 0)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteFence() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteFence("", 0)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchAllVehicleFences() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(fencesResponse) {
            subject.fetchAllVehicleFences("", "")
        }
        val api = listOf(emptyApiFence())
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `fetchAllVehicleFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        fencesResponse.mockError()
        val case = ResponseTaskTestCase(fencesResponse) {
            subject.fetchAllVehicleFences("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `activateVehicleFence() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.activateVehicleFence("", "", 0)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `activateVehicleFence() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.activateVehicleFence("", "", 0)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteVehicleFence() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteVehicleFence("", "", 0)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteVehicleFence() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteVehicleFence("", "", 0)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchAllViolations() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(violationsResponse) {
            subject.fetchAllViolations("", "")
        }
        scope.runBlockingTest {
            case.finish(emptyList())
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `fetchAllViolations() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        violationsResponse.mockError()
        val case = ResponseTaskTestCase(violationsResponse) {
            subject.fetchAllViolations("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteAllViolations() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteAllViolations("", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteAllViolations() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteAllViolations("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteGeofencingViolation() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteViolation("", "", 0)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteGeofencingViolation() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteViolation("", "", 0)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    private fun emptyApiFence() = ApiFence(
        null,
        null,
        null,
        ApiActiveTimes(emptyList(), 0, 0),
        null,
        ApiShape(null, null)
    )

    private fun emptyFence() = Fence(
        null,
        null,
        "",
        ActiveTimes(emptyList(), 0, 0),
        null,
        Shape(null, null)
    )
}
