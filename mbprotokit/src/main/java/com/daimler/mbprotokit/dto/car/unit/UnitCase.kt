package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class UnitCase(val displayUnitCase: VehicleAttributeStatus.DisplayUnitCase) {
    DISPLAYUNIT_NOT_SET(VehicleAttributeStatus.DisplayUnitCase.DISPLAYUNIT_NOT_SET),
    COMBUSTION_CONSUMPTION_UNIT(VehicleAttributeStatus.DisplayUnitCase.COMBUSTION_CONSUMPTION_UNIT),
    GAS_CONSUMPTION_UNIT(VehicleAttributeStatus.DisplayUnitCase.GAS_CONSUMPTION_UNIT),
    ELECTRICITY_CONSUMPTION_UNIT(VehicleAttributeStatus.DisplayUnitCase.ELECTRICITY_CONSUMPTION_UNIT),
    TEMPERATURE_UNIT(VehicleAttributeStatus.DisplayUnitCase.TEMPERATURE_UNIT),
    PRESSURE_UNIT(VehicleAttributeStatus.DisplayUnitCase.PRESSURE_UNIT),
    RATIO_UNIT(VehicleAttributeStatus.DisplayUnitCase.RATIO_UNIT),
    CLOCK_HOUR_UNIT(VehicleAttributeStatus.DisplayUnitCase.CLOCK_HOUR_UNIT),
    SPEED_UNIT(VehicleAttributeStatus.DisplayUnitCase.SPEED_UNIT),
    DISTANCE_UNIT(VehicleAttributeStatus.DisplayUnitCase.DISTANCE_UNIT);

    companion object {
        fun map(displayUnitCase: VehicleAttributeStatus.DisplayUnitCase) = values().find {
            it.displayUnitCase == displayUnitCase
        } ?: DISPLAYUNIT_NOT_SET
    }
}
