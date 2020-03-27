package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.Door
import com.daimler.mbcarkit.business.model.vehicle.Doors
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class DoorsObservableMessage(doors: Doors) : VehicleObservableMessage<Doors>(doors) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || doorStatusChanged(oldVehicleStatus.doors, updatedVehicleStatus.doors)
    }

    private fun doorStatusChanged(oldDoors: Doors, updatedDoors: Doors): Boolean {
        return doorChanged(oldDoors.rearLeft, updatedDoors.rearLeft)
            .or(doorChanged(oldDoors.rearRight, updatedDoors.rearRight))
            .or(doorChanged(oldDoors.frontLeft, updatedDoors.frontLeft))
            .or(doorChanged(oldDoors.frontRight, updatedDoors.frontRight))
            .or(doorChanged(oldDoors.decklid, updatedDoors.decklid))
            .or(oldDoors.stateOverall != updatedDoors.stateOverall)
            .or(oldDoors.lockStateOverall != updatedDoors.lockStateOverall)
    }

    private fun doorChanged(oldDoor: Door, updateDoor: Door): Boolean {
        return (oldDoor.lockStatus != updateDoor.lockStatus)
            .or(oldDoor.state != updateDoor.state)
    }
}
