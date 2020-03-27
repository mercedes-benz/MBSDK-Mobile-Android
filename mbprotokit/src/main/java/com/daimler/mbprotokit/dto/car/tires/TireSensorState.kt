package com.daimler.mbprotokit.dto.car.tires

enum class TireSensorState(val id: Int) {
    ALL_AVAILABLE(0),
    ONE_TO_THREE_MISSING(1),
    ALL_MISSING(2),
    SYSTEM_ERROR(3),
    AUTOLOCATE_ERROR(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> TireSensorState? = { map(it) }
    }
}
