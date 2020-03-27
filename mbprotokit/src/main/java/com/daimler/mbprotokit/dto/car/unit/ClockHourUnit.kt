package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class ClockHourUnit(
    val clockHourUnit: VehicleAttributeStatus.ClockHourUnit
) : Unit {
    UNSPECIFIED_CLOCK_HOUR_UNIT(VehicleAttributeStatus.ClockHourUnit.UNSPECIFIED_CLOCK_HOUR_UNIT),

    /**
     * 12h (AM/PM)
     */
    T12H(VehicleAttributeStatus.ClockHourUnit.T12H),

    /**
     * 24h
     */
    T24H(VehicleAttributeStatus.ClockHourUnit.T24H),

    UNRECOGNIZED(VehicleAttributeStatus.ClockHourUnit.UNRECOGNIZED);

    companion object {
        fun map(clockHourUnit: VehicleAttributeStatus.ClockHourUnit) = values().find {
            clockHourUnit == it.clockHourUnit
        } ?: UNRECOGNIZED
    }
}
