package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.generated.VehicleEvents

data class VehicleChargingProgramParameter(
    /**
     * The charging program.
     */
    val program: ChargingProgram?,

    /**
     * Values need to be between 50 and 100 and divisible by ten.
     * Maximum value for the state of charge of the HV battery [in %].
     * Valid value range = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100].
     */
    val maxSoc: Int,

    /**
     * Denotes whether the charge cable should be unlocked automatically
     * if the HV battery is fully charged resp. charged until [maxSoc] value.
     */
    val autoUnlock: Boolean,

    /**
     * Automatically switch between home and work program, based on the location of the car.
     */
    val locationBasedCharging: Boolean,

    val weeklyProfile: Boolean,
    val clockTimer: Boolean,

    /**
     * Current max charging amount.
     */
    val maxChargingCurrent: Int,

    /**
     * True if ECO charging mode is activated.
     */
    val ecoCharging: Boolean
) {
    companion object {
        fun mapToVehicleChargingProgramParameters(): (VehicleEvents.ChargeProgramsValue?) -> List<VehicleChargingProgramParameter>? = {
            it?.chargeProgramParametersList?.map { param ->
                VehicleChargingProgramParameter(
                    program = ChargingProgram.map(param.chargeProgram),
                    maxSoc = param.maxSoc,
                    autoUnlock = param.autoUnlock,
                    locationBasedCharging = param.locationBasedCharging,
                    weeklyProfile = param.weeklyProfile,
                    clockTimer = param.clockTimer,
                    maxChargingCurrent = param.maxChargingCurrent,
                    ecoCharging = param.ecoCharging
                )
            }
        }
    }
}
