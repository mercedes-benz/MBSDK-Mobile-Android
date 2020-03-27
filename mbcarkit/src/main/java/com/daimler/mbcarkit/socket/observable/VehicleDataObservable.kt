package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.VehicleData
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.util.isUpdatedBy

class VehicleDataObservable(vehicleData: VehicleData) : VehicleObservableMessage<VehicleData>(vehicleData) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || vehicleDataChanged(oldVehicleStatus.vehicle, updatedVehicleStatus.vehicle)
    }

    private fun vehicleDataChanged(oldVehicleData: VehicleData, updatedVehicleData: VehicleData) =
        oldVehicleData.getAllAttributes().isUpdatedBy(updatedVehicleData.getAllAttributes())
}
