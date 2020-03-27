package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.assignment.AssignmentPreconditionError
import com.daimler.mbcarkit.business.model.assignment.AssignmentType
import com.daimler.mbcarkit.network.model.ApiAssignmentPreconditionError
import com.daimler.mbcarkit.network.model.ApiAssignmentType
import com.daimler.mbcarkit.network.model.ApiQrAssignmentResponse
import com.daimler.mbcarkit.network.model.ApiRifability
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.every
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
internal class RetrofitAssignmentServiceTest : BaseRetrofitServiceTest<RetrofitAssignmentService>() {

    private val assignQrResponse = mockk<Response<ApiQrAssignmentResponse>>()
    private val rifabilityResponse = mockk<Response<ApiRifability>>()

    override fun createSubject(scope: CoroutineScope): RetrofitAssignmentService =
        RetrofitAssignmentService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery { assignVehicleWithQrCode(any(), any(), any()) } returns assignQrResponse
            coEvery { assignVehicleByVin(any(), any()) } returns rifabilityResponse
            coEvery { confirmVehicleAssignmentWithVac(any(), any(), any()) } returns noContentResponse
            coEvery { unassignVehicleByVin(any(), any()) } returns noContentResponse
            coEvery { fetchRifability(any(), any()) } returns rifabilityResponse
        }
    }

    @Test
    fun `assignVehicleWithQrCode() should return transformed data`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val apiQr = ApiQrAssignmentResponse(
            "finOrVin",
            ApiAssignmentType.OWNER,
            "model"
        )
        val case = ResponseTaskTestCase(assignQrResponse) {
            subject.assignVehicleWithQrCode("", "", null)
        }
        scope.runBlockingTest {
            case.finish(apiQr)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.finOrVin).isEqualTo(apiQr.vin)
            softly.assertThat(case.success?.model).isEqualTo(apiQr.model)
            softly.assertThat(case.success?.type).isEqualTo(AssignmentType.OWNER)
        }
    }

    @Test
    fun `assignVehicleWithQrCode() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        assignQrResponse.mockError()
        val case = ResponseTaskTestCase(assignQrResponse) {
            subject.assignVehicleWithQrCode("", "", null)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `assignVehicleWithQrCode() should return correct precondition error in case of 412`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val api = ApiAssignmentPreconditionError(
            "finOrVin",
            null,
            true,
            false,
            null,
            null,
            null
        )
        assignQrResponse.apply {
            every { isSuccessful } returns false
            every { code() } returns 412
            every { errorBody()?.string() } returns Gson().toJson(api)
        }
        val case = ResponseTaskTestCase(assignQrResponse) {
            subject.assignVehicleWithQrCode("", "", null)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            val preconditionError = case.error?.requestError as? AssignmentPreconditionError
            softly.assertThat(preconditionError).isNotNull
            softly.assertThat(preconditionError?.finOrVin).isEqualTo(api.finOrVin)
            softly.assertThat(preconditionError?.termsOfUseRequired).isTrue
            softly.assertThat(preconditionError?.mePinRequired).isFalse
        }
    }

    @Test
    fun `assignVehicleByVin() should return transformed data`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val api = ApiRifability(
            false,
            false,
            false,
            true,
            null
        )
        val case = ResponseTaskTestCase(rifabilityResponse) {
            subject.assignVehicleByVin("", "")
        }
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(api.canCarReceiveVacs).isTrue
        }
    }

    @Test
    fun `assignVehicleByVin() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        rifabilityResponse.mockError()
        val case = ResponseTaskTestCase(rifabilityResponse) {
            subject.assignVehicleByVin("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `confirmVehicleAssignmentWithVac should return transformed data`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.confirmVehicleAssignmentWithVac("", "", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `confirmVehicleAssignmentWithVac() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.confirmVehicleAssignmentWithVac("", "", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `confirmAssignmentWithVac() should return correct precondition error in case of 412`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val api = ApiAssignmentPreconditionError(
            "finOrVin",
            null,
            true,
            false,
            null,
            null,
            null
        )
        noContentResponse.apply {
            every { isSuccessful } returns false
            every { code() } returns 412
            every { errorBody()?.string() } returns Gson().toJson(api)
        }
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.confirmVehicleAssignmentWithVac("", "", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            val preconditionError = case.error?.requestError as? AssignmentPreconditionError
            softly.assertThat(preconditionError).isNotNull
            softly.assertThat(preconditionError?.finOrVin).isEqualTo(api.finOrVin)
            softly.assertThat(preconditionError?.termsOfUseRequired).isTrue
            softly.assertThat(preconditionError?.mePinRequired).isFalse
        }
    }

    @Test
    fun `unassignVehicleByVin() should return transformed data`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.unassignVehicleByVin("", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `fetchRifability() should return transformed data`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val api = ApiRifability(
            true,
            null,
            null,
            null,
            null
        )
        val case = ResponseTaskTestCase(rifabilityResponse) {
            subject.fetchRifability("", "")
        }
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.isRifable).isTrue
        }
    }

    @Test
    fun `fetchRifability() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        rifabilityResponse.mockError()
        val case = ResponseTaskTestCase(rifabilityResponse) {
            subject.fetchRifability("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }
}
