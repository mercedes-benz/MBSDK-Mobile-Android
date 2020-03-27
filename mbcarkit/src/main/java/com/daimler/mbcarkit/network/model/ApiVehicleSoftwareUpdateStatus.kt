package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleSoftwareUpdateStatus
import com.daimler.mbcarkit.network.model.ApiVehicleSoftwareUpdateStatus.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiVehicleSoftwareUpdateStatus {
    @SerializedName("PENDING") PENDING,
    @SerializedName("SUCCESSFUL") SUCCESSFUL,
    @SerializedName("FAILED") FAILED,
    @SerializedName("CANCELED") CANCELED;

    companion object {
        val map: Map<String, VehicleSoftwareUpdateStatus> = VehicleSoftwareUpdateStatus.values().associateBy(
            VehicleSoftwareUpdateStatus::name
        )
    }
}

internal fun ApiVehicleSoftwareUpdateStatus.toVehicleSoftwareUpdateStatus(): VehicleSoftwareUpdateStatus? =
    map[name]
