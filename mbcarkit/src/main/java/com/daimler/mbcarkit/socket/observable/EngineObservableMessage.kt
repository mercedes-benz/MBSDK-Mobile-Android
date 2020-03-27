package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.Engine
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class EngineObservableMessage(engine: Engine) : VehicleObservableMessage<Engine> (engine) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || engineChanged(oldVehicleStatus.engine, updatedVehicleStatus.engine)
    }

    private fun engineChanged(oldEngine: Engine, updatedEngine: Engine): Boolean {
        return (oldEngine.ignitionState != updatedEngine.ignitionState)
            .or(oldEngine.engineRunningState != updatedEngine.engineRunningState)
            .or(oldEngine.ignitionState != updatedEngine.ignitionState)
            .or(oldEngine.remoteStartActiveState != updatedEngine.remoteStartActiveState)
            .or(oldEngine.remoteStartTemperature != updatedEngine.remoteStartTemperature)
            .or(oldEngine.remoteStartEndtime != updatedEngine.remoteStartEndtime)
    }
}
