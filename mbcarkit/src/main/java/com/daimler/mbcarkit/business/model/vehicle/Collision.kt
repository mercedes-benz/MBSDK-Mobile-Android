package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.ClockHourUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Collision(
    /**
     * UTC timestamp in milliseconds of last park Event
     * Range: 0..2^64-2
     */
    var lastParkEvent: VehicleAttribute<Long, ClockHourUnit>,

    /**
     * Describes the confirmation state of the last park (damage) event.
     */
    var lastParkEventNotConfirmed: VehicleAttribute<LastParkEventState, NoUnit>,

    /**
     * Park event level, e.g. 'low' or 'high'
     */
    var parkEventLevel: VehicleAttribute<ParkEventLevel, NoUnit>,

    /**
     * Park event direction, e.g. 'front left' or 'middle rear'
     */
    var parkEventType: VehicleAttribute<ParkEventType, NoUnit>,

    /**
     * Park event sensor status, e.g. 'active' or 'inactive'
     */
    var parkEventSensorStatus: VehicleAttribute<ActiveSelectionState, NoUnit>
)
