package com.daimler.mbcarkit.business.model.vehicle.unit

@Deprecated("Use SpeedUnit or DistanceUnit instead")
enum class SpeedDistanceUnit {
    UNSPECIFIED_SPEED_DISTANCE_UNIT,
    /**
     * km/h, distance unit: km
     */
    KM_PER_H,
    /**
     * mph, distance unit: miles
     */
    M_PER_H
}
