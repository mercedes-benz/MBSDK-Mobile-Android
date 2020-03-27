package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class TemperatureUnit(
    val temperatureUnit: VehicleAttributeStatus.TemperatureUnit
) : Unit {
    UNSPECIFIED_TEMPERATURE_UNIT(VehicleAttributeStatus.TemperatureUnit.UNSPECIFIED_TEMPERATURE_UNIT),
    CELSIUS(VehicleAttributeStatus.TemperatureUnit.CELSIUS),
    FAHRENHEIT(VehicleAttributeStatus.TemperatureUnit.FAHRENHEIT),
    UNRECOGNIZED(VehicleAttributeStatus.TemperatureUnit.UNRECOGNIZED);

    companion object {
        fun map(temperatureUnit: VehicleAttributeStatus.TemperatureUnit) = values().find {
            temperatureUnit == it.temperatureUnit
        } ?: UNRECOGNIZED
    }
}
