package com.daimler.mbmobilesdk.utils.extensions

import android.content.Context
import com.daimler.mbmobilesdk.R
import com.daimler.mbcarkit.business.model.vehicle.VehicleAttribute
import com.daimler.mbcarkit.business.model.vehicle.unit.*

fun VehicleAttribute.Unit<*>.localizedDisplayUnit(context: Context): String =
    when (displayUnit) {
        is ClockHourUnit -> (displayUnit as ClockHourUnit).localizedDisplayUnit(context)
        is CombustionConsumptionUnit -> (displayUnit as CombustionConsumptionUnit).localizedDisplayUnit(context)
        is ElectricityConsumptionUnit -> (displayUnit as ElectricityConsumptionUnit).localizedDisplayUnit(context)
        is GasConsumptionUnit -> (displayUnit as GasConsumptionUnit).localizedDisplayUnit(context)
        is PressureUnit -> (displayUnit as PressureUnit).localizedDisplayUnit(context)
        is RatioUnit -> (displayUnit as RatioUnit).localizedDisplayUnit(context)
        is SpeedDistanceUnit -> (displayUnit as SpeedDistanceUnit).localizedDisplayUnit(context)
        is DistanceUnit -> (displayUnit as DistanceUnit).localizedDisplayUnit(context)
        is SpeedUnit -> (displayUnit as SpeedUnit).localizedDisplayUnit(context)
        is TemperatureUnit -> (displayUnit as TemperatureUnit).localizedDisplayUnit(context)
        else -> ""
    }

fun ClockHourUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        ClockHourUnit.UNSPECIFIED_CLOCK_HOUR_UNIT -> ""
        ClockHourUnit.T12H -> context.getString(R.string.setting_units_12h)
        ClockHourUnit.T24H -> context.getString(R.string.setting_units_24h)
    }

fun CombustionConsumptionUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        CombustionConsumptionUnit.UNSPECIFIED_COMBUSTION_CONSUMPTION_UNIT -> ""
        CombustionConsumptionUnit.LITER_PER_100KM -> context.getString(R.string.setting_units_liters_per_100_kilometers)
        CombustionConsumptionUnit.KM_PER_LITER -> context.getString(R.string.setting_units_kilometers_per_liter)
        CombustionConsumptionUnit.MPG_UK -> context.getString(R.string.setting_units_miles_per_gallon_uk)
        CombustionConsumptionUnit.MPG_US -> context.getString(R.string.setting_units_miles_per_gallon_us)
    }

fun ElectricityConsumptionUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        ElectricityConsumptionUnit.UNSPECIFIED_ELECTRICITY_CONSUMPTION_UNIT -> ""
        ElectricityConsumptionUnit.KWH_PER_100KM -> context.getString(R.string.setting_units_kilowatt_hours_per_100_kilometers)
        ElectricityConsumptionUnit.KM_PER_KWH -> context.getString(R.string.setting_units_kilometers_per_kilowatt_hour)
        ElectricityConsumptionUnit.KWH_PER_100MI -> context.getString(R.string.setting_units_kilowatt_hours_per_100_miles)
        ElectricityConsumptionUnit.M_PER_KWH -> context.getString(R.string.setting_units_miles_per_kilowatt_hour)
        ElectricityConsumptionUnit.MPGE -> context.getString(R.string.setting_units_miles_per_gallon_gasoline_equivalent)
    }

fun GasConsumptionUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        GasConsumptionUnit.UNSPECIFIED_GAS_CONSUMPTION_UNIT -> ""
        GasConsumptionUnit.KG_PER_100KM -> context.getString(R.string.setting_units_kilogram_per_100_kilometers)
        GasConsumptionUnit.KM_PER_KG -> context.getString(R.string.setting_units_kilometers_per_kilogram)
        GasConsumptionUnit.M_PER_KG -> context.getString(R.string.setting_units_miles_per_kilogram)
    }

fun PressureUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        PressureUnit.UNSPECIFIED_PRESSURE_UNIT -> ""
        PressureUnit.KPA -> context.getString(R.string.setting_units_kilopascal)
        PressureUnit.BAR -> context.getString(R.string.setting_units_bar)
        PressureUnit.PSI -> context.getString(R.string.setting_units_pounds_per_square_inch)
    }

@Suppress("UNUSED_PARAMETER")
fun RatioUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        RatioUnit.UNSPECIFIED_RATIO_UNIT -> ""
        RatioUnit.PERCENT -> "%"
    }

fun SpeedDistanceUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        SpeedDistanceUnit.UNSPECIFIED_SPEED_DISTANCE_UNIT -> ""
        SpeedDistanceUnit.KM_PER_H -> context.getString(R.string.setting_units_kilometers_per_hour)
        SpeedDistanceUnit.M_PER_H -> context.getString(R.string.setting_units_miles_per_hour)
    }

fun TemperatureUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        TemperatureUnit.UNSPECIFIED_TEMPERATURE_UNIT -> ""
        TemperatureUnit.CELSIUS -> context.getString(R.string.setting_units_celsius)
        TemperatureUnit.FAHRENHEIT -> context.getString(R.string.setting_units_fahrenheit)
    }

fun DistanceUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        DistanceUnit.KILOMETERS -> context.getString(R.string.setting_units_kilometers)
        DistanceUnit.MILES -> context.getString(R.string.setting_units_miles)
        DistanceUnit.UNSPECIFIED_DISTANCE_UNIT -> ""
    }

fun SpeedUnit.localizedDisplayUnit(context: Context): String =
    when (this) {
        SpeedUnit.KM_PER_H -> context.getString(R.string.setting_units_kilometers_per_hour)
        SpeedUnit.M_PER_H -> context.getString(R.string.setting_units_miles_per_hour)
        SpeedUnit.UNSPECIFIED_SPEED_UNIT -> ""
    }
