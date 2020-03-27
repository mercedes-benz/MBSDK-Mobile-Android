package com.daimler.mbcarkit.business.model.vehicle

data class AuxheatWarnings(var bitmask: Int = NONE) {

    companion object {
        const val NONE = 0
        const val NO_BUDGET = 1
        const val BUDGET_EMPTY = 2
        const val SYSTEM_ERROR = 4
        const val RUNNING_ERROR = 8
        const val FUEL_ON_RESERVE = 16
        const val RESERVE_REACHED = 32
        const val LOW_VOLTAGE = 64
        const val LOW_VOLTAGE_OPERATION = 128
        const val COMMUNICATION_ERROR = 256
    }

    fun noWarnings() = bitmask == NONE

    fun noBudget() = bitmask.and(NO_BUDGET) == NO_BUDGET

    fun budgetEmpty() = bitmask.and(BUDGET_EMPTY) == BUDGET_EMPTY

    fun systemError() = bitmask.and(SYSTEM_ERROR) == SYSTEM_ERROR

    fun runningError() = bitmask.and(RUNNING_ERROR) == RUNNING_ERROR

    fun fuelOnReserve() = bitmask.and(FUEL_ON_RESERVE) == FUEL_ON_RESERVE

    fun reserveReached() = bitmask.and(RESERVE_REACHED) == RESERVE_REACHED

    fun lowVoltage() = bitmask.and(LOW_VOLTAGE) == LOW_VOLTAGE

    fun lowVoltageOperation() = bitmask.and(LOW_VOLTAGE_OPERATION) == LOW_VOLTAGE_OPERATION

    fun communicationError() = bitmask.and(COMMUNICATION_ERROR) == COMMUNICATION_ERROR
}
