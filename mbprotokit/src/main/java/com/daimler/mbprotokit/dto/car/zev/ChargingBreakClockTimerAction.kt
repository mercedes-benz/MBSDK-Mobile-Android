package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.generated.VehicleEvents

enum class ChargingBreakClockTimerAction(val action: VehicleEvents.ChargingBreakClockTimerAction) {
    DELETE(VehicleEvents.ChargingBreakClockTimerAction.DELETE),
    ACTIVATE(VehicleEvents.ChargingBreakClockTimerAction.ACTIVATE),
    DEACTIVATE(VehicleEvents.ChargingBreakClockTimerAction.DEACTIVATE),
    UNRECOGNIZED(VehicleEvents.ChargingBreakClockTimerAction.UNRECOGNIZED);

    companion object {
        fun map(action: VehicleEvents.ChargingBreakClockTimerAction?) = values().find {
            action == it.action
        } ?: UNRECOGNIZED
    }
}
