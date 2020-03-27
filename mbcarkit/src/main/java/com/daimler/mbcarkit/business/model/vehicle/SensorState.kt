package com.daimler.mbcarkit.business.model.vehicle

enum class SensorState {
    ALL_AVAILABLE,
    ONE_TO_THREE_MISSING,
    ALL_MISSING,
    SYSTEM_ERROR,
    AUTOLOCATE_ERROR,
    UNKNOWN
}
