package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Theft(
    /**
     * Status of interior protection sensor, e.g. 'active' or 'inactive'
     */
    var interiorProtectionStatus: VehicleAttribute<ActiveSelectionState, NoUnit>,

    /**
     * Current alarm state, one out of 'active' or 'inactive'
     */
    var alarmActive: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Status of overall alarm sensors, one out of 'armed' or 'not armed'
     */
    var systemArmed: VehicleAttribute<ArmedState, NoUnit>,

    /**
     * Status of tow protection sensor, e.g. 'active' or 'inactive'
     */
    var towProtectionSensorStatus: VehicleAttribute<ActiveSelectionState, NoUnit>,

    /**
     * Indicator, where the current theft warning has been triggered, e.g. 'door front right' or 'glovebox'
     */
    var lastTheftWarningReason: VehicleAttribute<TheftWarningReasonState, NoUnit>,

    /**
     * UTC timestamp of last theft alarm
     * Range: 0..2^64-2
     */
    var lastTheftWarning: VehicleAttribute<Long, NoUnit>,

    /**
     * Indicates whether the vehicles keys are disabled or enabled.
     */
    var keyActivationState: VehicleAttribute<KeyActivationState, NoUnit>,

    /**
     * Last collision details
     */
    var collision: Collision
)
