package com.daimler.mbcarkit.business.model.command

import java.util.Date

interface VehicleCommandCallback<T : VehicleCommandError> {

    /**
     * The command was successfully executed.
     */
    fun onSuccess(timestamp: Long?)

    /**
     * The command status changed. It is NOT guaranteed that every state change triggers an onUpdate call.
     */
    fun onUpdate(status: VehicleCommandStatusUpdate)

    /**
     * The command could not be executed because of an error.
     */
    fun onError(timestamp: Long?, errors: List<T>)
}

data class VehicleCommandStatusUpdate(
    val updatedTimestamp: Long?,
    val status: VehicleCommandUpdatedStatus
) {

    fun updated() = updatedTimestamp?.let { Date(it) }
}

enum class VehicleCommandUpdatedStatus(val commandStatus: VehicleCommandStatus) {

    /**
     * Command is about to be sent over the socket.
     */
    ABOUT_TO_SEND(VehicleCommandStatus.ABOUT_TO_SEND),

    /**
     * Command execution request is accepted and an asynchronous process is being initialized.
     */
    ACCEPTED(VehicleCommandStatus.ACCEPTED),

    /**
     * This command is queued until another running command is finished. This can only happen, if another similar command is currently executed.
     */
    ENQUEUED(VehicleCommandStatus.ENQUEUED),

    /**
     * The command is currently processed.
     */
    PROCESSING(VehicleCommandStatus.PROCESSING),

    /**
     * The backend is waiting for a response from the vehicle (can take up to 8 minutes). This state can only happen, if the command execution takes longer than 20 seconds.
     */
    WAITING(VehicleCommandStatus.WAITING)
}
