package com.daimler.mbprotokit.dto.car.zev

enum class DepartureTimeMode(val id: Int) {
    DISABLED(0),
    SINGLE(1),
    WEEKLY(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> DepartureTimeMode? = { map(it) }
    }
}
