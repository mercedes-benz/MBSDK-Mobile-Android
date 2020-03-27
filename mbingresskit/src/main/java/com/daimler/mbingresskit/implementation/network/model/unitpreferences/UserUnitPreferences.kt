package com.daimler.mbingresskit.implementation.network.model.unitpreferences

import com.daimler.mbingresskit.common.UnitPreferences
import com.google.gson.annotations.SerializedName

internal data class UserUnitPreferences(
    @SerializedName("clockHours") val clockHours: UnitPreferences.ClockHoursUnits,
    @SerializedName("speedDistance") val speedDistance: UnitPreferences.SpeedDistanceUnits,
    @SerializedName("consumptionCo") val consumptionCo: UnitPreferences.ConsumptionCoUnits,
    @SerializedName("consumptionEv") val consumptionEv: UnitPreferences.ConsumptionEvUnits,
    @SerializedName("consumptionGas") val consumptionGas: UnitPreferences.ConsumptionGasUnits,
    @SerializedName("tirePressure") val tirePressure: UnitPreferences.TirePressureUnits,
    @SerializedName("temperature") val temperature: UnitPreferences.TemperatureUnits
)

internal fun UserUnitPreferences.toUnitPreferences() = UnitPreferences(
    clockHours = clockHours,
    speedDistance = speedDistance,
    consumptionCo = consumptionCo,
    consumptionEv = consumptionEv,
    consumptionGas = consumptionGas,
    tirePressure = tirePressure,
    temperature = temperature
)

internal fun UnitPreferences.toUserUnitPreferences() = UserUnitPreferences(
    clockHours = clockHours,
    speedDistance = speedDistance,
    consumptionCo = consumptionCo,
    consumptionEv = consumptionEv,
    consumptionGas = consumptionGas,
    tirePressure = tirePressure,
    temperature = temperature
)
