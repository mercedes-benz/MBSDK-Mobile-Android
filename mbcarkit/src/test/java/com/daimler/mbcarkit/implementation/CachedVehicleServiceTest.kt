package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.CommandCapabilitiesCache
import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcarkit.business.VehicleService
import com.daimler.mbcarkit.business.model.command.capabilities.Command
import com.daimler.mbcarkit.business.model.command.capabilities.CommandCapabilities
import com.daimler.mbcarkit.business.model.command.capabilities.CommandName
import com.daimler.mbcarkit.business.model.consumption.Consumption
import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import com.daimler.mbcarkit.business.model.vehicle.AvpReservationStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.Vehicles
import com.daimler.mbcarkit.socket.VehicleCache
import com.daimler.mbcarkit.utils.ResponseTaskObject
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class CachedVehicleServiceTest {

    private val vehicleCache: VehicleCache = mockk()
    private val vehicleService: VehicleService = mockk()
    private val selectedVehicleStorage: SelectedVehicleStorage = mockk()
    private val commandCapabilitiesCache: CommandCapabilitiesCache = mockk()

    private lateinit var vehiclesTask: ResponseTaskObject<Vehicles>
    private lateinit var consumptionTask: ResponseTaskObject<Consumption>
    private lateinit var commandCapabilitiesTask: ResponseTaskObject<CommandCapabilities>
    private lateinit var avpReservationStatusTask: ResponseTaskObject<List<AvpReservationStatus>>
    private lateinit var unitTask: ResponseTaskObject<Unit>

    private val deletedVehiclesSlot = slot<Vehicles>()
    private val updatedVehiclesSlot = slot<Vehicles>()

    private val subject = CachedVehicleService(
        vehicleCache,
        vehicleService,
        selectedVehicleStorage,
        commandCapabilitiesCache
    )

    @BeforeEach
    fun setup() {
        vehiclesTask = ResponseTaskObject()
        consumptionTask = ResponseTaskObject()
        commandCapabilitiesTask = ResponseTaskObject()
        avpReservationStatusTask = ResponseTaskObject()
        unitTask = ResponseTaskObject()
        every { vehicleService.fetchVehicles(any()) } returns vehiclesTask
        every { vehicleService.updateLicensePlate(any(), any(), any(), any()) } returns unitTask
        every { vehicleService.updateVehicleDealers(any(), any(), any()) } returns unitTask
        every { vehicleService.fetchConsumption(any(), any()) } returns consumptionTask
        every { vehicleService.resetDamageDetection(any(), any()) } returns unitTask
        every { vehicleService.acceptAvpDrive(any(), any(), any(), any()) } returns unitTask
        every {
            vehicleService.fetchAvpReservationStatus(
                any(),
                any(),
                any()
            )
        } returns avpReservationStatusTask
        every {
            vehicleService.fetchCommandCapabilities(
                any(),
                any()
            )
        } returns commandCapabilitiesTask

        every { vehicleCache.loadVehicles() } returns Vehicles(emptyList())
        every { vehicleCache.deleteVehicles(capture(deletedVehiclesSlot)) } returns Unit
        every { vehicleCache.updateVehicles(capture(updatedVehiclesSlot)) } returns Unit

        every { selectedVehicleStorage.selectedFinOrVin() } returns ""
        every { selectedVehicleStorage.clear() } returns Unit

        every { commandCapabilitiesCache.loadFromCache(any()) } returns null
        every { commandCapabilitiesCache.writeCache(any(), any()) } returns Unit
    }

    @AfterEach
    fun clearMocks() {
        clearAllMocks()
    }

    @Test
    fun `fetchVehicles with service response should remove unassigned vehicles from cache`(softly: SoftAssertions) {
        var success: Vehicles? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchVehicles("")
            .onComplete { success = it }
            .onFailure { failure = it }

        val vehicle1 = getVehicleInfo("1", AssignmentPendingState.DELETE)
        val vehicle2 = getVehicleInfo("2", AssignmentPendingState.NONE)
        val vehicle3 = getVehicleInfo("3", AssignmentPendingState.ASSIGN)
        val vehicle4 = getVehicleInfo("4")
        every { vehicleCache.loadVehicles() } returns Vehicles(listOf(vehicle1, vehicle2, vehicle3))

        val vehicles = Vehicles(listOf(vehicle4))
        vehiclesTask.complete(vehicles)

        verify { vehicleCache.deleteVehicles(any()) }
        softly.assertThat(deletedVehiclesSlot.isCaptured).isTrue
        softly.assertThat(deletedVehiclesSlot.captured.vehicles).containsExactly(vehicle1, vehicle2)

        softly.assertThat(success).isNotNull
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicles with service response should update cached values`(softly: SoftAssertions) {
        var success: Vehicles? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchVehicles("")
            .onComplete { success = it }
            .onFailure { failure = it }

        val vehicle1 = getVehicleInfo("1")
        val vehicle2 = getVehicleInfo("2")
        val vehicle1Updated = getVehicleInfo("1")
        val vehicle2Updated = getVehicleInfo("2")
        every { vehicleCache.loadVehicles() } returns Vehicles(listOf(vehicle1, vehicle2))

        val vehicles = Vehicles(listOf(vehicle1Updated, vehicle2Updated))
        vehiclesTask.complete(vehicles)

        softly.assertThat(updatedVehiclesSlot.isCaptured).isTrue
        softly.assertThat(updatedVehiclesSlot.captured.vehicles).containsExactly(vehicle1, vehicle2)

        softly.assertThat(success).isNotNull
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicles with service response should deselect selected vehicle if removed`(softly: SoftAssertions) {
        var success: Vehicles? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchVehicles("")
            .onComplete { success = it }
            .onFailure { failure = it }

        val vehicle1 = getVehicleInfo("1")
        val vehicle2 = getVehicleInfo("2")
        every { vehicleCache.loadVehicles() } returns Vehicles(listOf(vehicle1, vehicle2))
        every { selectedVehicleStorage.selectedFinOrVin() } returns vehicle1.vin

        val vehicles = Vehicles(listOf(vehicle2))
        vehiclesTask.complete(vehicles)

        verify { selectedVehicleStorage.clear() }

        softly.assertThat(success).isNotNull
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicles with service failure should return cached values`(softly: SoftAssertions) {
        var success: Vehicles? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchVehicles("")
            .onComplete { success = it }
            .onFailure { failure = it }

        val vehicles = Vehicles(listOf(getVehicleInfo("1")))
        every { vehicleCache.loadVehicles() } returns vehicles

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        vehiclesTask.fail(responseError)

        softly.assertThat(success).isEqualTo(vehicles)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `updateLicensePlate should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.updateLicensePlate("", "", "", ""))
    }

    @Test
    fun `updateVehicleDealers should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.updateVehicleDealers("", "", emptyList()))
    }

    @Test
    fun `fetchConsumption should return retrofit response`() {
        Assertions.assertEquals(consumptionTask, subject.fetchConsumption("", ""))
    }

    @Test
    fun `fetchCommandCapabilities with cached values should return cached values and update caches`(
        softly: SoftAssertions
    ) {
        var success: CommandCapabilities? = null
        var failure: ResponseError<out RequestError>? = null

        val command1 = Command(CommandName.AUXHEAT_START, true, emptyList(), emptyList())
        val command2 = Command(CommandName.AUXHEAT_STOP, true, emptyList(), emptyList())
        val cachedCapabilities = CommandCapabilities(listOf(command1, command2))
        every { commandCapabilitiesCache.loadFromCache(any()) } returns cachedCapabilities

        subject.fetchCommandCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        softly.assertThat(success).isEqualTo(cachedCapabilities)
        softly.assertThat(failure).isNull()

        val command3 = Command(CommandName.CHARGE_OPT_START, true, emptyList(), emptyList())
        val command4 = Command(CommandName.CHARGE_OPT_STOP, true, emptyList(), emptyList())
        val freshCapabilities = CommandCapabilities(listOf(command3, command4))
        commandCapabilitiesTask.complete(freshCapabilities)

        verify { commandCapabilitiesCache.writeCache(any(), eq(freshCapabilities)) }
    }

    @Test
    fun `fetchCommandCapabilities without cached values should return fresh values`(softly: SoftAssertions) {
        var success: CommandCapabilities? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchCommandCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val command1 = Command(CommandName.CHARGE_OPT_START, true, emptyList(), emptyList())
        val command2 = Command(CommandName.CHARGE_OPT_STOP, true, emptyList(), emptyList())
        val freshCapabilities = CommandCapabilities(listOf(command1, command2))
        commandCapabilitiesTask.complete(freshCapabilities)

        verify { commandCapabilitiesCache.writeCache(any(), eq(freshCapabilities)) }

        softly.assertThat(success).isEqualTo(freshCapabilities)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchCommandCapabilities without cached values with service failure should fail task`(
        softly: SoftAssertions
    ) {
        var success: CommandCapabilities? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchCommandCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        commandCapabilitiesTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `createOrUpdateAssigningVehicle should update vehicle cache`(softly: SoftAssertions) {
        every { vehicleCache.loadVehicleByVin(any()) } returns getVehicleInfo(
            "1",
            AssignmentPendingState.NONE
        )
        subject.createOrUpdateAssigningVehicle("1")

        verify { vehicleCache.updateVehicles(any()) }

        softly.assertThat(updatedVehiclesSlot.isCaptured).isTrue
        softly.assertThat(updatedVehiclesSlot.captured.vehicles[0].assignmentState)
            .isEqualTo(AssignmentPendingState.ASSIGN)
    }

    @Test
    fun `createOrUpdateAssigningVehicle without cached vehicle should create and update vehicle cache`(
        softly: SoftAssertions
    ) {
        every { vehicleCache.loadVehicleByVin(any()) } returns null
        subject.createOrUpdateAssigningVehicle("1")

        verify { vehicleCache.updateVehicles(any()) }

        softly.assertThat(updatedVehiclesSlot.isCaptured).isTrue
        softly.assertThat(updatedVehiclesSlot.captured.vehicles[0].assignmentState)
            .isEqualTo(AssignmentPendingState.ASSIGN)
    }

    @Test
    fun `createOrUpdateUnassigningVehicle should update vehicle cache`(softly: SoftAssertions) {
        every { vehicleCache.loadVehicleByVin(any()) } returns getVehicleInfo(
            "1",
            AssignmentPendingState.NONE
        )
        subject.createOrUpdateUnassigningVehicle("1")

        verify { vehicleCache.updateVehicles(any()) }

        softly.assertThat(updatedVehiclesSlot.isCaptured).isTrue
        softly.assertThat(updatedVehiclesSlot.captured.vehicles[0].assignmentState)
            .isEqualTo(AssignmentPendingState.DELETE)
    }

    @Test
    fun `createOrUpdateUnassigningVehicle without cached vehicle should create and update vehicle cache`(
        softly: SoftAssertions
    ) {
        every { vehicleCache.loadVehicleByVin(any()) } returns null
        every { selectedVehicleStorage.selectedFinOrVin() } returns "1"
        subject.createOrUpdateUnassigningVehicle("1")

        verify { vehicleCache.updateVehicles(any()) }
        verify { selectedVehicleStorage.clear() }

        softly.assertThat(updatedVehiclesSlot.isCaptured).isTrue
        softly.assertThat(updatedVehiclesSlot.captured.vehicles[0].assignmentState)
            .isEqualTo(AssignmentPendingState.DELETE)
    }

    private fun getVehicleInfo(
        vin: String,
        assignmentPendingState: AssignmentPendingState? = null
    ) =
        VehicleInfo(
            vin, "", "", "", assignmentPendingState,
            0, "", null, emptyList(), null,
            null, null, null, null,
            false, false, null, null,
            null, null, null,
            null, null, false, null,
            false, null, null, null,
            null, null, null
        )
}
