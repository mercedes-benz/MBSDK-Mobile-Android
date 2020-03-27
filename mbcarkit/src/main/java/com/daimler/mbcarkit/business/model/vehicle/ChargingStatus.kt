package com.daimler.mbcarkit.business.model.vehicle

enum class ChargingStatus {
    CHARGING,
    CHARGING_ENDS,
    CHARGE_BREAK,
    UNPLUGGED,
    FAILURE,
    SLOW,
    FAST,
    DISCHARGING,
    NO_CHARGING,
    CHARGING_FOREIGN_OBJECT_DETECTION,
    UNKNOWN
}
