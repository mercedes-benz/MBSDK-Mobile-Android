package com.daimler.mbprotokit.dto.car.zev

enum class ChargingError(val id: Int) {
    NONE(0),
    CABLE(1),
    CHARGING_DISORDER(2),
    CHARGING_STATION(3),
    CHARGING_TYPE(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ChargingError? = { map(it) }
    }
}
