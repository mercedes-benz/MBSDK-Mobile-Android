package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.generated.VehicleEvents

data class VehicleChargingBreakClockTimer(
    val action: ChargingBreakClockTimerAction,
    val endTimeHour: Int,
    val endTimeMin: Int,
    val startTimeHour: Int,
    val startTimeMin: Int,
    val timerId: Long
) {

    companion object {
        fun mapVehicleChargingBreakClockTimer(): (VehicleEvents.ChargingBreakClockTimer?) -> List<VehicleChargingBreakClockTimer>? = {
            it?.clockTimerList?.map { entry ->
                VehicleChargingBreakClockTimer(
                    action = ChargingBreakClockTimerAction.map(entry.action),
                    endTimeHour = entry.endTimeHour,
                    endTimeMin = entry.endTimeMin,
                    startTimeHour = entry.startTimeHour,
                    startTimeMin = entry.startTimeMin,
                    timerId = entry.timerId
                )
            }
        }
    }
}
