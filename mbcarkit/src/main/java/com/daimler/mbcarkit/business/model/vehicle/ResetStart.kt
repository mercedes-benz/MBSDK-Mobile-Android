package com.daimler.mbcarkit.business.model.vehicle

data class ResetStart <VAL, UNIT : Enum<*>>(
    var reset: VehicleAttribute<VAL, UNIT>,
    var start: VehicleAttribute<VAL, UNIT>
)
