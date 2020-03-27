package com.daimler.mbprotokit.dto.car.windows

enum class WindowsOverallStatus(val id: Int) {
    OPEN(0),
    CLOSED(1),
    COMPLETELY_OPEN(2),
    AIRING(3),
    RUNNING(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> WindowsOverallStatus? = { map(it) }
    }
}
