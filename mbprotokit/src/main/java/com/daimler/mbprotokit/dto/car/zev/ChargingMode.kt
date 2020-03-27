package com.daimler.mbprotokit.dto.car.zev

enum class ChargingMode(val id: Int) {
    NONE(0),
    COUNDUCTIVE_AC(1),
    INDUCTIVE(2),
    CONDUCTIVE_AC_INDUCTIVE(3),
    CONDUCTIVE_DC(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ChargingMode? = { map(it) }
    }
}
