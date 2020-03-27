package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.ClockHourUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Auxheat(
    /**
     * One out of ACTIVE, INACTIVE or UNKNOWN
     */
    var activeState: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Auxiliary heating remaining runtime:
     * 0..60 minutes
     */
    var runtime: VehicleAttribute<Int, NoUnit>,

    /**
     * Current auxiliary heating state, e.g. heating or ventilating
     */
    var state: VehicleAttribute<AuxheatState, NoUnit>,

    /**
     * Auxiliary heating pre-selection timer 1 in minutes from begin of day
     * 0..1439
     */
    var time1: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * Auxiliary heating pre-selection timer 2 in minutes from begin of day
     * 0..1439
     */
    var time2: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * Auxiliary heating pre-selection timer 3 in minutes from begin of day
     * 0..1439
     */
    var time3: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * One out of NONE, TIME1, TIME2, TIME3, UNKNOWN
     */
    var timeSelection: VehicleAttribute<AuxheatTimeSelectionState, NoUnit>,

    /**
     * Warning for e.g. tank reserve reached
     */
    var warnings: VehicleAttribute<AuxheatWarnings, NoUnit>
)
