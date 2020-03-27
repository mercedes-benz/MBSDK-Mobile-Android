package com.daimler.mbprotokit.dto.car.sunroof

enum class SunroofBlindState(val id: Int) {
    INTERMEDIATE(0),
    COMPLETELY_OPENED(1),
    COMPLETELY_CLOSED(2),
    OPENING(3),
    CLOSING(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> SunroofBlindState? = { map(it) }
    }
}
