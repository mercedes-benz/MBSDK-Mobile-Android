package com.daimler.mbnetworkkit.task

enum class TaskState {
    /**
     * The request is still pending. It could be created or currently running, but not yet finished.
     */
    PENDING,

    /**
     * The request has finished running successfully.
     */
    COMPLETED,

    /**
     * The request has finished running and a failure occured.
     */
    FAILED
}
