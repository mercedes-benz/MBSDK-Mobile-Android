package com.daimler.mbprotokit.dto.car.zev

enum class ChargingLimitation(val id: Int) {
    LIMIT_6A(0),
    LIMIT_8A(1),
    NO_LIMIT(6);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ChargingLimitation? = { map(it) }
    }
}
