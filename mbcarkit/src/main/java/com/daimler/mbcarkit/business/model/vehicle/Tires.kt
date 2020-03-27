package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Tires(
    /**
     * Tire details (pressure, warnings) for front left wheel
     */
    var frontLeft: Tire,

    /**
     * Tire details (pressure, warnings) for front right wheel
     */
    var frontRight: Tire,

    /**
     * Tire details (pressure, warnings) for rear left wheel
     */
    var rearLeft: Tire,

    /**
     * Tire details (pressure, warnings) for rear right wheel
     */
    var rearRight: Tire,

    /**
     * Latest pressure measurement UTC timestamp in seconds
     */
    var pressureMeasurementTimestampInSeconds: VehicleAttribute<Long, NoUnit>,

    /**
     * Health indicator of all tire sensors, e.g. 'all available' or 'one to three missing'
     */
    var sensorAvailable: VehicleAttribute<SensorState, NoUnit>,

    /**
     * Overall tire pressure warning state, e.g. 'no warning' or 'low pressure'
     */
    var warningLevelOverall: VehicleAttribute<TireSrdkState, NoUnit>
)
