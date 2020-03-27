package com.daimler.mbprotokit.dto.car.vehicledata

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.dto.car.doors.DoorLockOverallStatus
import com.daimler.mbprotokit.dto.car.doors.DoorLockState
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class VehicleData(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {

    /**
     * Current state of the starter battery, e.g. 'red' or 'green'
     */
    val batteryState: Pair<BatteryState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.STARTER_BATTERY_STATE,
        BatteryState.map()
    )

    /**
     * Connection status of the vehicle
     * false: Disconnected
     * true: Connected
     */
    val vehicleDataConnectionState: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.VEHICLE_DATA_CONNECTION_STATE
    )

    /**
     * Open status of the engine hood
     * false: Engine hood closed
     * true: Engine hood open
     */
    val engineHoodStatus: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ENGINE_HOOD_STATUS
    )

    /**
     * Filter particle state
     * 0.. Air quality high
     * 1.. Air quality medium
     * 2.. Air quality low
     */
    val filterParticleState: Pair<FilterParticleState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.FILTER_PARTICLE_LOADING,
        FilterParticleState.map()
    )

    /**
     * Odometer value in km
     * Range: 0..999999
     */
    val odo: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ODO
    )

    /**
     * Lock status for gas
     * false: locked
     * true: unlocked
     */
    val doorLockStateGas: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATUS_GAS
    )

    /**
     * Convertible top opened / closed
     * 0: "Unlocked"
     * 1: "Open and locked"
     * 2: "Closed and locked"
     */
    val rooftopState: Pair<RooftopState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ROOFTOP_STATUS,
        RooftopState.map()
    )

    /**
     * Residual maintenance interval time in days
     * Range: -1998 .. 1998
     */
    val serviceIntervalDays: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SERVICE_INTERVAL_DAYS
    )

    /**
     * Residual maintenance interval distance in given distance unit
     * Range: -199999 .. 199999
     */
    val serviceIntervalDistance: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SERVICE_INTERVAL_DISTANCE
    )

    /**
     * Door lock state of all doors combined
     * 0: Vehicle unlocked
     * 1: Vehicle internal locked
     * 2: Vehicle external locked
     * 3: Vehicle selective unlocked
     */
    val lockStateOverall: Pair<DoorLockOverallStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATUS_OVERALL,
        DoorLockOverallStatus.map()
    )

    /**
     * Displayed battery State of charge in percent (High voltage battery)
     * Range: 0..100
     */
    val soc: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SOC
    )

    /**
     * Vehicle HeadUnit in s since 1.1.1970 00:00:00
     * Range: 0..2^64-2
     */
    val vTime: Pair<Long?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.V_TIME
    )
    val speedAlertConfiguration: Pair<List<SpeedAlertConfiguration>?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SPEED_ALERT_CONFIGURATION,
        SpeedAlertConfiguration.mapToSpeedAlertConfigurations()
    )

    /**
     * Speed Unit in HeadUnit
     * false: Vehicle speed unit "km/h" (kilometers per hour) and distance unit "km" (kilometers)
     * true: Vehicle speed unit "mph" (miles per hour) and distance unit "mi" (miles)
     */
    val speedUnitFromIc: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SPEED_UNIT_FROM_IC
    )

    /**
     * Parking brake status
     * false: "park brake not engaged"
     * true: "park brake engaged"
     */
    val parkBrakeState: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PARK_BRAKE_STATUS
    )

    /**
     * Overall door lock state for all doors combined
     * 0: Vehicle unlocked
     * 1: Vehicle internal locked
     * 2: Vehicle external locked
     * 3: Vehicle selective unlocked
     */
    val vehicleLockState: Pair<DoorLockState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATUS_VEHICLE,
        DoorLockState.map()
    )
}
