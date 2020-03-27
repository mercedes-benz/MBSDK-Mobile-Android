package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.generated.VehicleEvents

enum class ChargingProgram(val chargeProgram: VehicleEvents.ChargeProgram) {
    DEFAULT(VehicleEvents.ChargeProgram.DEFAULT_CHARGE_PROGRAM),

    /**  INSTANT can only be received and not be used as parameter for commands. */
    INSTANT(VehicleEvents.ChargeProgram.INSTANT_CHARGE_PROGRAM),
    HOME(VehicleEvents.ChargeProgram.HOME_CHARGE_PROGRAM),
    WORK(VehicleEvents.ChargeProgram.WORK_CHARGE_PROGRAM);

    companion object {
        fun map(chargeProgram: VehicleEvents.ChargeProgram?) = values().find {
            chargeProgram == it.chargeProgram
        }

        fun map(): (Int?) -> ChargingProgram? = { map(VehicleEvents.ChargeProgram.forNumber(it ?: -1)) }
    }
}
