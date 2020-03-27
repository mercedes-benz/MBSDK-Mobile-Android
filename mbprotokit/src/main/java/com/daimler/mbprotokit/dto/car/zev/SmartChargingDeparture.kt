package com.daimler.mbprotokit.dto.car.zev

enum class SmartChargingDeparture(val id: Int) {
    INACTIVE(0),
    REQUESTED(1);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> SmartChargingDeparture? = { map(it) }
    }
}
