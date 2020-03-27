package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdate

interface VehicleStatusCache {

    fun update(finOrVin: String, vehicleStatusUpdate: VehicleStatusUpdate): VehicleStatus
    fun currentVehicleState(finOrVin: String): VehicleStatus
    fun clearVehicleStates()
    fun clearVehicleState(finOrVin: String): Boolean
}
