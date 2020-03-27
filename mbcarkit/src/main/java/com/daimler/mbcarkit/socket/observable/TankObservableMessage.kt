package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.Tank
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class TankObservableMessage(tank: Tank) : VehicleObservableMessage<Tank>(tank) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || tankChanged(oldVehicleStatus.tank, updatedVehicleStatus.tank)
    }

    private fun tankChanged(oldTank: Tank, updateTank: Tank): Boolean {
        return (oldTank.adBlueLevel != updateTank.adBlueLevel)
            .or(oldTank.electricLevel != updateTank.electricLevel)
            .or(oldTank.electricRange != updateTank.electricRange)
            .or(oldTank.gasLevel != updateTank.gasLevel)
            .or(oldTank.gasRange != updateTank.gasRange)
            .or(oldTank.liquidLevel != updateTank.liquidLevel)
            .or(oldTank.liquidRange != updateTank.liquidRange)
    }
}
