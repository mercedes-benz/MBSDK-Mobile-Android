package com.daimler.mbcarkit.business.model.command

/**
 * The state of a vehicle command.
 */
enum class VehicleCommandStatus {
    UNKNOWN,
    /**
     * Command execution request is accepted and an asynchronous process is being initialized.
     */
    ACCEPTED,
    /**
     * This command is queued until another running command is finished. This can only happen, if another similar command is currently executed.
     */
    ENQUEUED,
    /**
     * The command is currently processed.
     */
    PROCESSING,
    /**
     * The backend is waiting for a response from the vehicle (can take up to 8 minutes). This state can only happen, if the command execution takes longer than 20 seconds.
     */
    WAITING,
    /**
     * The command execution has completed.
     */
    FINISHED,
    /**
     * The command execution failed.
     */
    FAILED,
    /**
     * The command is about to be sent over the socket.
     */
    ABOUT_TO_SEND
}
