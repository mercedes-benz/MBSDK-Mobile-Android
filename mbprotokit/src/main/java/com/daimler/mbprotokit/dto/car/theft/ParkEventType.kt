package com.daimler.mbprotokit.dto.car.theft

enum class ParkEventType(val id: Int) {
    IDLE(0),
    FRONT_LEFT(1),
    FRONT_MIDDLE(2),
    FRONT_RIGHT(3),
    RIGHT(4),
    REAR_RIGHT(5),
    REAR_MIDDLE(6),
    REAR_LEFT(7),
    LEFT(8),
    DIRECTION_UNKNOWN(9);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ParkEventType? = { map(it) }
    }
}
