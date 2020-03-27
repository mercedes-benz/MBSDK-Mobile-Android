package com.daimler.mbprotokit.dto.car.zev

enum class DepartureTimeIcon(val id: Int) {
    INACTIVE(0),
    ADHOC_ACTIVE(1),
    WEEK_PER_TM_ACTIVE(2),
    SKIP(3),
    TRIP_ACTIVE(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> DepartureTimeIcon? = { map(it) }
    }
}
