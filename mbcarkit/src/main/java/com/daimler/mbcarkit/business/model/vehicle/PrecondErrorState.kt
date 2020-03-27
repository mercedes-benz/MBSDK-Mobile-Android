package com.daimler.mbcarkit.business.model.vehicle

enum class PrecondErrorState {
    NO_CHANGE,
    BATTERY_OR_FUEL_LOW,
    AVAILABLE_AFTER_RESTART,
    CHARGING_NOT_FINISHED,
    GENERAL_ERROR,
    UNKNOWN
}
