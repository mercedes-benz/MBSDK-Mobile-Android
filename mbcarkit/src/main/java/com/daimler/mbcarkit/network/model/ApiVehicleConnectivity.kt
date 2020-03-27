package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleConnectivity
import com.daimler.mbcarkit.network.model.ApiVehicleConnectivity.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiVehicleConnectivity {
    @SerializedName("NONE") NONE,
    @SerializedName("ADAPTER") ADAPTER,
    @SerializedName("BUILTIN") BUILT_IN;

    companion object {
        val map: Map<String, VehicleConnectivity> = VehicleConnectivity.values().associateBy(VehicleConnectivity::name)
    }
}

internal fun ApiVehicleConnectivity?.toVehicleConnectivity(): VehicleConnectivity? =
    this?.let { map[name] }
