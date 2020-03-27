package com.daimler.mbprotokit.mapping.car

import com.daimler.mbprotokit.dto.car.AttributeUnit
import com.daimler.mbprotokit.dto.car.unit.ClockHourUnit
import com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit
import com.daimler.mbprotokit.dto.car.unit.DistanceUnit
import com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit
import com.daimler.mbprotokit.dto.car.unit.GasConsumptionUnit
import com.daimler.mbprotokit.dto.car.unit.PressureUnit
import com.daimler.mbprotokit.dto.car.unit.RatioUnit
import com.daimler.mbprotokit.dto.car.unit.SpeedUnit
import com.daimler.mbprotokit.dto.car.unit.TemperatureUnit
import com.daimler.mbprotokit.dto.car.unit.Unit
import com.daimler.mbprotokit.dto.car.unit.UnitCase
import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus
import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus.DisplayUnitCase

/**
 * Maps unit information like displayValue, displayUnitCase, unit
 * to AttributeUnit [com.daimler.mbprotokit.dto.car.AttributeUnit]
 */
internal fun VehicleAttributeStatus?.mapUnit(): AttributeUnit? = this?.let {
    it.mapDisplayUnitCase()?.let { unit ->
        AttributeUnit(
            displayValue = it.displayValue,
            displayUnitCase = UnitCase.map(it.displayUnitCase),
            displayUnit = unit
        )
    }
}

/**
 * Maps proto displayUnitCase to the associated Unit
 * Known units: [com.daimler.mbprotokit.dto.car.unit]
 */
internal fun VehicleAttributeStatus.mapDisplayUnitCase(): Unit? =
    when (displayUnitCase) {
        DisplayUnitCase.CLOCK_HOUR_UNIT -> ClockHourUnit.map(clockHourUnit)
        DisplayUnitCase.COMBUSTION_CONSUMPTION_UNIT -> CombustionConsumptionUnit.map(
            combustionConsumptionUnit
        )
        DisplayUnitCase.ELECTRICITY_CONSUMPTION_UNIT -> ElectricityConsumptionUnit.map(
            electricityConsumptionUnit
        )
        DisplayUnitCase.GAS_CONSUMPTION_UNIT -> GasConsumptionUnit.map(gasConsumptionUnit)
        DisplayUnitCase.PRESSURE_UNIT -> PressureUnit.map(pressureUnit)
        DisplayUnitCase.RATIO_UNIT -> RatioUnit.map(ratioUnit)
        DisplayUnitCase.SPEED_UNIT -> SpeedUnit.map(speedUnit)
        DisplayUnitCase.DISTANCE_UNIT -> DistanceUnit.map(distanceUnit)
        DisplayUnitCase.TEMPERATURE_UNIT -> TemperatureUnit.map(temperatureUnit)
        else -> null
    }
