package com.daimler.mbcarkit.business.model.speedfence

import com.daimler.mbcarkit.business.model.onboardfence.SyncStatus

data class SpeedFence(
    val geoFenceId: Int?,
    /**
     * User defined name of the fence (max. 128 characters)
     */
    val name: String?,
    val isActive: Boolean?,
    /**
     * UTC timestamp in seconds (ISO 9945)
     */
    val endTime: Long?,
    /**
     * Threshold is a value in (range: 0..511)
     */
    val threshold: Int?,
    /**
     * Delay in minutes
     */
    val violationDelay: Int?,
    val violationTypes: List<SpeedFenceViolationType>,
    /**
     * UTC timestamp in seconds (ISO 9945)
     */
    val timestamp: Long?,
    val speedFenceId: Int?,
    val syncStatus: SyncStatus?
)
