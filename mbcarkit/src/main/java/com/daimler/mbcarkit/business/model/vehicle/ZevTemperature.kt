package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.TemperatureUnit

data class ZevTemperature(
    /**
     * Temperature front center in given temperature unit
     * Range: 0..30
     */
    var frontCenter: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature front left in given temperature unit
     * Range: 0..30
     */
    var frontLeft: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature front right in given temperature unit
     * Range: 0..30
     */
    var frontRight: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature rear center (1) in given temperature unit
     * Range: 0..30
     */
    var rearCenter: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature rear center (2) in given temperature unit
     * Range: 0..30
     */
    var rearCenter2: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature rear left in given temperature unit
     * Range: 0..30
     */
    var rearLeft: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature rear right in given temperature unit
     * Range: 0..30
     */
    var rearRight: VehicleAttribute<Double, TemperatureUnit>
)
