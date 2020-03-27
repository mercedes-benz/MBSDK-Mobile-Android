package com.daimler.mbcarkit.business.model.consumption

data class Individual30DaysConsumptionData(
    /**
     * Consumption average over the last 30 days
     */
    val value: Double,
    /**
     * Timestamp in seconds since 1970
     */
    val lastUpdated: Long?,
    /**
     * Unit of the value
     */
    val unit: VehicleConsumptionUnit
)
