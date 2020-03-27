package com.daimler.mbcarkit.business.model.vehicle

data class VehicleChargingPowerControl(
    val chargingStatus: ChargingStatusForPowerControl,
    val controlDuration: Int,

    /**
     * 0-14, Placeholder values for now.
     */
    val controlInfo: Int,
    val chargingPower: Int,

    /**
     * 0: Not defined
     * 1: Service not active
     * 2: Service active
     */
    val serviceStatus: Int,

    /**
     * 0: Not defined
     * 1: Service not available
     * 2: Service available
     */
    val serviceAvailable: Int,

    /**
     * 0: Undefined use case
     * 1: Undefined use case Mercedes-Benz
     * 2: Undefined use case third party
     * 3: Time of use
     * 4: Solar charging
     * 5: Control of variable charging power based on time schedule
     * 6: Control of variable charging and discharging power based on time schedule
     * 7..62: Undefined
     */
    val useCase: Int
)
