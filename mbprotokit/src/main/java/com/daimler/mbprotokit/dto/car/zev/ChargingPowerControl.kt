package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.generated.VehicleEvents

data class ChargingPowerControl(
    val chargingStatus: ChargingStatusForPowerControl,
    val controlDuration: Int,
    val controlInfo: Int,
    val chargingPower: Int,
    val serviceStatus: Int,
    val serviceAvailable: Int,
    val useCase: Int
) {

    companion object {
        fun mapToChargingPowerControl(): (VehicleEvents.ChargingPowerControl?) -> ChargingPowerControl? =
            {
                it?.let { control ->
                    ChargingPowerControl(
                        chargingStatus = ChargingStatusForPowerControl.map(control.chargeStatus)
                            ?: ChargingStatusForPowerControl.NOT_DEFINED,
                        controlDuration = control.ctrlDuration,
                        controlInfo = control.ctrlInfo,
                        chargingPower = control.chargePower,
                        serviceStatus = control.servStat,
                        serviceAvailable = control.servAvail,
                        useCase = control.useCase
                    )
                }
            }
    }
}
