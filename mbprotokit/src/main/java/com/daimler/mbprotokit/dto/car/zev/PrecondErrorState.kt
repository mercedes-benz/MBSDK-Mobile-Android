package com.daimler.mbprotokit.dto.car.zev

enum class PrecondErrorState(val id: Int) {
    NO_CHANGE(0),
    BATTERY_OR_FUEL_LOW(1),
    AVAILABLE_AFTER_RESTART(2),
    CHARGING_NOT_FINISHED(3),
    GENERAL_ERROR(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> PrecondErrorState? = { map(it) }
    }
}
