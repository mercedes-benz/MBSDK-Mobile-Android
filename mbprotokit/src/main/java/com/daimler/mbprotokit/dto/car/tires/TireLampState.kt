package com.daimler.mbprotokit.dto.car.tires

enum class TireLampState(val id: Int) {
    INACTIVE(0),
    TRIGGERED(1),
    FLASHING(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> TireLampState? = { map(it?.toInt()) }
    }
}
