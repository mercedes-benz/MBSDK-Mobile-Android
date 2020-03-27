package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.Auxheat
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class AuxheatObservableMessage(auxheat: Auxheat) : VehicleObservableMessage<Auxheat>(auxheat) {
    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || auxheatChanged(oldVehicleStatus.auxheat, updatedVehicleStatus.auxheat)
    }

    private fun auxheatChanged(oldAuxheat: Auxheat, updatedAuxheat: Auxheat): Boolean {
        return (oldAuxheat.activeState != updatedAuxheat.activeState)
            .or(oldAuxheat.runtime != updatedAuxheat.runtime)
            .or(oldAuxheat.state != updatedAuxheat.state)
            .or(oldAuxheat.time1 != updatedAuxheat.time1)
            .or(oldAuxheat.time2 != updatedAuxheat.time2)
            .or(oldAuxheat.time3 != updatedAuxheat.time3)
            .or(oldAuxheat.timeSelection != updatedAuxheat.timeSelection)
            .or(oldAuxheat.warnings != updatedAuxheat.warnings)
    }
}
