package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.network.model.ApiVehicle
import com.daimler.mbcarkit.network.model.toVehicleInfo

data class Vehicles(val vehicles: List<VehicleInfo>, val isUpdated: Boolean = true) : Iterable<VehicleInfo> {

    override fun iterator(): Iterator<VehicleInfo> {
        return vehicles.iterator()
    }
}

internal fun List<ApiVehicle>.toVehicles() =
    Vehicles(map { it.toVehicleInfo() }, true)
