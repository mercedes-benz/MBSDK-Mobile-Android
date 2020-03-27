package com.daimler.mbingresskit.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnitPreferences(
    val clockHours: ClockHoursUnits,
    val speedDistance: SpeedDistanceUnits,
    val consumptionCo: ConsumptionCoUnits,
    val consumptionEv: ConsumptionEvUnits,
    val consumptionGas: ConsumptionGasUnits,
    val tirePressure: TirePressureUnits,
    val temperature: TemperatureUnits
) : Parcelable {

    enum class ClockHoursUnits {
        @SerializedName("24h")
        TYPE_24H,
        @SerializedName("12h (AM/PM)")
        TYPE_12H
    }

    enum class SpeedDistanceUnits {
        @SerializedName("km/h, km")
        KILOMETERS,
        @SerializedName("mph, mi")
        MILES
    }

    enum class ConsumptionCoUnits {
        @SerializedName("l/100km")
        LITERS_PER_100_KILOMETERS,
        @SerializedName("km/l")
        KILOMETERS_PER_LITER,
        @SerializedName("mpg (UK)")
        MILES_PER_GALLON_UK,
        @SerializedName("mpg (US)")
        MILES_PER_GALLON_US,
    }

    enum class ConsumptionEvUnits {
        @SerializedName("kWh/100km")
        KILOWATT_HOURS_PER_100_KILOMETERS,
        @SerializedName("km/kWh")
        KILOMETERS_PER_KILOWATT_HOUR,
        @SerializedName("kWh/100mi")
        KILOWATT_HOURS_PER_100_MILES,
        @SerializedName("mpkWh")
        MILES_PER_KILOWATT_HOUR,
        @SerializedName("mpge")
        MILES_PER_GALLON_GASOLINE_EQUIVALENT
    }

    enum class ConsumptionGasUnits {
        @SerializedName("kg/100km")
        KILOGRAM_PER_100_KILOMETERS,
        @SerializedName("km/kg")
        KILOMETERS_PER_KILOGRAM,
        @SerializedName("mpkg")
        MILES_PER_KILOGRAM
    }

    enum class TirePressureUnits {
        @SerializedName("kPa")
        KILOPASCAL,
        @SerializedName("Bar")
        BAR,
        @SerializedName("Psi")
        POUNDS_PER_SQUARE_INCH
    }

    enum class TemperatureUnits {
        @SerializedName("Celsius")
        CELSIUS,
        @SerializedName("Fahrenheit")
        FAHRENHEIT
    }

    companion object {
        fun defaultUnitPreferences() =
            UnitPreferences(
                clockHours = ClockHoursUnits.TYPE_24H,
                speedDistance = SpeedDistanceUnits.KILOMETERS,
                consumptionCo = ConsumptionCoUnits.LITERS_PER_100_KILOMETERS,
                consumptionEv = ConsumptionEvUnits.KILOWATT_HOURS_PER_100_KILOMETERS,
                consumptionGas = ConsumptionGasUnits.KILOGRAM_PER_100_KILOMETERS,
                tirePressure = TirePressureUnits.KILOPASCAL,
                temperature = TemperatureUnits.CELSIUS
            )

        fun clockHoursUnitFromInt(ordinal: Int?) =
            ClockHoursUnits.values().getOrElse(ordinal ?: -1) { ClockHoursUnits.TYPE_24H }

        fun speedDistanceUnitFromInt(ordinal: Int?) =
            SpeedDistanceUnits.values().getOrElse(ordinal ?: -1) { SpeedDistanceUnits.KILOMETERS }

        fun consumptionCoUnitFromInt(ordinal: Int?) =
            ConsumptionCoUnits.values().getOrElse(ordinal ?: -1) { ConsumptionCoUnits.LITERS_PER_100_KILOMETERS }

        fun consumptionEvUnitFromInt(ordinal: Int?) =
            ConsumptionEvUnits.values().getOrElse(ordinal ?: -1) { ConsumptionEvUnits.KILOWATT_HOURS_PER_100_KILOMETERS }

        fun consumptionGasUnitFromInt(ordinal: Int?) =
            ConsumptionGasUnits.values().getOrElse(ordinal ?: -1) { ConsumptionGasUnits.KILOGRAM_PER_100_KILOMETERS }

        fun tirePressureUnitFromInt(ordinal: Int?) =
            TirePressureUnits.values().getOrElse(ordinal ?: -1) { TirePressureUnits.KILOPASCAL }

        fun temperatureUnitFromInt(ordinal: Int?) =
            TemperatureUnits.values().getOrElse(ordinal ?: -1) { TemperatureUnits.CELSIUS }
    }
}
