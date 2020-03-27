package com.daimler.mbprotokit.dto.car.zev

enum class ChargeFlapStatus(val id: Int) {
    OPEN(0),
    CLOSED(1),
    FLAP_PRESSED(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ChargeFlapStatus? = { map(it) }
    }
}
