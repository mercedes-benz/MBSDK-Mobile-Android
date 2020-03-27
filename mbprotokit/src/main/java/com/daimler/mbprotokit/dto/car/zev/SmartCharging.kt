package com.daimler.mbprotokit.dto.car.zev

enum class SmartCharging(val id: Int) {
    WALLBOX(0),
    SMART_CHARGE(1),
    PEAK_SETTING(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> SmartCharging? = { map(it) }
    }
}
