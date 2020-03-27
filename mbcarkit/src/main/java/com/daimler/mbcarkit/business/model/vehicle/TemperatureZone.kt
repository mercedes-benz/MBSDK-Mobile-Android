package com.daimler.mbcarkit.business.model.vehicle

enum class TemperatureZone(internal val value: String) {
    UNKNOWN("unknown"),
    FRONT_LEFT("frontLeft"),
    FRONT_RIGHT("frontRight"),
    FRONT_CENTER("frontCenter"),
    REAR_LEFT("rearLeft"),
    REAR_RIGHT("rearRight"),
    REAR_CENTER("rearCenter"),
    REAR_2_LEFT("rear2left"),
    REAR_2_RIGHT("rear2right"),
    REAR_2_CENTER("rear2center")
}
