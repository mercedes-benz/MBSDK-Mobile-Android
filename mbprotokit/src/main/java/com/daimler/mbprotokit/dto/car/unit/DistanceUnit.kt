package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class DistanceUnit(
    val distanceUnit: VehicleAttributeStatus.DistanceUnit
) : Unit {
    UNSPECIFIED_DISTANCE_UNIT(VehicleAttributeStatus.DistanceUnit.UNSPECIFIED_DISTANCE_UNIT),

    /**
     * distance unit: km
     */
    KILOMETERS(VehicleAttributeStatus.DistanceUnit.KILOMETERS),

    /**
     * distance unit: miles
     */
    MILES(VehicleAttributeStatus.DistanceUnit.MILES),

    UNRECOGNIZED(VehicleAttributeStatus.DistanceUnit.UNRECOGNIZED);

    companion object {
        fun map(distanceUnit: VehicleAttributeStatus.DistanceUnit) = values().find {
            distanceUnit == it.distanceUnit
        } ?: UNRECOGNIZED
    }
}
