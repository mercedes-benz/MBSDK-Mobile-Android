package com.daimler.mbprotokit.dto.car

enum class Day(val id: Int) {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    companion object {
        fun map(id: Int?) = values().find {
            it.id == id
        }

        fun map(): (Int?) -> Day? = { map(it) }
    }
}
