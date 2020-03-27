package com.daimler.mbcarkit.proto.tmp.car

import com.daimler.mbcarkit.business.model.DebugMessage

fun com.daimler.mbprotokit.dto.car.DebugMessage.map() = DebugMessage(
    timestamp = timestamp,
    message = message
)
