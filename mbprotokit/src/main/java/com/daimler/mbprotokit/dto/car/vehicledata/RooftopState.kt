package com.daimler.mbprotokit.dto.car.vehicledata

enum class RooftopState(val id: Int) {
    UNLOCKED(0),
    OPEN_LOCKED(1),
    CLOSE_LOCKED(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> RooftopState? = { map(it) }
    }
}
