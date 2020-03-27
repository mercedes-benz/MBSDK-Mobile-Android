package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbnetworkkit.socket.message.ObservableMessage

abstract class VehicleObservableMessage<T>(
    data: T
) : ObservableMessage<T>(data) {

    internal abstract fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean
}
