package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class PressureUnit(
    val pressureUnit: VehicleAttributeStatus.PressureUnit
) : Unit {
    UNSPECIFIED_PRESSURE_UNIT(VehicleAttributeStatus.PressureUnit.UNSPECIFIED_PRESSURE_UNIT),
    KPA(VehicleAttributeStatus.PressureUnit.KPA),
    BAR(VehicleAttributeStatus.PressureUnit.BAR),

    /**
     * Pounds per square inch
     */
    PSI(VehicleAttributeStatus.PressureUnit.PSI),

    UNRECOGNIZED(VehicleAttributeStatus.PressureUnit.UNRECOGNIZED);

    companion object {
        fun map(pressureUnit: VehicleAttributeStatus.PressureUnit) = values().find {
            pressureUnit == it.pressureUnit
        } ?: UNRECOGNIZED
    }
}
