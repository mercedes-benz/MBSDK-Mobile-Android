package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.RatioUnit

data class Tank(
    /**
     * Tanklevel Ad Blue in percent
     * Range: 0..100
     */
    var adBlueLevel: VehicleAttribute<Int, RatioUnit>,

    /**
     * Electric Level in percent
     * Ranke: 0..100
     */
    var electricLevel: VehicleAttribute<Int, RatioUnit>,

    /**
     * Remaining range electric engine in given distance unit
     * Range: 0..4000
     */
    var electricRange: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Gas tanklevel in given unit. For LPG and hydrogen vehicles.
     * Range: 0..204.6
     */
    var gasLevel: VehicleAttribute<Double, RatioUnit>,

    /**
     * Gas tankrange in given distance unit. For LPG and hydrogen vehicles.
     * Range: 0..2046
     */
    var gasRange: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Tanklevel in given unit. For combustion vehicles.
     * Range: 0..100
     */
    var liquidLevel: VehicleAttribute<Int, RatioUnit>,

    /**
     * Remaining milage in given distance unit
     * Range: 0..4000
     */
    var liquidRange: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Overall range combined over all fuel types.
     */
    var overallRange: VehicleAttribute<Double, DistanceUnit>
)
