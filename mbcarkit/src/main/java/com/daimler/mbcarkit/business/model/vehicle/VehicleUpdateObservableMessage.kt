package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbnetworkkit.socket.message.ObservableMessage

class VehicleUpdateObservableMessage(
    vehicleUpdate: VehicleUpdate
) : ObservableMessage<VehicleUpdate>(vehicleUpdate)
