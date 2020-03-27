package com.daimler.mbcarkit.business.model.vehicle

data class ZeResetStart<VAL, UNIT : Enum<*>>(
    var reset: VehicleAttribute<VAL, UNIT>,
    var start: VehicleAttribute<VAL, UNIT>,
    val ze: ResetStart<Int, UNIT>
)
