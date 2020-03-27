package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class SpeedUnit(val speedUnit: VehicleAttributeStatus.SpeedUnit) :
    Unit {
    UNSPECIFIED_SPEED_UNIT(VehicleAttributeStatus.SpeedUnit.UNSPECIFIED_SPEED_UNIT),

    /**
     * km/h
     */
    KM_PER_H(VehicleAttributeStatus.SpeedUnit.KM_PER_HOUR),

    /**
     * mph
     */
    M_PER_H(VehicleAttributeStatus.SpeedUnit.M_PER_HOUR),

    UNRECOGNIZED(VehicleAttributeStatus.SpeedUnit.UNRECOGNIZED);

    companion object {
        fun map(speedUnit: VehicleAttributeStatus.SpeedUnit) = values().find {
            speedUnit == it.speedUnit
        } ?: UNRECOGNIZED
    }
}
