package com.daimler.mbprotokit.dto.car.vehicledata

enum class BatteryState(val id: Int) {
    GREEN(0),
    YELLOW(1),
    RED(2),
    SERVICE_DISABLED(3),
    VEHICLE_NOT_AVAILABLE(4);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> BatteryState? = { map(it) }
    }
}
