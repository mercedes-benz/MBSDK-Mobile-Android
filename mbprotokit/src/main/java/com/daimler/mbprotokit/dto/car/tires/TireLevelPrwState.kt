package com.daimler.mbprotokit.dto.car.tires

enum class TireLevelPrwState(val id: Int) {
    NO_WARNING(0),
    WARNING(1),
    GO_TO_WORKSHOP(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> TireLevelPrwState? = { map(it) }
    }
}
