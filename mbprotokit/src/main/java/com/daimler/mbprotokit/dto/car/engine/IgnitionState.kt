package com.daimler.mbprotokit.dto.car.engine

enum class IgnitionState(val id: Int) {
    LOCK(0),
    OFF(1),
    ACCESSORY(2),
    ON(4),
    START(5);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> IgnitionState? = { map(it) }
    }
}
