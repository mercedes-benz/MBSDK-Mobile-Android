package com.daimler.mbprotokit.dto.car.zev

enum class CouplerLockStatus(val id: Int) {
    CONNECTOR_LOCKED(0),
    CONNECTOR_UNLOCKED(1),
    NOT_CLEAR(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> CouplerLockStatus? = { map(it) }
    }
}
