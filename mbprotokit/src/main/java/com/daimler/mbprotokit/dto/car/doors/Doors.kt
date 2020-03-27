package com.daimler.mbprotokit.dto.car.doors

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Doors(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Door lock state front left
     * false: locked
     * true: unlocked
     * null: unknown
     */
    val doorLockStateFrontLeft: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATE_FRONT_LEFT
    )

    /**
     * Door open state front left
     * false: closed
     * true: open
     * null: unknown
     */
    val doorStateFrontLeft: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_STATE_FRONT_LEFT
    )

    /**
     * Door lock state front right
     * false: locked
     * true: unlocked
     */
    val doorLockStateFrontRight: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATE_FRONT_RIGHT
    )

    /**
     * Door open state front right
     * false: closed
     * true: open
     */
    val doorStateFrontRight: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_STATE_FRONT_RIGHT
    )

    /**
     * Door lock state rear left
     * false: locked
     * true: unlocked
     */
    val doorLockStateRearLeft: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATE_REAR_LEFT
    )

    /**
     * Door open state rear left
     * false: closed
     * true: open
     */
    val doorStateRearLeft: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_STATE_REAR_LEFT
    )

    /**
     * Door lock state rear right
     * false: locked
     * true: unlocked
     */
    val doorLockStateRearRight: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATE_REAR_RIGHT
    )

    /**
     * Door open state rear right
     * false: closed
     * true: open
     */
    val doorStateRearRight: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_STATE_REAR_RIGHT
    )
}
