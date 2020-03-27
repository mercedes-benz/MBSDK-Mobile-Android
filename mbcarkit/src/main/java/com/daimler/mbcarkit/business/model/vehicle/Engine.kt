package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.TemperatureUnit

data class Engine(
    /**
     * State of ignition, e.g. 'off', 'locked' or 'on'
     */
    var ignitionState: VehicleAttribute<IgnitionState, NoUnit>,

    /**
     * State of engine, e.g. 'running' or 'not running'
     */
    var engineRunningState: VehicleAttribute<RunningState, NoUnit>,

    /**
     * State of remote start
     */
    var remoteStartActiveState: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Remote Start inside Temperature (steps in 0.5°C)
     * Range: -40.0..85.0
     */
    var remoteStartTemperature: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Remote Start End Time in seconds since 1.1.1970 00:00:00
     * Range: 0..2^64-2
     */
    var remoteStartEndtime: VehicleAttribute<Long, NoUnit>
)
