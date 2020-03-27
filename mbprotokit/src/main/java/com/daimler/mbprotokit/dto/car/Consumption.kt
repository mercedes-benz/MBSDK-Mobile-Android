package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Consumption(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Electric consumption from reset of trip
     * Range: 0..655.3
     */
    val electricConsumptionReset: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ELECTRIC_CONSUMPTION_RESET
    )

    /**
     * Electric consumption from start of trip
     * Range: 0..655.3
     */
    val electricConsumptionStart: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ELECTRIC_CONSUMPTION_START
    )

    /**
     * Gaseous fuel consumption from reset in given consumption unit
     * Range: 0..100.
     */
    val gasConsumptionReset: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.GAS_CONSUMPTION_RESET
    )

    /**
     * Gaseous fuel consumption from start in given consumption unit
     * Range: 0..100.0
     */
    val gasConsumptionStart: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.GAS_CONSUMPTION_START
    )

    /**
     * Liquid fuel consumption from rest in given consumption unit
     * Range: 0..100.0
     */
    val liquidConsumptionReset: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.LIQUID_CONSUMPTION_RESET
    )

    /**
     * Liquid fuel consumption from start in given consumption unit
     * Range: 0..100.0
     */
    val liquidConsumptionStart: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.LIQUID_CONSUMPTION_START
    )
}
