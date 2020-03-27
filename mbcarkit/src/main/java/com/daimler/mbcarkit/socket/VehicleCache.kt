package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.Vehicles

interface VehicleCache {

    fun updateVehicles(vehicles: Vehicles)

    fun loadVehicles(): Vehicles

    fun clearVehicles()

    fun deleteVehicle(finOrVin: String)

    fun deleteVehicles(vehicles: Vehicles)

    fun loadVehicleByVin(finOrVin: String): VehicleInfo?
}
