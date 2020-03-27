package com.daimler.mbcarkit.proto.tmp.car

import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.VehicleAttribute
import com.daimler.mbcarkit.business.model.vehicle.unit.ClockHourUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.CombustionConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.ElectricityConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.GasConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.PressureUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.RatioUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.SpeedUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.TemperatureUnit
import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.dto.car.AttributeStatus
import com.daimler.mbprotokit.dto.car.AttributeUnit
import com.daimler.mbprotokit.dto.car.unit.UnitCase

@Suppress("UNCHECKED_CAST")
fun <T, R : Enum<*>, S> Pair<T, AttributeInfo>.mapTo(mapper: ((T) -> S?)? = null): VehicleAttribute<S, R> {
    return VehicleAttribute(
        status = second.status.mapToStatus(),
        lastChanged = second.lastChanged,
        value = mapper?.invoke(first) ?: first as? S,
        unit = second.unit?.mapUnit()
    )
}

fun <R : Enum<*>> AttributeUnit.mapUnit() = VehicleAttribute.Unit<R>(
    this.displayValue,
    this.displayUnitCase.mapToUnitCase(),
    this.getDisplayUnit()
)

@Suppress("UNCHECKED_CAST")
fun <R : Enum<*>> AttributeUnit.getDisplayUnit(): R = when (val unit = this.displayUnit) {
    is com.daimler.mbprotokit.dto.car.unit.ClockHourUnit -> unit.mapClockHourUnit()
    is com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit -> unit.mapCombustionConsumptionUnit()
    is com.daimler.mbprotokit.dto.car.unit.DistanceUnit -> unit.mapDistanceUnit()
    is com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit -> unit.mapElectricConsumptionUnit()
    is com.daimler.mbprotokit.dto.car.unit.GasConsumptionUnit -> unit.mapGasConsumptionUnit()
    is com.daimler.mbprotokit.dto.car.unit.PressureUnit -> unit.mapPressureUnit()
    is com.daimler.mbprotokit.dto.car.unit.RatioUnit -> unit.mapRatioUnit()
    is com.daimler.mbprotokit.dto.car.unit.SpeedUnit -> unit.mapSpeedUnit()
    is com.daimler.mbprotokit.dto.car.unit.TemperatureUnit -> unit.mapTemperatureUnit()
    else -> throw NotImplementedError()
} as R

fun AttributeStatus.mapToStatus() = when (this) {
    AttributeStatus.VALID -> StatusEnum.VALID
    AttributeStatus.NO_VALUE -> StatusEnum.NO_VALUE
    AttributeStatus.INVALID,
    AttributeStatus.VALUE_NOT_AVAILABLE,
    AttributeStatus.UNRECOGNIZED -> StatusEnum.INVALID
}

fun UnitCase.mapToUnitCase() = when (this) {
    UnitCase.DISPLAYUNIT_NOT_SET -> DisplayUnitCase.DISPLAYUNIT_NOT_SET
    UnitCase.COMBUSTION_CONSUMPTION_UNIT -> DisplayUnitCase.COMBUSTION_CONSUMPTION_UNIT
    UnitCase.GAS_CONSUMPTION_UNIT -> DisplayUnitCase.GAS_CONSUMPTION_UNIT
    UnitCase.ELECTRICITY_CONSUMPTION_UNIT -> DisplayUnitCase.ELECTRICITY_CONSUMPTION_UNIT
    UnitCase.TEMPERATURE_UNIT -> DisplayUnitCase.TEMPERATURE_UNIT
    UnitCase.PRESSURE_UNIT -> DisplayUnitCase.PRESSURE_UNIT
    UnitCase.RATIO_UNIT -> DisplayUnitCase.RATIO_UNIT
    UnitCase.CLOCK_HOUR_UNIT -> DisplayUnitCase.CLOCK_HOUR_UNIT
    UnitCase.SPEED_UNIT -> DisplayUnitCase.SPEED_UNIT
    UnitCase.DISTANCE_UNIT -> DisplayUnitCase.DISTANCE_UNIT
}

fun com.daimler.mbprotokit.dto.car.unit.ClockHourUnit.mapClockHourUnit() = when (this) {
    com.daimler.mbprotokit.dto.car.unit.ClockHourUnit.T12H -> ClockHourUnit.T12H
    com.daimler.mbprotokit.dto.car.unit.ClockHourUnit.T24H -> ClockHourUnit.T24H
    com.daimler.mbprotokit.dto.car.unit.ClockHourUnit.UNSPECIFIED_CLOCK_HOUR_UNIT,
    com.daimler.mbprotokit.dto.car.unit.ClockHourUnit.UNRECOGNIZED -> ClockHourUnit.UNSPECIFIED_CLOCK_HOUR_UNIT
}

fun com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit.mapCombustionConsumptionUnit() =
    when (this) {
        com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit.LITER_PER_100KM -> CombustionConsumptionUnit.LITER_PER_100KM
        com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit.KM_PER_LITER -> CombustionConsumptionUnit.KM_PER_LITER
        com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit.MPG_UK -> CombustionConsumptionUnit.MPG_UK
        com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit.MPG_US -> CombustionConsumptionUnit.MPG_US
        com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit.UNSPECIFIED_COMBUSTION_CONSUMPTION_UNIT,
        com.daimler.mbprotokit.dto.car.unit.CombustionConsumptionUnit.UNRECOGNIZED -> CombustionConsumptionUnit.UNSPECIFIED_COMBUSTION_CONSUMPTION_UNIT
    }

