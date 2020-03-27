package com.daimler.mbprotokit.dto.command

/**
 * The state of a vehicle command.
 */
enum class VehicleCommandStatus(val id: Int) {
    UNKNOWN(0),
    /**
     * Command execution request is accepted and an asynchronous process is being initialized.
     */
    ACCEPTED(1),
    /**
     * This command is queued until another runnin^g command is finished. This can only happen, if another similar command is currently executed.
     */
    ENQUEUED(2),
    /**
     * The command is currently processed.
     */
    PROCESSING(3),
    /**
     * The backend is waiting for a response from the vehicle (can take up to 8 minutes). This state can only happen, if the command execution takes longer than 20 seconds.
     */
    WAITING(4),
    /**
     * The command execution has completed.
     */
    FINISHED(5),
    /**
     * The command execution failed.
     */
    FAILED(6),
    /**
     * The command is about to be sent over the socket.
     */
    ABOUT_TO_SEND(7);

    companion object {
        fun map(id: Int) = values().find {
            id == it.id
        } ?: UNKNOWN
    }
}
