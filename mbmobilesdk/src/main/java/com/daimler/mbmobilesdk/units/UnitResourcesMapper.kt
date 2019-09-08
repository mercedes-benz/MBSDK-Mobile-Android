package com.daimler.mbmobilesdk.units

import android.content.res.Resources
import com.daimler.mbmobilesdk.R
import com.daimler.mbingresskit.common.UnitPreferences

object UnitResourcesMapper {

    fun getTimeFormatUnitsResources(resources: Resources): Map<UnitPreferences.ClockHoursUnits, CharSequence> {
        return mapOf(
            UnitPreferences.ClockHoursUnits.TYPE_24H to
                resources.getString(R.string.setting_units_24h),
            UnitPreferences.ClockHoursUnits.TYPE_12H to
                resources.getString(R.string.setting_units_12h)
        )
    }

    fun getSpeedDistanceUnitsResources(
        resources: Resources
    ): Map<UnitPreferences.SpeedDistanceUnits, CharSequence> {
        return mapOf(
            UnitPreferences.SpeedDistanceUnits.KILOMETERS to
                resources.getString(R.string.setting_units_kilometers_per_hour),
            UnitPreferences.SpeedDistanceUnits.MILES to
                resources.getString(R.string.setting_units_miles_per_hour)
        )
    }

    fun getConsumptionCoUnitsResources(
        resources: Resources
    ): Map<UnitPreferences.ConsumptionCoUnits, CharSequence> {
        return mapOf(
            UnitPreferences.ConsumptionCoUnits.LITERS_PER_100_KILOMETERS to
                resources.getString(R.string.setting_units_liters_per_100_kilometers),
            UnitPreferences.ConsumptionCoUnits.KILOMETERS_PER_LITER to
                resources.getString(R.string.setting_units_kilometers_per_liter),
            UnitPreferences.ConsumptionCoUnits.MILES_PER_GALLON_UK to
                resources.getString(R.string.setting_units_miles_per_gallon_uk),
            UnitPreferences.ConsumptionCoUnits.MILES_PER_GALLON_US to
                resources.getString(R.string.setting_units_miles_per_gallon_us)
        )
    }

    fun getConsumptionEvUnitsResources(
        resources: Resources
    ): Map<UnitPreferences.ConsumptionEvUnits, CharSequence> {
        return mapOf(
            UnitPreferences.ConsumptionEvUnits.KILOWATT_HOURS_PER_100_KILOMETERS to
                resources.getString(R.string.setting_units_kilowatt_hours_per_100_kilometers),
            UnitPreferences.ConsumptionEvUnits.KILOMETERS_PER_KILOWATT_HOUR to
                resources.getString(R.string.setting_units_kilometers_per_kilowatt_hour),
            UnitPreferences.ConsumptionEvUnits.KILOWATT_HOURS_PER_100_MILES to
                resources.getString(R.string.setting_units_kilowatt_hours_per_100_miles),
            UnitPreferences.ConsumptionEvUnits.MILES_PER_KILOWATT_HOUR to
                resources.getString(R.string.setting_units_miles_per_kilowatt_hour),
            UnitPreferences.ConsumptionEvUnits.MILES_PER_GALLON_GASOLINE_EQUIVALENT to
                resources.getString(R.string.setting_units_miles_per_gallon_gasoline_equivalent)
        )
    }

    fun getConsumptionGasUnitsResources(
        resources: Resources
    ): Map<UnitPreferences.ConsumptionGasUnits, CharSequence> {
        return mapOf(
            UnitPreferences.ConsumptionGasUnits.KILOGRAM_PER_100_KILOMETERS to
                resources.getString(R.string.setting_units_kilogram_per_100_kilometers),
            UnitPreferences.ConsumptionGasUnits.KILOMETERS_PER_KILOGRAM to
                resources.getString(R.string.setting_units_kilometers_per_kilogram),
            UnitPreferences.ConsumptionGasUnits.MILES_PER_KILOGRAM to
                resources.getString(R.string.setting_units_miles_per_kilogram)
        )
    }

    fun getTirePressureUnitsResources(resources: Resources): Map<UnitPreferences.TirePressureUnits, CharSequence> {
        return mapOf(
            UnitPreferences.TirePressureUnits.KILOPASCAL to
                resources.getString(R.string.setting_units_kilopascal),
            UnitPreferences.TirePressureUnits.BAR to
                resources.getString(R.string.setting_units_bar),
            UnitPreferences.TirePressureUnits.POUNDS_PER_SQUARE_INCH to
                resources.getString(R.string.setting_units_pounds_per_square_inch)
        )
    }

    fun getTemperatureUnitsResources(resources: Resources): Map<UnitPreferences.TemperatureUnits, CharSequence> {
        return mapOf(
            UnitPreferences.TemperatureUnits.CELSIUS to
                resources.getString(R.string.setting_units_celsius),
            UnitPreferences.TemperatureUnits.FAHRENHEIT to
                resources.getString(R.string.setting_units_fahrenheit)
        )
    }
}