package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbcarkit.network.error.PinProviderNotSetError
import com.daimler.mbcarkit.network.error.PinRequestError
import com.daimler.mbcarkit.network.model.ApiAvpReservationStatus
import com.daimler.mbcarkit.network.model.ApiCommand
import com.daimler.mbcarkit.network.model.ApiCommandCapabilities
import com.daimler.mbcarkit.network.model.ApiConsumption
import com.daimler.mbcarkit.network.model.ApiDriveStatus
import com.daimler.mbcarkit.network.model.ApiDriveType
import com.daimler.mbcarkit.network.model.ApiVehicle
import com.daimler.mbcarkit.network.model.ApiVehicleSegment
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import java.sql.Date

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class)
internal class RetrofitVehicleServiceTest : BaseRetrofitServiceTest<RetrofitVehicleService>() {

    private val vehiclesResponse = mockk<Response<ArrayList<ApiVehicle>>>()
    private val capabilitiesResponse = mockk<Response<ApiCommandCapabilities>>()
    private val avpStatusResponse = mockk<Response<List<ApiAvpReservationStatus>>>()
    private val consumptionResponse = mockk<Response<ApiConsumption>>()

    private val pinProvider = mockk<PinProvider>()

    override fun createSubject(scope: CoroutineScope): RetrofitVehicleService =
        RetrofitVehicleService(vehicleApi, pinProvider, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery { fetchVehicles(any()) } returns vehiclesResponse
            coEvery {
                fetchCommandCapabilities(
                    any(),
                    any()
                )
            } returns capabilitiesResponse
            coEvery {
                resetDamageDetection(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery { acceptAvpDrive(any(), any(), any()) } returns noContentResponse
            coEvery {
                fetchAvpReservationStatus(
                    any(),
                    any(),
                    any()
                )
            } returns avpStatusResponse
            coEvery {
                updateLicensePlate(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                updateVehicleDealers(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery { fetchConsumption(any(), any()) } returns consumptionResponse
        }
    }

    @Test
    fun `fetchVehicles() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val apiVehicle = ApiVehicle(
            "fin", "vin", null, null, null,
            0, null, null, null, null,
            null, null, null, null, null, null,
            null, null, null,
            null, null, ApiVehicleSegment.AMG, null, null,
            null, null, null, null
        )
        val case = ResponseTaskTestCase(vehiclesResponse) {
            subject.fetchVehicles("")
        }
        scope.runBlockingTest {
            case.finish(ArrayList<ApiVehicle>().apply { add(apiVehicle) })
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.firstOrNull()?.fin).isEqualTo(apiVehicle.fin)
            softly.assertThat(case.success?.firstOrNull()?.vin).isEqualTo(apiVehicle.vin)
        }
    }

    @Test
    fun `fetchVehicles() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        vehiclesResponse.mockError()
        val case = ResponseTaskTestCase(vehiclesResponse) {
            subject.fetchVehicles("")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchCommandCapabilities() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val api = ApiCommandCapabilities(
            listOf(ApiCommand(null, true, null, null))
        )
        val case = ResponseTaskTestCase(capabilitiesResponse) {
            subject.fetchCommandCapabilities("", "")
        }
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.commands?.size).isEqualTo(api.commands?.size)
        }
    }

    @Test
    fun `fetchCommandCapabilities() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        capabilitiesResponse.mockError()
        val case = ResponseTaskTestCase(capabilitiesResponse) {
            subject.fetchCommandCapabilities("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `resetDamageDetection() should return successfully if valid pin given`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.resetDamageDetection("", "")
        }
        every { pinProvider.requestPin(any()) } answers {
            firstArg<PinRequest>().confirmPin("")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `resetDamageDetection() should return transformed error if no pin given`(softly: SoftAssertions) {
        every { pinProvider.requestPin(any()) } answers {
            firstArg<PinRequest>().cancel(null)
        }
        var error: ResponseError<out RequestError>? = null
        subject.resetDamageDetection("", "")
            .onFailure { error = it }
        softly.assertThat(error).isNotNull
        softly.assertThat(error?.requestError).isInstanceOf(PinRequestError::class.java)
    }

    @Test
    fun `resetDamageDetection() should return transformed error if no PinProvider given`(softly: SoftAssertions) {
        val subject = RetrofitVehicleService(vehicleApi, null)
        var error: ResponseError<out RequestError>? = null
        subject.resetDamageDetection("", "")
            .onFailure { error = it }
        softly.assertThat(error).isNotNull
        softly.assertThat(error?.requestError).isInstanceOf(PinProviderNotSetError::class.java)
    }

    @Test
    fun `resetDamageDetection() should return transformed error if valid pin given`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        every { pinProvider.requestPin(any()) } answers {
            firstArg<PinRequest>().confirmPin("")
        }
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.resetDamageDetection("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `acceptAvpDrive() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.acceptAvpDrive("", "", "", true)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `acceptAvpDrive() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.acceptAvpDrive("", "", "", true)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchAvpReservationStatus() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val api = ApiAvpReservationStatus(
            "9c787feacad84363894af83dbb39f250",
            ApiDriveType.DROP_OFF,
            ApiDriveStatus.READY,
            null,
            Date(1604985141000),
            "BERLIN"
        )
        val case = ResponseTaskTestCase(avpStatusResponse) {
            subject.fetchAvpReservationStatus(
                "",
                "",
                listOf(
                    "9c787feacad84363894af83dbb39f250",
                    "c9e885b97d1b4ab1b5d8df48e26ad290",
                    "3e9fc367db874c2da6427ae0673b4264"
                )
            )
        }
        scope.runBlockingTest {
            case.finish(listOf(api))
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.firstOrNull()?.reservationId)
                .isEqualTo(api.reservationId)
        }
    }

    @Test
    fun `fetchAvpReservationStatus() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        avpStatusResponse.mockError()
        val case = ResponseTaskTestCase(avpStatusResponse) {
            subject.fetchAvpReservationStatus("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `updateLicensePlate() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateLicensePlate("", "", "", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `updateLicensePlate() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateLicensePlate("", "", "", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `updateVehicleDealers() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateVehicleDealers("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `updateVehicleDealers() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateVehicleDealers("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchConsumption() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val api = ApiConsumption(
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val case = ResponseTaskTestCase(consumptionResponse) {
            subject.fetchConsumption("", "")
        }
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `fetchConsumption() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        consumptionResponse.mockError()
        val case = ResponseTaskTestCase(consumptionResponse) {
            subject.fetchConsumption("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }
}
