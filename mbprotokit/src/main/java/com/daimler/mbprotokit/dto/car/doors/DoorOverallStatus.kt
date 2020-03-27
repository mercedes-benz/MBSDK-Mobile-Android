package com.daimler.mbprotokit.dto.car.doors

enum class DoorOverallStatus(val id: Int) {
    OPEN(0),
    CLOSED(1),
    NOT_EXISTING(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> DoorOverallStatus? = { map(it) }
    }
}