fun com.daimler.mbprotokit.dto.car.unit.DistanceUnit.mapDistanceUnit() = when (this) {
    com.daimler.mbprotokit.dto.car.unit.DistanceUnit.KILOMETERS -> DistanceUnit.KILOMETERS
    com.daimler.mbprotokit.dto.car.unit.DistanceUnit.MILES -> DistanceUnit.MILES
    com.daimler.mbprotokit.dto.car.unit.DistanceUnit.UNSPECIFIED_DISTANCE_UNIT,
    com.daimler.mbprotokit.dto.car.unit.DistanceUnit.UNRECOGNIZED -> DistanceUnit.UNSPECIFIED_DISTANCE_UNIT
}

fun com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit.mapElectricConsumptionUnit() =
    when (this) {
        com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit.KWH_PER_100KM -> ElectricityConsumptionUnit.KWH_PER_100KM
        com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit.KM_PER_KWH -> ElectricityConsumptionUnit.KM_PER_KWH
        com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit.KWH_PER_100MI -> ElectricityConsumptionUnit.KWH_PER_100MI
        com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit.M_PER_KWH -> ElectricityConsumptionUnit.M_PER_KWH
        com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit.MPGE -> ElectricityConsumptionUnit.MPGE
        com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit.UNSPECIFIED_ELECTRICITY_CONSUMPTION_UNIT,
        com.daimler.mbprotokit.dto.car.unit.ElectricityConsumptionUnit.UNRECOGNIZED -> ElectricityConsumptionUnit.UNSPECIFIED_ELECTRICITY_CONSUMPTION_UNIT
    }

fun com.daimler.mbprotokit.dto.car.unit.GasConsumptionUnit.mapGasConsumptionUnit() = when (this) {
    com.daimler.mbprotokit.dto.car.unit.GasConsumptionUnit.KG_PER_100KM -> GasConsumptionUnit.KG_PER_100KM
    com.daimler.mbprotokit.dto.car.unit.GasConsumptionUnit.KM_PER_KG -> GasConsumptionUnit.KM_PER_KG
    com.daimler.mbprotokit.dto.car.unit.GasConsumptionUnit.M_PER_KG -> GasConsumptionUnit.M_PER_KG
    com.daimler.mbprotokit.dto.car.unit.GasConsumptionUnit.UNSPECIFIED_GAS_CONSUMPTION_UNIT,
    com.daimler.mbprotokit.dto.car.unit.GasConsumptionUnit.UNRECOGNIZED -> GasConsumptionUnit.UNSPECIFIED_GAS_CONSUMPTION_UNIT
}

fun com.daimler.mbprotokit.dto.car.unit.PressureUnit.mapPressureUnit() = when (this) {
    com.daimler.mbprotokit.dto.car.unit.PressureUnit.KPA -> PressureUnit.KPA
    com.daimler.mbprotokit.dto.car.unit.PressureUnit.BAR -> PressureUnit.BAR
    com.daimler.mbprotokit.dto.car.unit.PressureUnit.PSI -> PressureUnit.PSI
    com.daimler.mbprotokit.dto.car.unit.PressureUnit.UNSPECIFIED_PRESSURE_UNIT,
    com.daimler.mbprotokit.dto.car.unit.PressureUnit.UNRECOGNIZED -> PressureUnit.UNSPECIFIED_PRESSURE_UNIT
}

fun com.daimler.mbprotokit.dto.car.unit.RatioUnit.mapRatioUnit() = when (this) {
    com.daimler.mbprotokit.dto.car.unit.RatioUnit.PERCENT -> RatioUnit.PERCENT
    com.daimler.mbprotokit.dto.car.unit.RatioUnit.UNRECOGNIZED,
    com.daimler.mbprotokit.dto.car.unit.RatioUnit.UNSPECIFIED_RATIO_UNIT -> RatioUnit.UNSPECIFIED_RATIO_UNIT
}

fun com.daimler.mbprotokit.dto.car.unit.SpeedUnit.mapSpeedUnit() = when (this) {
    com.daimler.mbprotokit.dto.car.unit.SpeedUnit.KM_PER_H -> SpeedUnit.KM_PER_H
    com.daimler.mbprotokit.dto.car.unit.SpeedUnit.M_PER_H -> SpeedUnit.M_PER_H
    com.daimler.mbprotokit.dto.car.unit.SpeedUnit.UNSPECIFIED_SPEED_UNIT,
    com.daimler.mbprotokit.dto.car.unit.SpeedUnit.UNRECOGNIZED -> SpeedUnit.UNSPECIFIED_SPEED_UNIT
}

fun com.daimler.mbprotokit.dto.car.unit.TemperatureUnit.mapTemperatureUnit() = when (this) {
    com.daimler.mbprotokit.dto.car.unit.TemperatureUnit.CELSIUS -> TemperatureUnit.CELSIUS
    com.daimler.mbprotokit.dto.car.unit.TemperatureUnit.FAHRENHEIT -> TemperatureUnit.FAHRENHEIT
    com.daimler.mbprotokit.dto.car.unit.TemperatureUnit.UNSPECIFIED_TEMPERATURE_UNIT,
    com.daimler.mbprotokit.dto.car.unit.TemperatureUnit.UNRECOGNIZED -> TemperatureUnit.UNSPECIFIED_TEMPERATURE_UNIT
}
