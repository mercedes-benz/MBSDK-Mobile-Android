package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.generated.VehicleEvents

data class SocProfile(
    /**
     * Timestamp in seconds, UTC
     */
    val time: Long,

    /**
     * State of Charge
     * Range: 0..100
     */
    val soc: Int
) {
    companion object {
        fun mapToSocProfiles(): (VehicleEvents.StateOfChargeProfileValue?) -> List<SocProfile>? = {
            it?.statesOfChargeList?.map { charge ->
                SocProfile(
                    time = charge.timestampInS,
                    soc = charge.stateOfCharge
                )
            }
        }
    }
}
