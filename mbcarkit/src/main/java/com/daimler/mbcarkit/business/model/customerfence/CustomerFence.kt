package com.daimler.mbcarkit.business.model.customerfence

import com.daimler.mbcarkit.business.model.vehicle.Day

data class CustomerFence(
    val customerFenceId: Int?,
    val geoFenceId: Int?,
    /**
     * User defined name of the fence (max. 128 characters)
     */
    val name: String?,
    val days: List<Day>,
    /**
     * Minutes from midnight (range: 0.. 1439). If begin > end, a midnight overlap is assumed (e.g., 23h-02h).
     */
    val beginMinutes: Int?,
    /**
     * Minutes from midnight (range: 0..1439). If begin > end, a midnight overlap is assumed (e.g., 23h-02h).
     */
    val endMinutes: Int?,
    /**
     * UTC timestamp in seconds (ISO 9945)
     */
    val timestamp: Long?,
    val violationType: CustomerFenceViolationType?
)
