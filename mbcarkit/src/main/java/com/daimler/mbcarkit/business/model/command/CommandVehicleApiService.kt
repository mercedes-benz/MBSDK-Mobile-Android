package com.daimler.mbcarkit.business.model.command

import com.daimler.mbcarkit.tracking.VehicleCommandTracker

class CommandVehicleApiService {

    private val commands: MutableList<CarVehicleApiCommandWrapper<*>> = mutableListOf()
    private val vehicleCommandTracker = VehicleCommandTracker()

    fun containsAndRequiresPin(commandStatusUpdate: CommandVehicleApiStatus): Boolean {
        synchronized(commands) {
            return commands.firstOrNull { it.carCommand.requestId() == commandStatusUpdate.requestId }
                ?.carCommand?.requiresPin() == true
        }
    }

    fun onSendCommandFailed(carCommand: CarVehicleApiCommand<*>, cause: GenericCommandError) {
        synchronized(commands) {
            val id = carCommand.requestId()
            commands.filter { it.carCommand.requestId() == id }
                .forEach {
                    it.applyGenericError(cause)
                }
            commands.removeAll { id == carCommand.requestId() }
        }
    }

    fun <T : VehicleCommandError> commandRequestCreated(carCommand: CarVehicleApiCommand<T>, commandCallback: VehicleCommandCallback<T>) {
        synchronized(commands) {
            val id = carCommand.requestId()
            val notContains = commands.none { it.carCommand.requestId() == id }
            if (notContains) {
                commands.add(CarVehicleApiCommandWrapper(carCommand, commandCallback))
                vehicleCommandTracker.trackCarCommand(carCommand)
            }
        }
    }

    fun correlateRequest(requestId: String, pId: String) {
        synchronized(commands) {
            commands.filter { it.carCommand.requestId() == requestId }
                .forEach { it.carCommand.pId = pId }
        }
    }

    fun commandUpdate(commandStatus: CommandVehicleApiStatus) {
        synchronized(commands) {
            val id = commandStatus.pid
            commands.filter { id == it.carCommand.pId }
                .forEach {
                    it.carCommand.update(commandStatus)
                    vehicleCommandTracker.trackCarCommand(it.carCommand)
                    it.apply(commandStatus)
                    commands.removeAll { commandWrapper ->
                        (VehicleCommandStatus.FINISHED == commandWrapper.carCommand.state())
                            .or(VehicleCommandStatus.FAILED == commandWrapper.carCommand.state()) &&
                            it.carCommand.pId == commandWrapper.carCommand.pId
                    }
                }
        }
    }

    fun commandAboutToSend(command: CarVehicleApiCommand<*>) {
        synchronized(commands) {
            commands.filter { it.carCommand.requestId() == command.requestId() }
                .forEach {
                    val state = CommandVehicleApiStatus(
                        emptyList(),
                        "",
                        VehicleCommandStatus.ABOUT_TO_SEND,
                        it.carCommand.requestId(),
                        System.currentTimeMillis(),
                        0
                    )
                    it.carCommand.update(state)
                    it.apply(state)
                }
        }
    }

    fun commandStateType(commandRequest: VehicleCommand<*>): VehicleCommandStatus? {
        synchronized(commands) {
            return commands.firstOrNull { commandRequest.id == it.carCommand.requestId() }?.carCommand?.state()
        }
    }

    internal fun getCommandForRequestId(requestId: String): CarVehicleApiCommand<*>? =
        commands.firstOrNull { it.carCommand.requestId() == requestId }?.carCommand

    internal fun getPendingCommands(): List<CarVehicleApiCommand<*>> =
        commands.filter {
            it.carCommand.state() != VehicleCommandStatus.FINISHED &&
                it.carCommand.state() != VehicleCommandStatus.FAILED
        }.map {
            it.carCommand
        }

    private class CarVehicleApiCommandWrapper<T : VehicleCommandError>(val carCommand: CarVehicleApiCommand<T>, val callback: VehicleCommandCallback<T>) {
        fun apply(status: CommandVehicleApiStatus) {
            val timestamp = status.timestamp
            when (status.commandState) {
                VehicleCommandStatus.UNKNOWN -> {
                }
                VehicleCommandStatus.ACCEPTED -> {
                    callback.onUpdate(VehicleCommandStatusUpdate(timestamp, VehicleCommandUpdatedStatus.ACCEPTED))
                }
                VehicleCommandStatus.ENQUEUED -> {
                    callback.onUpdate(VehicleCommandStatusUpdate(timestamp, VehicleCommandUpdatedStatus.ENQUEUED))
                }
                VehicleCommandStatus.PROCESSING -> {
                    callback.onUpdate(VehicleCommandStatusUpdate(timestamp, VehicleCommandUpdatedStatus.PROCESSING))
                }
                VehicleCommandStatus.WAITING -> {
                    callback.onUpdate(VehicleCommandStatusUpdate(timestamp, VehicleCommandUpdatedStatus.WAITING))
                }
                VehicleCommandStatus.FINISHED -> {
                    callback.onSuccess(timestamp)
                }
                VehicleCommandStatus.FAILED -> {
                    callback.onError(timestamp, carCommand.convertToSpecificErrors(status))
                }
                VehicleCommandStatus.ABOUT_TO_SEND -> {
                    callback.onUpdate(VehicleCommandStatusUpdate(timestamp, VehicleCommandUpdatedStatus.ABOUT_TO_SEND))
                }
            }
        }

        fun applyGenericError(error: GenericCommandError) {
            callback.onError(System.currentTimeMillis(), listOf(carCommand.createGenericError(error)))
        }
    }
}
