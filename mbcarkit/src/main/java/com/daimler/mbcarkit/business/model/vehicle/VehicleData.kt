package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.RatioUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.SpeedUnit

data class VehicleData(
    /**
     * Current state of the starter battery, e.g. 'red' or 'green'
     */
    var starterBatteryState: VehicleAttribute<BatteryState, NoUnit>,

    /**
     * Connection status of the vehicle. Only for Fastpath vehicles.
     * False..Disconnected
     * True..Connected
     */
    var dataConnectionState: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Open status of the engine hood, e.g. 'open' or 'closed'
     */
    var engineHoodStatus: VehicleAttribute<OpenStatus, NoUnit>,

    /**
     * Air quality state, e.g. 'quality high' or 'quality medium'
     */
    var filterParticleState: VehicleAttribute<FilterParticleState, NoUnit>,

    /**
     * Odometer, value in km
     * Range: 0..999999
     */
    var odo: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Lock status for gas, e.g. 'locked' or 'unlocked'
     */
    var lockGasState: VehicleAttribute<LockStatus, NoUnit>,

    /**
     * Lock status for doors, e.g. 'vehicle selectively unlocked' or 'vehicle internal locked'
     */
    var doorLockState: VehicleAttribute<DoorLockState, NoUnit>,

    /**
     * Parking brake status, e.g. 'brake engaged'
     */
    var parkBrakeStatus: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Lock status for rooftop, e.g. 'open but locked'
     */
    var rooftopState: VehicleAttribute<RooftopState, NoUnit>,

    /**
     * Residual maintenance interval days
     * Range: -1998..1998
     */
    var serviceIntervalDays: VehicleAttribute<Int, NoUnit>,

    /**
     * Residual maintenance interval distance in given distance unit
     * Range: -199999..199999
     */
    var serviceIntervalDistance: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Displayed battery state of charge (HV battery) in percent
     * Range: 0..100
     */
    var soc: VehicleAttribute<Int, RatioUnit>,

    /**
     *
     */
    var speedAlertConfiguration: VehicleAttribute<List<SpeedAlertConfiguration>, SpeedUnit>,

    /**
     * Vehicle speed unit, e.g. 'km/h' or 'mph'
     */
    var speedUnitFromIC: VehicleAttribute<SpeedUnitType, NoUnit>,

    /**
     * Vehicle time as timestamp in seconds since 1.1.1970 00:00:00
     */
    var time: VehicleAttribute<Int, NoUnit>
) {
    internal fun getAllAttributes() =
        listOf<VehicleAttribute<*, *>>(
            starterBatteryState,
            dataConnectionState,
            engineHoodStatus,
            filterParticleState,
            odo,
            lockGasState,
            doorLockState,
            parkBrakeStatus,
            rooftopState,
            serviceIntervalDays,
            serviceIntervalDistance,
            soc,
            speedAlertConfiguration,
            speedUnitFromIC,
            time
        )
}
