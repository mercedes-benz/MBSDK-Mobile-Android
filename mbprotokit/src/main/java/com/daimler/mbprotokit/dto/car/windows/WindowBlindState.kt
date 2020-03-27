package com.daimler.mbprotokit.dto.car.windows

enum class WindowBlindState(val id: Int) {
    INTERMEDIATE(0),
    COMPLETELY_OPENED(1),
    COMPLETELY_CLOSED(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> WindowBlindState? = { map(it) }
    }
}
