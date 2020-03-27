package com.daimler.mbprotokit.dto.car.zev

enum class CouplerStatus(val id: Int) {
    WIRE_PLUGGED_BOTH_SIDES(0),
    WIRE_PLUGGED_VEHICLE_SIDE(1),
    WIRE_NOT_PLUGGED_VEHICLE_SIDE(2),
    PLUGGED_STATE_UNKNOWN(3),
    PLUGGED_STATE_UNKNOWN_DUE_DEFECT(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> CouplerStatus? = { map(it) }
    }
}
