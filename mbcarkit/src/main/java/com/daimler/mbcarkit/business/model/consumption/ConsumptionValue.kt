package com.daimler.mbcarkit.business.model.consumption

data class ConsumptionValue(
    /**
     * Consumption
     */
    val consumption: Double,
    /**
     *
     */
    val group: Int?,
    /**
     *
     */
    val percentage: Double,
    /**
     *
     */
    val unit: VehicleConsumptionUnit
)
