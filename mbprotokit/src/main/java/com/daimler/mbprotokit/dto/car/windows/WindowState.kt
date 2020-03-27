package com.daimler.mbprotokit.dto.car.windows

enum class WindowState(val id: Int) {
    INTERMEDIATE(0),
    OPENED(1),
    CLOSED(2),
    AIRING_POSITION(3),
    AIRING_INTERMEDIATE(4),
    RUNNING(5);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> WindowState? = { map(it) }
    }
}
