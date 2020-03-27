package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.Location
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class LocationObservableMessage(location: Location) : VehicleObservableMessage<Location>(location) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || locationChanged(oldVehicleStatus.location, updatedVehicleStatus.location)
    }

    private fun locationChanged(oldLocation: Location, updateLocation: Location): Boolean {
        return (oldLocation.longitude != updateLocation.longitude)
            .or(oldLocation.latitude != updateLocation.latitude)
            .or(oldLocation.heading != updateLocation.heading)
            .or(oldLocation.positionErrorState != updateLocation.positionErrorState)
            .or(oldLocation.proximityCalculationRequired != updateLocation.proximityCalculationRequired)
    }
}
