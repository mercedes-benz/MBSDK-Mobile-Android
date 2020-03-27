package com.daimler.mbprotokit.dto.car.doors

enum class DoorLockOverallStatus(val id: Int) {
    LOCKED(0),
    UNLOCKED(1),
    NOT_EXISTING(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> DoorLockOverallStatus? = { map(it) }
    }
}
