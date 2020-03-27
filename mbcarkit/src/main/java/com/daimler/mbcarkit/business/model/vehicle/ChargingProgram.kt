package com.daimler.mbcarkit.business.model.vehicle

enum class ChargingProgram {
    DEFAULT,
    /**  INSTANT can only be received and not be used as parameter for commands. */
    INSTANT,
    HOME,
    WORK,
    UNKNOWN
}
