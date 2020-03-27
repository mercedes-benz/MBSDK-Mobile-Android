package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.AssignmentService
import com.daimler.mbcarkit.business.model.assignment.AssignmentType
import com.daimler.mbcarkit.business.model.assignment.QRAssignment
import com.daimler.mbcarkit.business.model.rif.Rifability
import com.daimler.mbcarkit.utils.ResponseTaskObject
import com.daimler.mbcarkit.utils.ResponseTaskObjectUnit
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class CachedAssignmentServiceTest {

    private val vehicleService: CachedVehicleService = mockk()
    private val assignmentService: AssignmentService = mockk()

    private lateinit var qrAssignmentTask: ResponseTaskObject<QRAssignment>
    private lateinit var vinAssignmentTask: ResponseTaskObject<Rifability>
    private lateinit var confirmAssignmentTask: ResponseTaskObjectUnit
    private lateinit var unAssignmentTask: ResponseTaskObjectUnit
    private lateinit var fetchRifabilityTask: ResponseTaskObject<Rifability>

    private val subject: CachedAssignmentService = CachedAssignmentService(
        vehicleService,
        assignmentService
    )

    @BeforeEach
    fun setup() {
        qrAssignmentTask = ResponseTaskObject()
        vinAssignmentTask = ResponseTaskObject()
        confirmAssignmentTask = ResponseTaskObject()
        unAssignmentTask = ResponseTaskObject()
        fetchRifabilityTask = ResponseTaskObject()

        every { assignmentService.assignVehicleWithQrCode(any(), any(), any()) } returns qrAssignmentTask
        every { assignmentService.assignVehicleByVin(any(), any()) } returns vinAssignmentTask
        every { assignmentService.confirmVehicleAssignmentWithVac(any(), any(), any()) } returns confirmAssignmentTask
        every { assignmentService.unassignVehicleByVin(any(), any()) } returns unAssignmentTask
        every { assignmentService.fetchRifability(any(), any()) } returns fetchRifabilityTask

        every { vehicleService.createOrUpdateAssigningVehicle(any()) } returns true
        every { vehicleService.createOrUpdateUnassigningVehicle(any()) } returns true
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `assignVehicleWithQrCode with qrAssignment response should update vehicleCache`(softly: SoftAssertions) {
        var success: QRAssignment? = null
        var failure: ResponseError<out RequestError>? = null

        subject.assignVehicleWithQrCode("", "", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val qrAssignment = QRAssignment("finOrVin", AssignmentType.OWNER, "model")
        qrAssignmentTask.complete(qrAssignment)

        verify { vehicleService.createOrUpdateAssigningVehicle(any()) }

        softly.assertThat(success).isEqualTo(qrAssignment)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `assignVehicleWithQrCode with assignment failure should propagate failure`(softly: SoftAssertions) {
        var success: QRAssignment? = null
        var failure: ResponseError<out RequestError>? = null

        subject.assignVehicleWithQrCode("", "", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        qrAssignmentTask.fail(responseError)

        verify(inverse = true) { vehicleService.createOrUpdateAssigningVehicle(any()) }

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `assignVehicleByVin with assignment response should update vehicleCache`(softly: SoftAssertions) {
        var success: Rifability? = null
        var failure: ResponseError<out RequestError>? = null

        subject.assignVehicleByVin("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val rifability = Rifability(true, true)
        vinAssignmentTask.complete(rifability)

        verify { vehicleService.createOrUpdateAssigningVehicle(any()) }

        softly.assertThat(success).isEqualTo(rifability)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `assignVehicleByVin with assignment failure should propagate failure`(softly: SoftAssertions) {
        var success: Rifability? = null
        var failure: ResponseError<out RequestError>? = null

        subject.assignVehicleByVin("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        vinAssignmentTask.fail(responseError)

        verify(inverse = true) { vehicleService.createOrUpdateAssigningVehicle(any()) }

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `confirmVehicleAssignmentWithVac with confirmation response should update vehicleCache`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.confirmVehicleAssignmentWithVac("", "", "")
            .onComplete { success = true }
            .onFailure { failure = it }

        confirmAssignmentTask.complete(Unit)

        verify { vehicleService.createOrUpdateAssigningVehicle(any()) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `confirmVehicleAssignmentWithVac with confirmation failure should propagate failure`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.confirmVehicleAssignmentWithVac("", "", "")
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        confirmAssignmentTask.fail(responseError)

        verify(inverse = true) { vehicleService.createOrUpdateAssigningVehicle(any()) }

        softly.assertThat(success).isFalse()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `unassignVehicleByVin with unassign response should update vehicleCache`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.unassignVehicleByVin("", "")
            .onComplete { success = true }
            .onFailure { failure = it }

        unAssignmentTask.complete(Unit)

        verify { vehicleService.createOrUpdateUnassigningVehicle(any()) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `unassignVehicleByVin with unassign failure should propagate failure`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.unassignVehicleByVin("", "")
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        unAssignmentTask.fail(responseError)

        verify(inverse = true) { vehicleService.createOrUpdateUnassigningVehicle(any()) }

        softly.assertThat(success).isFalse()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `fetchRifability should return retrofit response`(softly: SoftAssertions) {
        Assertions.assertEquals(fetchRifabilityTask, subject.fetchRifability("", ""))
    }
}
