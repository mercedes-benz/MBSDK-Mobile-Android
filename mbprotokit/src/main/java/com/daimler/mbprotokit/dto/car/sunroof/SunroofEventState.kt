package com.daimler.mbprotokit.dto.car.sunroof

enum class SunroofEventState(val id: Int) {
    NONE(0),
    RAIN_LIFT_POSITION(1),
    AUTOMATIC_LIFT_POSITION(2),
    VENTILATION_POSITION(3);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> SunroofEventState? = { map(it) }
    }
}
