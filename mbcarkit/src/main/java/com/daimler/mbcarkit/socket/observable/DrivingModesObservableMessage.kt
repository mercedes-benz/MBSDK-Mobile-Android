package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.DrivingModes
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class DrivingModesObservableMessage(drivingModes: DrivingModes) :
    VehicleObservableMessage<DrivingModes>(drivingModes) {

    override fun hasChanged(
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) =
        oldVehicleStatus == null ||
            drivingModesChanged(
                oldVehicleStatus.drivingModes,
                updatedVehicleStatus.drivingModes
            )

    private fun drivingModesChanged(
        oldDrivingModes: DrivingModes,
        updatedDrivingModes: DrivingModes
    ) =
        (oldDrivingModes.teenageDrivingMode != updatedDrivingModes.teenageDrivingMode)
            .or(oldDrivingModes.valetDrivingMode != updatedDrivingModes.valetDrivingMode)
}
