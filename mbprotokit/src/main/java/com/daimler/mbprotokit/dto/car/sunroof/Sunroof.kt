package com.daimler.mbprotokit.dto.car.sunroof

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Sunroof(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {

    /**
     * Event state rain close
     * 0: No event
     * 1: Lift position because of rain
     * 2: Lift position automatic
     * 3: Lift position because of timer
     */
    val eventState: Pair<SunroofEventState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SUNROOF_EVENT,
        SunroofEventState.map()
    )

    /**
     * Rainclose monitoring active
     * false: not active
     * true: active
     */
    val eventActive: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SUNROOF_EVENT_ACTIVE
    )

    /**
     * Sunroof state
     * 0:  "Tilt/slide sunroof is closed"
     * 1:  "Tilt/slide sunroof is complete open"
     * 2:  "Lifting roof is open"
     * 3:  "Tilt/slide sunroof is running" (only available if no differentiation between opening/closing possible)
     * 4:  "Tilt/slide sunroof in anti-booming position"
     * 5:  "Sliding roof in intermediate position"
     * 6:  "Lifting roof in intermediate position"
     * 7:  "Sunroof is opening"
     * 8:  "Sunroof is closing"
     * 9:  "Lifting anti booming"
     * 10: "Sunroof in intermediate position"
     * 11: "Lifting roof is opening"
     * 12: "Lifting roof is closing"
     */
    val state: Pair<SunroofState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SUNROOF_STATUS,
        SunroofState.map()
    )

    /**
     * Front roller blind state sunroof
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     * 3: opening
     * 4: closing
     */
    val blindFrontState: Pair<SunroofBlindState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SUNROOF_BLIND_STATUS_FRONT,
        SunroofBlindState.map()
    )

    /**
     * Rear roller blind state sunroof
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     * 3: opening
     * 4: closing
     */
    val blindRearState: Pair<SunroofBlindState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SUNROOF_BLIND_STATUS_REAR,
        SunroofBlindState.map()
    )
}
