package com.daimler.mbprotokit.dto.car.tires

enum class TireMarkerState(val id: Int) {
    NO_WARNING(0),
    SOFT_WARNING(1),
    LOW_PRESSURE(2),
    DEFLATION(3),
    MARK(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> TireMarkerState? = { map(it) }
    }
}
