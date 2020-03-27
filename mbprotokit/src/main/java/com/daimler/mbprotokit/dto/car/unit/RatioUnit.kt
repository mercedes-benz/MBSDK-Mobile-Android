package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class RatioUnit(
    val ratioUnit: VehicleAttributeStatus.RatioUnit
) : Unit {
    UNSPECIFIED_RATIO_UNIT(VehicleAttributeStatus.RatioUnit.UNSPECIFIED_RATIO_UNIT),
    PERCENT(VehicleAttributeStatus.RatioUnit.PERCENT),
    UNRECOGNIZED(VehicleAttributeStatus.RatioUnit.UNRECOGNIZED);

    companion object {
        fun map(ratioUnit: VehicleAttributeStatus.RatioUnit) = values().find {
            ratioUnit == it.ratioUnit
        } ?: UNRECOGNIZED
    }
}
