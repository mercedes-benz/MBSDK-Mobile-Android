package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Speed(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Average speed since reset in given speed unit
     * Range: 0..405.0
     */
    val averageSpeedReset: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AVERAGE_SPEED_RESET
    )

    /**
     * Average speed since start in given speed unit
     * Range: 0..405.0
     */
    val averageSpeedStart: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AVERAGE_SPEED_START
    )
}
