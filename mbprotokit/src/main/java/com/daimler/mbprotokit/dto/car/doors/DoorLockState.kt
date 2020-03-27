package com.daimler.mbprotokit.dto.car.doors

enum class DoorLockState(val id: Int) {
    UNLOCKED(0),
    LOCKED_INTERNAL(1),
    LOCKED_EXTERNAL(2),
    UNLOCKED_SELECTIVE(3);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> DoorLockState? = { map(it) }
    }
}
