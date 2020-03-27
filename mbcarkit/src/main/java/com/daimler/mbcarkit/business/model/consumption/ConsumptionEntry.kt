package com.daimler.mbcarkit.business.model.consumption

data class ConsumptionEntry(
    /**
     * Value changed
     */
    val changed: Boolean,
    /**
     * Consumption level
     */
    val value: Double,
    /**
     * Consumption unit
     */
    val unit: VehicleConsumptionUnit
)
