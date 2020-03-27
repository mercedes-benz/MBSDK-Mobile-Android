package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.generated.VehicleEvents

enum class TemperatureZone(internal val value: String) {
    FRONT_LEFT("frontLeft"),
    FRONT_RIGHT("frontRight"),
    FRONT_CENTER("frontCenter"),
    REAR_LEFT("rearLeft"),
    REAR_RIGHT("rearRight"),
    REAR_CENTER("rearCenter"),
    REAR_2_LEFT("rear2left"),
    REAR_2_RIGHT("rear2right"),
    REAR_2_CENTER("rear2center")
}

object TemperatureSeats {
    // TODO temperature display unit case. Currently, Apps doesn't use units yet
    fun mapToTemperaturePoints(): (VehicleEvents.TemperaturePointsValue?) -> Map<TemperatureZone, Double?> = {
        val temperatureMap = mutableMapOf<TemperatureZone, Double?>()
        TemperatureZone.values().forEach { zone ->
            temperatureMap[zone] = it?.temperaturePointsList?.firstOrNull { point ->
                point.zone == zone.value
            }?.temperature
        }
        temperatureMap.toMap()
    }
}
