package com.daimler.mbcarkit.proto.tmp.car

import com.daimler.mbcarkit.business.model.vehicle.VehicleUpdate

fun com.daimler.mbprotokit.dto.car.VehicleAuthUpdate.map() = VehicleUpdate(
    eventTimeStamp = eventTimeStamp,
    sequenceNumber = sequenceNumber
)
