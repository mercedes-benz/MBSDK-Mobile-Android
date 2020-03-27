package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Decklid(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Deck lid latch status:
     * isUnlocked
     *
     * false: locked
     * true: unlocked
     * null: unknown
     */
    val lockState: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATUS_DECKLID
    )

    /**
     * is deck lid open
     * false: closed
     * true: open
     * null: unknown
     */
    val state: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DECKLID_STATUS
    )
}
