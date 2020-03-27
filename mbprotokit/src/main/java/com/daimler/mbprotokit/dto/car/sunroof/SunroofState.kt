package com.daimler.mbprotokit.dto.car.sunroof

enum class SunroofState(val id: Int) {
    CLOSED(0),
    OPENED(1),
    OPEN_LIFTING(2),
    RUNNING(3),
    ANTI_BOOMING(4),
    INTERMEDIATE_SLIDING(5),
    INTERMEDIATE_LIFTING(6),
    OPENING(7),
    CLOSING(8),
    ANTI_BOOMING_LIFTING(9),
    INTERMEDIATE_POSITION(10),
    OPENING_LIFTING(11),
    CLOSING_LIFTING(12);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> SunroofState? = { map(it) }
    }
}
