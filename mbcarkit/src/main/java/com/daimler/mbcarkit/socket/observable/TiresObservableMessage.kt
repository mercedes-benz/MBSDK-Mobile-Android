package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.Tire
import com.daimler.mbcarkit.business.model.vehicle.Tires
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class TiresObservableMessage(tires: Tires) : VehicleObservableMessage<Tires>(tires) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || tiresChanged(oldVehicleStatus.tires, updatedVehicleStatus.tires)
    }

    private fun tiresChanged(oldTires: Tires, updateTires: Tires): Boolean {
        return (tireChanged(oldTires.frontLeft, updateTires.frontLeft))
            .or(tireChanged(oldTires.frontRight, updateTires.frontRight))
            .or(tireChanged(oldTires.rearLeft, updateTires.rearLeft))
            .or(tireChanged(oldTires.rearRight, updateTires.rearRight))
            .or(oldTires.pressureMeasurementTimestampInSeconds != updateTires.pressureMeasurementTimestampInSeconds)
            .or(oldTires.sensorAvailable != updateTires.sensorAvailable)
            .or(oldTires.warningLevelOverall != updateTires.warningLevelOverall)
    }

    private fun tireChanged(oldTire: Tire?, updateTire: Tire?): Boolean {
        return (oldTire?.pressureKpa != updateTire?.pressureKpa)
            .or(oldTire?.warningLevel != updateTire?.warningLevel)
    }
}
