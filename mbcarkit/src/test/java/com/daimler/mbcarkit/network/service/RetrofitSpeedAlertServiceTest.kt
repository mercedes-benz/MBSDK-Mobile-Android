package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.speedalert.SpeedUnit
import com.daimler.mbcarkit.network.model.ApiGeoCoordinates
import com.daimler.mbcarkit.network.model.ApiSpeedAlertViolation
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
internal class RetrofitSpeedAlertServiceTest : BaseRetrofitServiceTest<RetrofitSpeedAlertService>() {

    private val violationsResponse = mockk<Response<List<ApiSpeedAlertViolation>>>()

    override fun createSubject(scope: CoroutineScope): RetrofitSpeedAlertService =
        RetrofitSpeedAlertService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery { fetchViolations(any(), any(), any()) } returns violationsResponse
            coEvery { deleteViolation(any(), any(), any()) } returns noContentResponse
            coEvery { deleteViolations(any(), any()) } returns noContentResponse
        }
    }

    @Test
    fun `fetchViolations() should return transformed result`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val apiCoordinates = ApiGeoCoordinates(80.0, 80.0)
        val apiViolation = ApiSpeedAlertViolation(
            "id",
            0,
            10,
            20,
            apiCoordinates
        )
        val case = ResponseTaskTestCase(violationsResponse) {
            subject.fetchViolations("", "", SpeedUnit.KMH)
        }
        scope.runBlockingTest {
            case.finish(listOf(apiViolation))
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            val violation = case.success?.firstOrNull()
            softly.assertThat(violation).isNotNull
            softly.assertThat(violation?.id).isEqualTo(apiViolation.id)
            softly.assertThat(violation?.time).isEqualTo(apiViolation.time)
            softly.assertThat(violation?.speedAlertTreshold).isEqualTo(apiViolation.speedAlertTreshold)
            softly.assertThat(violation?.speedAlertEndTime).isEqualTo(apiViolation.speedAlertEndTime)
            val coordinates = violation?.coordinate
            softly.assertThat(coordinates?.latitude).isEqualTo(apiCoordinates.latitude)
            softly.assertThat(coordinates?.longitude).isEqualTo(apiCoordinates.longitude)
        }
    }

    @Test
    fun `fetchViolations() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        violationsResponse.mockError()
        val case = ResponseTaskTestCase(violationsResponse) {
            subject.fetchViolations("", "", SpeedUnit.KMH)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteViolation() should return successfully`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteViolation("", "", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `deleteViolation() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteViolation("", "", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteViolations() should return successfully`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteViolations("", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `deleteViolations() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteViolations("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }
}
