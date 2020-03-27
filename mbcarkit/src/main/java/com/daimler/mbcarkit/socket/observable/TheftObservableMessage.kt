package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.Collision
import com.daimler.mbcarkit.business.model.vehicle.Theft
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class TheftObservableMessage(theft: Theft) : VehicleObservableMessage<Theft>(theft) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || theftChanged(oldVehicleStatus.theft, updatedVehicleStatus.theft)
    }

    private fun theftChanged(oldTheft: Theft, updatedTheft: Theft): Boolean {
        return (oldTheft.interiorProtectionStatus != updatedTheft.interiorProtectionStatus)
            .or(oldTheft.towProtectionSensorStatus != updatedTheft.towProtectionSensorStatus)
            .or(oldTheft.alarmActive != updatedTheft.alarmActive)
            .or(oldTheft.systemArmed != updatedTheft.systemArmed)
            .or(oldTheft.keyActivationState != updatedTheft.keyActivationState)
            .or(collisionChanged(oldTheft.collision, updatedTheft.collision))
    }

    private fun collisionChanged(oldCollision: Collision?, updateCollision: Collision?): Boolean {
        return (oldCollision?.parkEventType != updateCollision?.parkEventType)
            .or(oldCollision?.parkEventLevel != updateCollision?.parkEventLevel)
            .or(oldCollision?.lastParkEvent != updateCollision?.lastParkEvent)
            .or(oldCollision?.lastParkEventNotConfirmed != updateCollision?.lastParkEventNotConfirmed)
            .or(oldCollision?.parkEventSensorStatus != updateCollision?.parkEventSensorStatus)
    }
}
