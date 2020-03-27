package com.daimler.mbprotokit.dto.car.auxheat

enum class AuxheatTimeSelectionState(val id: Int) {
    NONE(0),
    TIME1(1),
    TIME2(2),
    TIME3(3);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> AuxheatTimeSelectionState? = { map(it) }
    }
}
