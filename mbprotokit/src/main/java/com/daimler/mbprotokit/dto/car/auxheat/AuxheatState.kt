package com.daimler.mbprotokit.dto.car.auxheat

enum class AuxheatState(val id: Int) {
    INACTIVE(0),
    HEATING_NORMAL(1),
    VENTILATION_NORMAL(2),
    HEATING_MANUAL(3),
    HEATING_POST(4),
    VENTILATION_POST(5),
    HEATING_AUTO(6);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> AuxheatState? = { map(it) }
    }
}
