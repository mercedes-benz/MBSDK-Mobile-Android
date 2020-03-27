package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Distance(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Distance since reset electrical in given distance unit
     * Range: 0..999999.9
     */
    val electricalReset: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DISTANCE_ELECTRICAL_RESET
    )

    /**
     * Distance since start electrical in given distance unit
     * Range: 0..999999.9
     */
    val electricalStart: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DISTANCE_ELECTRICAL_START
    )

    /**
     * Distance since reset GAS in given distance unit (LPG and H2 vehicles)
     * Range: 0..999999.9
     */
    val gasReset: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DISTANCE_GAS_RESET
    )

    /**
     * Distance since start GAS in given distance unit (LPG and H2 vehicles)
     * Range: 0..999999.9
     */
    val gasStart: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DISTANCE_GAS_START
    )

    /**
     * Distance since reset liquid in given distance unit (combustion vehicles)
     * Range: 0..999999.9
     */
    val liquidReset: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DISTANCE_RESET
    )

    /**
     * Distance since start liquid in given distance unit (combustion vehicles)
     * Range: 0..999999.9
     */
    val liquidStart: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DISTANCE_START
    )

    /**
     * Distance since reset zero emission in given distance unit (pure electric vehicles)
     * Range: 0..999999.9
     */
    val zeReset: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DISTANCE_ZERESET
    )

    /**
     * Distance since start zero emission in given distance unit (pure electric vehicles)
     * Range: 0..999999.9
     */
    val zeStart: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DISTANCE_ZESTART
    )
}
