package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleSoftwareUpdateInformation
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Vehicle Software Update information (RUT campaign history)
 */
internal data class ApiVehicleSoftwareUpdateInformation(
    /**
     * Total count of all software updates for the vehicle (recent + available)
     * minimum: 0
     * example: 100
     */
    @SerializedName("totalUpdates") val totalUpdates: Int,
    /**
     * Count of available updates for the vehicle (not installed yet)
     * minimum: 0
     * example: 5
     */
    @SerializedName("availableUpdates") val availableUpdates: Int,
    /**
     * timestamp (date and time) of last synchronization of update campaigns for the vehicle
     * example: 2020-11-19T08:00:00.000Z
     */
    @SerializedName("lastSynchronization") val lastSynchronization: Date?,
    /**
     * List of updates
     */
    @SerializedName("updates") val updates: List<ApiVehicleSoftwareUpdateItem>
) : Mappable<VehicleSoftwareUpdateInformation> {

    override fun map(): VehicleSoftwareUpdateInformation = toVehicleSoftwareUpdateInformation()
}

internal fun ApiVehicleSoftwareUpdateInformation.toVehicleSoftwareUpdateInformation() =
    VehicleSoftwareUpdateInformation(
        totalUpdates,
        availableUpdates,
        lastSynchronization,
        updates.map { it.toVehicleSoftwareUpdateItem() }
    )
