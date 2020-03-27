package com.daimler.mbprotokit.dto.car.zev

enum class HybridWarningState(val id: Int) {
    NONE(0),
    SEEK_SERVICE_WITHOUT_ENGINGE_STOP(1),
    HIGH_VOLTAGE_POWERNET_FAULT(2),
    POWER_TRAIN_FAULT(3),
    STARTER_BATTERY(4),
    STOP_VEHICLE_CHARGE_BATTERY(5),
    PLUGIN_ONLY_EMODE_POSSIBLE(6),
    PLUGIN_VEHICLE_STILL_ACTIVE(7),
    POWER_REDUCE(8),
    STOP_ENGINE_OFF(9);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> HybridWarningState? = { map(it) }
    }
}
