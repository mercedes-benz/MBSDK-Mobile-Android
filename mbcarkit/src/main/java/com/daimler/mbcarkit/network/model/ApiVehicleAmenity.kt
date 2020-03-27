package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleAmenity
import com.google.gson.annotations.SerializedName

internal data class ApiVehicleAmenity(
    @SerializedName("code") val code: String?,
    @SerializedName("description") val description: String?
)

internal fun ApiVehicleAmenity?.toVehicleAmenity(): VehicleAmenity? =
    this?.let { VehicleAmenity(it.code, it.description) }
