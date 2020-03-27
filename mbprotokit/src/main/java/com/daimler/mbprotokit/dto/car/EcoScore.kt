package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class EcoScore(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {

    /**
     * ECOscore rating acceleration percentage points
     * Range: 0..100
     */
    val accel: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ECO_SCORE_ACCELL
    )

    /**
     * ECOscore rating bonus range in km
     * Range: 0..1638.0
     */
    val bonusRange: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ECO_SCORE_BONUS_RANGE
    )

    /**
     * ECOscore rating constancy percentage points
     * Range: 0..100
     */
    val const: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ECO_SCORE_CONST
    )

    /**
     * ECOscore rating free wheeling percentage points
     * Range: 0..100
     */
    val freeWhl: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ECO_SCORE_FREE_WHL
    )

    /**
     * ECOscore overall rating percentage points
     * Range: 0..100
     */
    val total: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ECO_SCORE_TOTAL
    )
}
