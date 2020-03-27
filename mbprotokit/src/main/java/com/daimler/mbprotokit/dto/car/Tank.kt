package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Tank(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Tanklevel Ad Blue in percent
     * Range 0..100
     */
    val adBlueLevel: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TANK_LEVEL_ADBLUE
    )

    /**
     * Displayed battery State of charge in percent (High voltage battery)
     * Range: 0..100
     */
    val electricalLevel: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SOC
    )

    /**
     * Remaining range electric engine in given distance unit
     * Range: 0..4000
     */
    val electricalRange: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.RANGE_ELECTRIC
    )

    /**
     * Gas tanklevel in liters (only for LPG vehicles)
     * Range: 0..204.6
     */
    val gasLevel: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.GAS_TANK_LEVEL
    )

    /**
     * Gas tankrange in given distance unit. For LPG and hydrogen vehicles.
     * Range: 2046
     */
    val gasRange: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.GAS_TANK_RANGE
    )

    /**
     * Liquid fuel tank level in percent
     */
    val liquidLevel: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TANK_LEVEL_PERCENT
    )

    /**
     * Remaining liquid fuel range in given distance unit
     * Range: 0..2046
     */
    val liquidRange: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.RANGE_LIQUID
    )

    /**
     * Overall range combined over all fuel types.
     */
    val overallRange: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.OVERALL_RANGE
    )
}
