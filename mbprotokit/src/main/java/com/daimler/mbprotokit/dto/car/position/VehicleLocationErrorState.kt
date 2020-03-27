package com.daimler.mbprotokit.dto.car.position

enum class VehicleLocationErrorState(val id: Int) {
    SERVICE_INACTIVE(1),
    TRACKING_INACTIVE(2),
    PARKED(3),
    IGNITION_ON(4),
    OK(5);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> VehicleLocationErrorState? = { map(it) }
    }
}
