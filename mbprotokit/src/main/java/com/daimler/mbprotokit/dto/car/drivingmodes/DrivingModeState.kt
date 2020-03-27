package com.daimler.mbprotokit.dto.car.drivingmodes

enum class DrivingModeState(val id: Int) {
    UNKNOWN(0),
    OFF(1),
    ON(2),
    PENDING_OFF(3),
    PENDING_ON(4),
    ERROR(5);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> DrivingModeState? = { map(it) }
    }
}
