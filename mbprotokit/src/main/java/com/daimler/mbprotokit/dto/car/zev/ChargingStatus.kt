package com.daimler.mbprotokit.dto.car.zev

enum class ChargingStatus(val id: Int) {
    CHARGING(0),
    CHARGING_ENDS(1),
    CHARGE_BREAK(2),
    UNPLUGGED(3),
    FAILURE(4),
    SLOW(5),
    FAST(6),
    DISCHARGING(7),
    NO_CHARGING(8),
    CHARGING_FOREIGN_OBJECT_DETECTION(9);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ChargingStatus? = { map(it) }
    }
}
