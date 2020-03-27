package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class DrivenTime(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Driven Time since reset in minutes
     * Range: 0..600000
     */
    val timeReset: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DRIVEN_TIME_RESET
    )

    /**
     * Driven Time since start in minutes
     * Range: 0..600000
     */
    val timeStart: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DRIVEN_TIME_START
    )

    /**
     * Driven Time Zero Emission since reset in minutes
     * Range: 0..600000
     */
    val zeTimeReset: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DRIVEN_TIME_ZERESET
    )

    /**
     * Driven Time Zero Emission since start in minutes
     * Range: 0..600000
     */
    val zeTimeStart: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DRIVEN_TIME_ZESTART
    )
}
