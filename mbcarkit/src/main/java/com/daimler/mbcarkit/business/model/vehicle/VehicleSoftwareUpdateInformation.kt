package com.daimler.mbcarkit.business.model.vehicle

import java.util.Date

/**
 * Vehicle Software Update information (RUT campaign history)
 */
data class VehicleSoftwareUpdateInformation(
    /**
     * Total count of all software updates for the vehicle (recent + available)
     * minimum: 0
     * example: 100
     */
    val totalUpdates: Int,
    /**
     * Count of available updates for the vehicle (not installed yet)
     * minimum: 0
     * example: 5
     */
    val availableUpdates: Int,
    /**
     * timestamp (date and time) of last synchronization of update campaigns for the vehicle
     * example: 2020-11-19T08:00:00.000Z
     */
    val lastSynchronization: Date?,
    /**
     * List of updates
     */
    val updates: List<VehicleSoftwareUpdateItem>
)
