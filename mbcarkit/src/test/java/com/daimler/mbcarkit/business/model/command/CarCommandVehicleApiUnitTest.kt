package com.daimler.mbcarkit.business.model.command

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CarCommandVehicleApiUnitTest {

    lateinit var vehicleApiService: CommandVehicleApiService

    @Before
    fun setup() {
        vehicleApiService = CommandVehicleApiService()
    }

    @Test
    fun commandRequestAdded() {
        val commandRequest = VehicleCommand.AuxHeatStart("123456")
        vehicleApiService.commandRequestCreated(CarVehicleApiCommand(commandRequest), CommandCallback())

        assertEquals(1, vehicleApiService.getPendingCommands().count())
    }

    @Test
    fun commandRequestNotAddedTwice() {
        val commandRequest = VehicleCommand.AuxHeatStart("123456")
        vehicleApiService.commandRequestCreated(CarVehicleApiCommand(commandRequest), CommandCallback())
        vehicleApiService.commandRequestCreated(CarVehicleApiCommand(commandRequest), CommandCallback())

        assertEquals(1, vehicleApiService.getPendingCommands().count())
    }

    @Test
    fun commandRequestsAdded() {
        val commandRequest = VehicleCommand.AuxHeatStart("123456")
        vehicleApiService.commandRequestCreated(CarVehicleApiCommand(commandRequest), CommandCallback())

        val secondCommandRequest = VehicleCommand.AuxHeatStart("123456")
        vehicleApiService.commandRequestCreated(CarVehicleApiCommand(secondCommandRequest), CommandCallback())

        assertEquals(2, vehicleApiService.getPendingCommands().count())
    }

    @Test
    fun commandRequestStartedNotifiedRequest() {
        val pid = "1894"
        val commandRequest = VehicleCommand.AuxHeatStart("123456")
        var status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.PROCESSING, null, 0, 0)
        vehicleApiService.commandRequestCreated(CarVehicleApiCommand(commandRequest), CommandCallback())
        vehicleApiService.correlateRequest(commandRequest.id, pid)
        vehicleApiService.commandUpdate(status)

        assertEquals(VehicleCommandStatus.PROCESSING, status.commandState)
        assertEquals(1, vehicleApiService.getPendingCommands().count())
    }

    @Test
    fun commandUpdatedStateChanged() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("123456")
        val carCommand = CarVehicleApiCommand(commandRequest)
        val status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.ACCEPTED, null, 0, 0)
        vehicleApiService.commandRequestCreated(carCommand, CommandCallback())

        vehicleApiService.correlateRequest(commandRequest.id, pid)
        assertEquals(carCommand.state(), VehicleCommandStatus.UNKNOWN)
        vehicleApiService.commandUpdate(status)
        assertEquals(status.commandState, carCommand.state())
    }

    @Test
    fun commandUpdatedFinishedAndRemoved() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("123456")
        val carCommand = CarVehicleApiCommand(commandRequest)
        val status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.FINISHED, null, 0, 0)
        vehicleApiService.commandRequestCreated(carCommand, CommandCallback())

        vehicleApiService.correlateRequest(commandRequest.id, pid)
        assertEquals(carCommand.state(), VehicleCommandStatus.UNKNOWN)
        assertEquals(1, vehicleApiService.getPendingCommands().count())
        vehicleApiService.commandUpdate(status)
        assertEquals(status.commandState, carCommand.state())
        assertEquals(0, vehicleApiService.getPendingCommands().count())
    }

    @Test
    fun commandFailedNotifiedAndRemoved() {
        val pid = "123456"
        val failedCause = GenericCommandError.PinProviderMissing("PIN_PROVIDER_MISSING")
        val commandRequest = VehicleCommand.AuxHeatStart("123456")
        val carCommand = CarVehicleApiCommand(commandRequest)
        vehicleApiService.commandRequestCreated(carCommand, CommandCallback())

        assertEquals(1, vehicleApiService.getPendingCommands().count())
        vehicleApiService.onSendCommandFailed(carCommand, failedCause)
        assertEquals(0, vehicleApiService.getPendingCommands().count())
    }

    internal inner class CommandCallback : VehicleCommandCallback<AuxHeatStartError> {
        override fun onSuccess(timestamp: Long?) {
        }

        override fun onUpdate(status: VehicleCommandStatusUpdate) {
        }

        override fun onError(timestamp: Long?, errors: List<AuxHeatStartError>) {
        }
    }
}
