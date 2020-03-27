package com.daimler.mbprotokit.dto.car.theft

enum class ParkEventLevel(val id: Int) {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ParkEventLevel? = { map(it) }
    }
}
