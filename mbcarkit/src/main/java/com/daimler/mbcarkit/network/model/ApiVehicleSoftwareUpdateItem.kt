package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleSoftwareUpdateItem
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Vehicle Software Update item (single campaign/software update)
 */
internal data class ApiVehicleSoftwareUpdateItem(
    /**
     * Localized title of the campaign/software update item
     * example: Update for the communication module
     */
    @SerializedName("title") val title: String,
    /**
     * Localized short description of the campaign/software update item
     * example: This free update contains optimisations for Mercedes me connect.
     */
    @SerializedName("description") val description: String,
    /**
     * issuing timestamp (date and time) of the campaign/software update item
     * example: 2020-11-18T10:00:00.000Z
     */
    @SerializedName("timestamp") val timestamp: Date?,
    /**
     * current status of the update operation
     */
    @SerializedName("status") val status: ApiVehicleSoftwareUpdateStatus,
) : Mappable<VehicleSoftwareUpdateItem> {

    override fun map(): VehicleSoftwareUpdateItem = toVehicleSoftwareUpdateItem()
}

internal fun ApiVehicleSoftwareUpdateItem.toVehicleSoftwareUpdateItem() = VehicleSoftwareUpdateItem(
    title,
    description,
    timestamp,
    status.toVehicleSoftwareUpdateStatus()
)
