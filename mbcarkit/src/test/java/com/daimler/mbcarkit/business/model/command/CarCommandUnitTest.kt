package com.daimler.mbcarkit.business.model.command

import com.daimler.mbcarkit.business.model.command.GenericCommandError.CommandFailed
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CarCommandUnitTest {
    private lateinit var commandService: CommandVehicleApiService
    private var lastStatus: VehicleCommandStatus? = null
    private var lastErrors: List<*>? = null
    private val commandCallback = CommandCallback()

    @Before
    fun setup() {
        commandService = CommandVehicleApiService()
        lastStatus = null
    }

    @Test
    fun commandRequestAdded() {
        val commandRequest = VehicleCommand.AuxHeatStart("")
        commandService.commandRequestCreated(CarVehicleApiCommand(commandRequest), commandCallback)
        Assert.assertEquals(1, commandService.getPendingCommands().size.toLong())
    }

    @Test
    fun commandRequestStartedNotifiedProgress() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.PROCESSING, "uid", 0, 0)
        commandService.commandRequestCreated(CarVehicleApiCommand(commandRequest), commandCallback)
        commandService.correlateRequest(commandRequest.id, pid)
        commandService.commandUpdate(status)
        Assert.assertTrue(lastStatus === VehicleCommandStatus.PROCESSING)
    }

    @Test
    fun commandStarted() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val carCommand: CarVehicleApiCommand<AuxHeatStartError> = CarVehicleApiCommand(commandRequest)
        commandService.commandRequestCreated(carCommand, commandCallback)
        commandService.correlateRequest(commandRequest.id, pid)
        Assert.assertEquals(carCommand.processId(), pid)
    }

    @Test
    fun commandUpdatedStateChanged() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val carCommand: CarVehicleApiCommand<AuxHeatStartError> = CarVehicleApiCommand(commandRequest)
        val status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.ABOUT_TO_SEND, "uid", 0, 0)
        commandService.commandRequestCreated(carCommand, commandCallback)
        commandService.correlateRequest(commandRequest.id, pid)
        commandService.commandUpdate(status)
        Assert.assertEquals(status.commandState, carCommand.state())
    }

    @Test
    fun commandUpdatedAndNotified() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val carCommand: CarVehicleApiCommand<AuxHeatStartError> = CarVehicleApiCommand(commandRequest)
        val status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.ABOUT_TO_SEND, "uid", 0, 0)
        commandService.commandRequestCreated(carCommand, commandCallback)
        commandService.correlateRequest(commandRequest.id, pid)
        commandService.commandUpdate(status)
        Assert.assertEquals(VehicleCommandStatus.ABOUT_TO_SEND, lastStatus)
    }

    @Test
    fun commandFinishedNotified() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val carCommand: CarVehicleApiCommand<AuxHeatStartError> = CarVehicleApiCommand(commandRequest)
        val status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.FINISHED, "uid", 0, 0)
        commandService.commandRequestCreated(carCommand, commandCallback)
        commandService.correlateRequest(commandRequest.id, pid)
        commandService.commandUpdate(status)
        Assert.assertEquals(VehicleCommandStatus.FINISHED, lastStatus)
    }

    @Test
    fun commandFinishedAndRemoved() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val carCommand: CarVehicleApiCommand<AuxHeatStartError> = CarVehicleApiCommand(commandRequest)
        val status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.FINISHED, "uid", 0, 0)
        commandService.commandRequestCreated(carCommand, commandCallback)
        commandService.correlateRequest(commandRequest.id, pid)
        commandService.commandUpdate(status)
        Assert.assertEquals(0, commandService.getPendingCommands().size.toLong())
    }

    @Test
    fun commandFailedNotified() {
        val failedCause = "FAIL"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val carCommand: CarVehicleApiCommand<AuxHeatStartError> = CarVehicleApiCommand(commandRequest)
        commandService.commandRequestCreated(carCommand, commandCallback)
        commandService.onSendCommandFailed(carCommand, CommandFailed(failedCause))
        Assert.assertEquals(VehicleCommandStatus.FAILED, lastStatus)
        val error = lastErrors!![0] as AuxHeatStartError.GenericError
        Assert.assertEquals(failedCause, error.rawErrorCode)
    }

    @Test
    fun commandFailedAndRemoved() {
        val failedCause = "FAIL"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val carCommand: CarVehicleApiCommand<AuxHeatStartError> = CarVehicleApiCommand(commandRequest)
        commandService.commandRequestCreated(carCommand, commandCallback)
        commandService.onSendCommandFailed(carCommand, CommandFailed(failedCause))
        Assert.assertEquals(0, commandService.getPendingCommands().size.toLong())
    }

    @Test
    fun commandNotAddedTwice() {
        val commandRequest = VehicleCommand.AuxHeatStart("")
        commandService.commandRequestCreated(CarVehicleApiCommand(commandRequest), commandCallback)
        commandService.commandRequestCreated(CarVehicleApiCommand(commandRequest), commandCallback)
        Assert.assertEquals(1, commandService.getPendingCommands().size.toLong())
    }

    @Test
    fun currentCommandState() {
        val pid = "123456"
        val commandRequest = VehicleCommand.AuxHeatStart("")
        val carCommand: CarVehicleApiCommand<AuxHeatStartError> = CarVehicleApiCommand(commandRequest)
        val status = CommandVehicleApiStatus(emptyList(), pid, VehicleCommandStatus.ABOUT_TO_SEND, "uid", 0, 0)
        commandService.commandRequestCreated(carCommand, commandCallback)
        commandService.correlateRequest(commandRequest.id, pid)
        commandService.commandUpdate(status)
        Assert.assertEquals(lastStatus, commandService.commandStateType(commandRequest))
    }

    internal inner class CommandCallback : VehicleCommandCallback<AuxHeatStartError> {
        override fun onSuccess(timestamp: Long?) {
            lastStatus = VehicleCommandStatus.FINISHED
        }

        override fun onUpdate(status: VehicleCommandStatusUpdate) {
            lastStatus = status.status.commandStatus
        }

        override fun onError(timestamp: Long?, errors: List<AuxHeatStartError>) {
            lastStatus = VehicleCommandStatus.FAILED
            lastErrors = errors
        }
    }
}
