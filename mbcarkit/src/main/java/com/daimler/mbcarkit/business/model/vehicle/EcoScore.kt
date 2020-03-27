package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.RatioUnit

data class EcoScore(
    /**
     * Rating acceleration percentage points (0% - 100%)
     * Range: 0..100
     */
    var accel: VehicleAttribute<Int, RatioUnit>,

    /**
     * Rating bonus range in km
     * Range: 0..1638.0
     */
    var bonusRange: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Rating constancy percentage points (0% - 100%)
     * Range: 0..100
     */
    var const: VehicleAttribute<Int, RatioUnit>,

    /**
     * Rating free wheeling percentage points (0% - 100%)
     * Range: 0..100
     */
    var freeWhl: VehicleAttribute<Int, RatioUnit>,

    /**
     * Overall rating percentage points (0% - 100%)
     * Range: 0..100
     */
    var total: VehicleAttribute<Int, RatioUnit>
)
