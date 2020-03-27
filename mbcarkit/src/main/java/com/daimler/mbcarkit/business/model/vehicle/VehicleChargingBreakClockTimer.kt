package com.daimler.mbcarkit.business.model.vehicle

data class VehicleChargingBreakClockTimer(
    val action: ChargingBreakClockTimerAction,
    /**
     * End time (hour) when charging break shall be applied, valid value range: 0-23
     */
    val endTimeHour: Int,
    /**
     *  End time (minute) when charging break shall be applied, valid value range: 0-59
     */
    val endTimeMin: Int,
    /**
     * Start time (hour) when charging break shall be applied, valid value range: 0-23
     */
    val startTimeHour: Int,
    /**
     * Start time (minute) when charging break shall be applied, valid value range: 0-59
     */
    val startTimeMin: Int,
    /**
     * Id which timer slot is used, valid value range: 1-4
     */
    val timerId: Long
)
