package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class VehicleStatusObservableMessage(vehicleStatus: VehicleStatus) : VehicleObservableMessage<VehicleStatus>(vehicleStatus) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || contentHasChanged(oldVehicleStatus, updatedVehicleStatus)
    }

    private fun contentHasChanged(oldVehicleStatus: VehicleStatus, updatedVehicleStatus: VehicleStatus): Boolean {
        return AuxheatObservableMessage(updatedVehicleStatus.auxheat).hasChanged(oldVehicleStatus, updatedVehicleStatus)
            .or(DoorsObservableMessage(updatedVehicleStatus.doors).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(DrivingModesObservableMessage(updatedVehicleStatus.drivingModes).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(EcoScoreObservableMessage(updatedVehicleStatus.ecoScore).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(HeadUnitObservableMessage(updatedVehicleStatus.hu).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(LocationObservableMessage(updatedVehicleStatus.location).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(StatisticsObservableMessage(updatedVehicleStatus.statistics).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(TankObservableMessage(updatedVehicleStatus.tank).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(TiresObservableMessage(updatedVehicleStatus.tires).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(VehicleDataObservable(updatedVehicleStatus.vehicle).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(WarningsObservableMessage(updatedVehicleStatus.warnings).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(WindowsObservableMessage(updatedVehicleStatus.windows).hasChanged(oldVehicleStatus, updatedVehicleStatus))
            .or(ZevObservableMessage(updatedVehicleStatus.zev).hasChanged(oldVehicleStatus, updatedVehicleStatus))
    }
}
