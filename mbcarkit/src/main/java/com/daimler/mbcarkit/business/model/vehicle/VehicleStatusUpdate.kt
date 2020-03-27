package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.ClockHourUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.CombustionConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.ElectricityConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.GasConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.PressureUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.RatioUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.SpeedUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.TemperatureUnit

data class VehicleStatusUpdate(

    /**
     * if set to true all other attributes should be reset.
     */
    val fullUpdate: Boolean,

    /**
     * One out of ACTIVE, INACTIVE or UNKNOWN
     */
    val auxheatActiveState: VehicleAttribute<Int, NoUnit>,

    /**
     * Auxiliary heating remaining runtime:
     * 0..60 minutes
     */
    val auxHeatRuntime: VehicleAttribute<Int, NoUnit>,

    /**
     * Current auxiliary heating state, e.g. heating or ventilating
     */
    val auxheatState: VehicleAttribute<Int, NoUnit>,

    /**
     * Auxiliary heating pre-selection timer 1 in minutes from begin of day
     * 0..1439
     */
    val auxheatTime1: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * Auxiliary heating pre-selection timer 2 in minutes from begin of day
     * 0..1439
     */
    val auxheatTime2: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * Auxiliary heating pre-selection timer 3 in minutes from begin of day
     * 0..1439
     */
    val auxheatTime3: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * One out of NONE, TIME1, TIME2, TIME3, UNKNOWN
     */
    val auxheatTimeSelection: VehicleAttribute<Int, NoUnit>,

    /**
     * Warning for e.g. tank reserve reached
     */
    val auxheatWarnings: VehicleAttribute<Int, NoUnit>,

    /**
     * Average speed since reset in given speed unit
     * Range: 0..405.0
     */
    val averageSpeedReset: VehicleAttribute<Double, SpeedUnit>,

    /**
     * Average speed since start in given speed unit
     * Range: 0..405.0
     */
    val averageSpeedStart: VehicleAttribute<Double, SpeedUnit>,

    /**
     * Current state of the starter battery, e.g. 'red' or 'green'
     */
    val batteryState: VehicleAttribute<Int, NoUnit>,

    /**
     * Deck lid latch status:
     * 0: locked
     * 1: unlocked
     */
    val decklidLockState: VehicleAttribute<Int, NoUnit>,

    val clientMessageData: VehicleAttribute<Double, NoUnit>, // Data in model from christoph --> not defined

    /**
     * 0: closed
     * 1: open
     */
    val decklidState: VehicleAttribute<Int, NoUnit>,

    /**
     * Distance since reset electrical in given distance unit
     * Range: 0..999999.9
     */
    val distanceElectricalReset: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Distance since start electrical in given distance unit
     * Range: 0..999999.9
     */
    val distanceElectricalStart: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Distance since reset GAS in given distance unit (LPG and H2 vehicles)
     * Range: 0..999999.9
     */
    val distanceGasReset: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Distance since start GAS in given distance unit (LPG and H2 vehicles)
     * Range: 0..999999.9
     */
    val distanceGasStart: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Distance since reset liquid in given distance unit (combustion vehicles)
     * Range: 0..999999.9
     */
    val distanceReset: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Distance since start liquid in given distance unit (combustion vehicles)
     * Range: 0..999999.9
     */
    val distanceStart: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Distance since reset zero emission in given distance unit (pure electric vehicles)
     * Range: 0..999999.9
     */
    val distanceZeReset: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Distance since start zero emission in given distance unit (pure electric vehicles)
     * Range: 0..999999.9
     */
    val distanceZeStart: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Door lock state front left
     * 0: locked
     * 1: unlocked
     */
    val doorLockStateFrontLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Door open state front left
     * 0: closed
     * 1: open
     */
    val doorStateFrontLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Door lock state front right
     * 0: locked
     * 1: unlocked
     */
    val doorLockStateFrontRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Door open state front right
     * 0: closed
     * 1: open
     */
    val doorStateFrontRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Lock status for gas
     * 0: locked
     * 1: unlocked
     */
    val doorLockStateGas: VehicleAttribute<Int, NoUnit>,

    /**
     * Door lock state rear left
     * 0: locked
     * 1: unlocked
     */
    val doorLockStateRearLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Door open state rear left
     * 0: closed
     * 1: open
     */
    val doorStateRearLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Door lock state rear right
     * 0: locked
     * 1: unlocked
     */
    val doorLockStateRearRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Door open state rear right
     * 0: closed
     * 1: open
     */
    val doorStateRearRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Driven Time since reset in minutes
     * Range: 0..600000
     */
    val drivenTimeReset: VehicleAttribute<Int, NoUnit>,

    /**
     * Driven Time since start in minutes
     * Range: 0..600000
     */
    val drivenTimeStart: VehicleAttribute<Int, NoUnit>,

    /**
     * Driven Time Zero Emission since reset in minutes
     * Range: 0..600000
     */
    val drivenTimeZEReset: VehicleAttribute<Int, NoUnit>,

    /**
     * Driven Time Zero Emission since start in minutes
     * Range: 0..600000
     */
    val drivenTimeZEStart: VehicleAttribute<Int, NoUnit>,

    /**
     * ECOscore rating acceleration percentage points
     * Range: 0..100
     */
    val ecoScoreAccel: VehicleAttribute<Int, RatioUnit>,

    /**
     * ECOscore rating bonus range in km
     * Range: 0..1638.0
     */
    val ecoScoreBonusRange: VehicleAttribute<Double, NoUnit>,

    /**
     * ECOscore rating constancy percentage points
     * Range: 0..100
     */
    val ecoScoreConst: VehicleAttribute<Int, RatioUnit>,

    /**
     * ECOscore rating free wheeling percentage points
     * Range: 0..100
     */
    val ecoScoreFreeWhl: VehicleAttribute<Int, RatioUnit>,

    /**
     * ECOscore overall rating percentage points
     * Range: 0..100
     */
    val ecoScoreTotal: VehicleAttribute<Int, RatioUnit>,

    /**
     * Open status of the engine hood
     * 0: Engine hood closed
     * 1: Engine hood open
     */
    val engineHoodStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Electric consumption from reset of trip
     * Range: 0..655.3
     */
    val electricConsumptionReset: VehicleAttribute<Double, ElectricityConsumptionUnit>,

    /**
     * Electric consumption from start of trip
     * Range: 0..655.3
     */
    val electricConsumptionStart: VehicleAttribute<Double, ElectricityConsumptionUnit>,

    /**
     *
     */
    val electricalRangeSkipIndication: VehicleAttribute<Int, NoUnit>,

    /**
     * Engine running state
     * 0.. Engine not running
     * 1.. Engine running
     */
    val engineState: VehicleAttribute<Int, NoUnit>,

    /**
     *
     */
    val eventTimeStamp: Long,

    /**
     * Filter particle state
     * 0.. Air quality high
     * 1.. Air quality medium
     * 2.. Air quality low
     */
    val filterParticelState: VehicleAttribute<Int, NoUnit>,

    /**
     * Gaseous fuel consumption from reset in given consumption unit
     * Range: 0..100.
     */
    val gasConsumptionReset: VehicleAttribute<Double, GasConsumptionUnit>,

    /**
     * Gaseous fuel consumption from start in given consumption unit
     * Range: 0..100.0
     */
    val gasConsumptionStart: VehicleAttribute<Double, GasConsumptionUnit>,

    /**
     *
     */
    val healthStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Ignition switch state
     * 0: "ignition lock (0)"
     * 1: "ignition off (15c)"
     * 2: "ignition accessory (15r)"
     * 4: "ignition on (15)"
     * 5: "ignition start (50)"
     */
    val ignitionState: VehicleAttribute<Int, NoUnit>,

    /**
     * State of interior protecton sensors
     * 0: not active, but selected
     * 1: not active, not selected
     * 2: Active
     */
    val interiorProtectionStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Activation state of all vehicle keys
     * 0: all vehicle keys are deactivated
     * 1: all vehicle keys are activated
     */
    val keyActivationState: VehicleAttribute<Int, NoUnit>,

    /**
     * Selected HeadUnit language
     * Value: See [LanguageState]
     */
    val languageHu: VehicleAttribute<Int, NoUnit>,

    /**
     * Last parking collision event as UTC timestamp
     * Range: 0..2^64-2
     */
    val lastParkEvent: VehicleAttribute<Long, ClockHourUnit>,

    /**
     * Request HU to show park (damage) event.
     */
    val lastParkEventNotConfirmed: VehicleAttribute<Int, NoUnit>,

    /**
     * Park event sensor status, e.g. 'active' or 'inactive'
     */
    var parkEventSensorStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Liquid fuel consumption from rest in given consumption unit
     * Range: 0..100.0
     */
    val liquidConsumptionReset: VehicleAttribute<Double, CombustionConsumptionUnit>,

    /**
     * Liquid fuel consumption from start in given consumption unit
     * Range: 0..100.0
     */
    val liquidConsumptionStart: VehicleAttribute<Double, CombustionConsumptionUnit>,

    /**
     *
     */
    val liquidRangeSkipIndication: VehicleAttribute<Int, NoUnit>,

    /**
     * Door lock state of all doors combined
     * 0: Vehicle unlocked
     * 1: Vehicle internal locked
     * 2: Vehicle external locked
     * 3: Vehicle selective unlocked
     */
    val lockStateOverall: VehicleAttribute<Int, NoUnit>,

    /**
     * Odometer value in km
     * Range: 0..999999
     */
    val odo: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Overall range combined over all fuel types.
     */
    val overallRange: VehicleAttribute<Double, DistanceUnit>,

    /**
     * Parking brake status
     * 0: "park brake not engaged"
     * 1: "park brake engaged"
     */
    val parkBrakeState: VehicleAttribute<Int, NoUnit>,

    /**
     * Parking collision level
     * 0.. low
     * 1.. medium
     * 2.. high
     */
    val parkEventLevel: VehicleAttribute<Int, NoUnit>,

    /**
     * Parking collision direction
     * 0: idle
     * 1: front left
     * 2: front middle
     * 3: front right
     * 4: right
     * 5: rear right
     * 6: rear middle
     * 7: rear left
     * 8: left
     * 9: direction unknown
     */
    val parkEventType: VehicleAttribute<Int, NoUnit>,

    /**
     * Direction of vehicle in degrees
     * Range: 0..359.9
     */
    val positionHeading: VehicleAttribute<Double, NoUnit>,

    /**
     * Latitude in WGS84 coordinates
     * Range: -90.000000..90.000000
     */
    val positionLat: VehicleAttribute<Double, NoUnit>,

    /**
     * Longitude in WGS84 coordinates
     * Range: -180.000000..180.000000
     */
    val positionLong: VehicleAttribute<Double, NoUnit>,

    /**
     * Vehicle position error code.
     * 0: position not available
     * 1: service inactive
     * 2: location tracking disabled
     * 3: vehicle is not moving
     * 4: vehicle is moving
     */
    val positionErrorCode: VehicleAttribute<Int, NoUnit>,

    /**
     * True if proximity calculation is required for vehicle location.
     *
     * 0: false
     * 1: true
     */
    val proximityCalculationRequiredForVehicleLocation: VehicleAttribute<Int, NoUnit>,

    /**
     * Remote Start Active
     * 0.. Remote Start not active
     * 1.. Remote Start active
     */
    val remoteStartActive: VehicleAttribute<Int, NoUnit>,

    /**
     * Remote Start inside Temperature
     * Step 0.5 °C
     * Range: -40.0..85.0
     */
    val remoteStartTemperature: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Remote Start End Time in s since 1.1.1970 00:00:00
     * Range: 0..2^64-2
     */
    val remoteStartEndtime: VehicleAttribute<Long, NoUnit>,

    /**
     * Convertible top opened / closed
     * 0: "Unlocked"
     * 1: "Open and locked"
     * 2: "Closed and locked"
     */
    val rooftopState: VehicleAttribute<Int, NoUnit>,

    /**
     * Residual maintenance interval time in days
     * Range: -1998 .. 1998
     */
    val serviceIntervalDays: VehicleAttribute<Int, NoUnit>,

    /**
     * Residual maintenance interval distance in given distance unit
     * Range: -199999 .. 199999
     */
    val serviceIntervalDistance: VehicleAttribute<Int, DistanceUnit>,

    /**
     *
     */
    val sequenceNumber: Int,

    /**
     * Displayed battery State of charge in percent (High voltage battery)
     * Range: 0..100
     */
    val soc: VehicleAttribute<Int, RatioUnit>,

    /**
     *
     */
    val speedAlertConfiguration: VehicleAttribute<List<SpeedAlertConfiguration>, SpeedUnit>,

    /**
     * Speed Unit in HeadUnit
     * 0: Vehicle speed unit "km/h" (kilometers per hour) and distance unit "km" (kilometers)
     * 1: Vehicle speed unit "mph" (miles per hour) and distance unit "mi" (miles)
     */
    val speedUnitFromIc: VehicleAttribute<Int, NoUnit>,

    /**
     *
     */
    val stateOverall: VehicleAttribute<Int, NoUnit>,

    /**
     * Event state rain close
     * 0: No event
     * 1: Lift position because of rain
     * 2: Lift position automatic
     * 3: Lift position because of timer
     */
    val sunroofEventState: VehicleAttribute<Int, NoUnit>,

    /**
     * Rainclose monitoring active
     * 0: not active
     * 1: active
     */
    val sunroofEventActive: VehicleAttribute<Int, NoUnit>,

    /**
     * Sunroof state
     * 0:  "Tilt/slide sunroof is closed"
     * 1:  "Tilt/slide sunroof is complete open"
     * 2:  "Lifting roof is open"
     * 3:  "Tilt/slide sunroof is running" (only available if no differentiation between opening/closing possible)
     * 4:  "Tilt/slide sunroof in anti-booming position"
     * 5:  "Sliding roof in intermediate position"
     * 6:  "Lifting roof in intermediate position"
     * 7:  "Sunroof is opening"
     * 8:  "Sunroof is closing"
     * 9:  "Lifting anti booming"
     * 10: "Sunroof in intermediate position"
     * 11: "Lifting roof is opening"
     * 12: "Lifting roof is closing"
     */
    val sunroofState: VehicleAttribute<Int, NoUnit>,

    /**
     * Front roller blind state sunroof
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     * 3: opening
     * 4: closing
     */
    val sunroofStatusFrontBlind: VehicleAttribute<Int, NoUnit>,

    /**
     * Rear roller blind state sunroof
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     * 3: opening
     * 4: closing
     */
    val sunroofStatusRearBlind: VehicleAttribute<Int, NoUnit>,

    /**
     * Tanklevel Ad Blue in percent
     * Range 0..100
     */
    val tankAdBlueLevel: VehicleAttribute<Int, RatioUnit>,

    /**
     * Displayed battery State of charge in percent (High voltage battery)
     * Range: 0..100
     */
    val tankElectricalLevel: VehicleAttribute<Int, RatioUnit>,

    /**
     * Remaining range electric engine in given distance unit
     * Range: 0..4000
     */
    val tankElectricalRange: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Gas tanklevel in liters (only for LPG vehicles)
     * Range: 0..204.6
     */
    val tankGasLevel: VehicleAttribute<Double, RatioUnit>,

    /**
     * Gas tankrange in given distance unit. For LPG and hydrogen vehicles.
     * Range: 2046
     */
    val tankGasRange: VehicleAttribute<Double, DistanceUnit>,

    /**
     *
     */
    val tankLiquidLevel: VehicleAttribute<Int, RatioUnit>,

    /**
     * Remaining liquid fuel range in given distance unit
     * Range: 0..2046
     */
    val tankLiquidRange: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Temperature unit in HeadUnit
     * 0: Celsius
     * 1: Fahrenheit
     */
    val temperatureUnitHu: VehicleAttribute<Int, NoUnit>,

    /**
     * Theft alarm active (ringing)
     * 0: Not active
     * 1: Active
     */
    val theftAlarmActive: VehicleAttribute<Int, NoUnit>,

    /**
     * Theft alarm system monitoring
     * 0: theft system not armed
     * 1: theft system armed
     */
    val theftSystemArmed: VehicleAttribute<Int, NoUnit>,

    /**
     * Time format in HeadUnit
     * 0: 12h
     * 1: 24h
     */
    val timeFormatHu: VehicleAttribute<Int, NoUnit>,

    /**
     * Tire level Plattrollwarner warning
     * 0: no warning
     * 1: warning
     * 2: Visit workshop
     */
    val tireLevelPrw: VehicleAttribute<Int, NoUnit>,

    /**
     * Tire pressure front left in given pressure unit
     * Range: 0..632.5
     */
    val tirePressureFrontLeft: VehicleAttribute<Double, PressureUnit>,

    /**
     * Tire pressure front right in given pressure unit
     * Range: 0..632.5
     */
    val tirePressureFrontRight: VehicleAttribute<Double, PressureUnit>,

    /**
     * Tire pressure rear left in given pressure unit
     * Range: 0..632.5
     */
    val tirePressureRearLeft: VehicleAttribute<Double, PressureUnit>,

    /**
     * Tire pressure rear right in given pressure unit
     * Range: 0..632.5
     */
    val tirePressureRearRight: VehicleAttribute<Double, PressureUnit>,

    /**
     * Tire marker front left
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     * 4: "mark" (unknown kind of warning but there is one)
     */
    val tireMarkerFrontLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Tire marker front right
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     * 4: "mark" (unknown kind of warning but there is one)
     */
    val tireMarkerFrontRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Tire marker rear left
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     * 4: "mark" (unknown kind of warning but there is one)
     */
    val tireMarkerRearLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Tire marker rear right
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     * 4: "mark" (unknown kind of warning but there is one)
     */
    val tireMarkerRearRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Latest pressure measurement UTC timestamp in seconds
     */
    val tirePressureMeasurementTimestamp: VehicleAttribute<Long, NoUnit>,

    /**
     * Tire sensor availability:
     * 0: All sensors located
     * 1: 1 to 3 sensors are missing
     * 2: All sensors missing
     * 3: System Error
     */
    val tireSensorAvailable: VehicleAttribute<Int, NoUnit>,

    /**
     * Tow protection sensor status
     * 0: not active, but selected
     * 1: not active, not selected
     * 2: Active
     */
    val towProtectionSensorStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Vehicle tracking on/off in HU
     * 0: Off
     * 1: On
     */
    val trackingStateHu: VehicleAttribute<Int, NoUnit>,

    /**
     * Connection status of the vehicle
     * False: Disconnected
     * True: Connected
     */
    val vehicleDataConnectionState: VehicleAttribute<Int, NoUnit>,

    /**
     * Overall door lock state for all doors combined
     * 0: Vehicle unlocked
     * 1: Vehicle internal locked
     * 2: Vehicle external locked
     * 3: Vehicle selective unlocked
     */
    val vehicleLockState: VehicleAttribute<Int, NoUnit>,

    /**
     * Unique vehicle identification number
     */
    val finOrVin: String,

    /**
     * Vehicle HeadUnit in s since 1.1.1970 00:00:00
     * Range: 0..2^64-2
     */
    val vTime: VehicleAttribute<Int, NoUnit>,

    /**
     * Brake fluid warning
     * 0: "brake fluid warning inactive"
     * 1: "brake fluid warning triggered"
     */
    val warningBrakeFluid: VehicleAttribute<Int, NoUnit>,

    /**
     * Brake lining wear warning
     * 0: "brake lining warning inactive"
     * 1: "brake lining warning triggered"
     */
    val warningBrakeLiningWear: VehicleAttribute<Int, NoUnit>,

    /**
     * Coolant level low warning
     * 0: "coolant level warning inactive"
     * 1: "coolant level warning triggered"
     */
    val warningCoolantLevelLow: VehicleAttribute<Int, NoUnit>,

    /**
     * Engine warning light
     * 0: Off
     * 1: On
     */
    val warningEngineLight: VehicleAttribute<Int, NoUnit>,

    /**
     * Starter battery warning
     * 0: "no warning"
     * 1: "Low battery power"
     */
    val warningLowBattery: VehicleAttribute<Int, NoUnit>,

    /**
     * Tire warning lamp
     * 0: "No warning"
     * 1: "Permanent glowing"
     * 2: "Lamp blinking followed by permanent glowing"
     */
    val warningTireLamp: VehicleAttribute<Int, NoUnit>,

    /**
     * Tire warning Plattrollwarner
     * 0: "no warning"
     * 1: "warning"
     */
    val warningTireSprw: VehicleAttribute<Int, NoUnit>,

    /**
     * Tire warning RDK
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     */
    val warningTireSrdk: VehicleAttribute<Int, NoUnit>,

    /**
     * Wash water low warning
     * 0: "wash water warning inactive"
     * 1: "wash water warning triggered"
     */
    val warningWashWater: VehicleAttribute<Int, NoUnit>,

    /**
     * Rear roller blind state
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     */
    val windowStatusRearBlind: VehicleAttribute<Int, NoUnit>,

    /**
     * Rear passenger compartment side roller blind left state
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     */
    val windowStatusRearLeftBlind: VehicleAttribute<Int, NoUnit>,

    /**
     * Rear passenger compartment side roller blind right state
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     */
    val windowStatusRearRightBlind: VehicleAttribute<Int, NoUnit>,

    /**
     * Window open state of all windows combined
     * 0: windows in intermediate position
     * 1: windows opened
     * 2: windows closed
     */
    val windowStateOverall: VehicleAttribute<Int, NoUnit>,

    /**
     * Window open state front left
     * 0: "window in intermediate position"
     * 1: "window completely opened"
     * 2: "window completely closed"
     * 3: "window airing position"
     * 4: "window intermidiate airing position"
     * 5: "window currently running"
     */
    val windowStateFrontLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Window open state front right
     * 0: "window in intermediate position"
     * 1: "window completely opened"
     * 2: "window completely closed"
     * 3: "window airing position"
     * 4: "window intermidiate airing position"
     * 5: "window currently running"
     */
    val windowStateFrontRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Window open state front left
     * 0: "window in intermediate position"
     * 1: "window completely opened"
     * 2: "window completely closed"
     * 3: "window airing position"
     * 4: "window intermidiate airing position"
     * 5: "window currently running"
     */
    val windowStateRearLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Window open state rear right
     * 0: "window in intermediate position"
     * 1: "window completely opened"
     * 2: "window completely closed"
     * 3: "window airing position"
     * 4: "window intermidiate airing position"
     * 5: "window currently running"
     */
    val windowStateRearRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Flipper window rotary latch state opened/closed
     * 0: “closed”
     * 1: “open”
     */
    val flipWindowStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * List of up to 21 departure times set in HeadUnit
     */
    val weeklySetHU: VehicleAttribute<List<DayTime>, NoUnit>,

    /**
     * Zero emission vehicle status details
     */
    val zevUpdate: ZevStatusUpdate,

    /**
     * Reason of last theft warning
     * See [TheftWarningReasonState]
     */
    val lastTheftWarningReason: VehicleAttribute<Int, NoUnit>,

    /**
     * UTC timestamp of last theft alarm
     * Range: 0..2^64-2
     */
    val lastTheftWarning: VehicleAttribute<Long, NoUnit>,

    /**
     * Weekly profile for preconditioning the vehicle.
     */
    val weeklyProfile: VehicleAttribute<WeeklyProfile, NoUnit>,

    /**
     * Teenage Driving Mode state
     * 0: Unknown
     * 1: Mode off
     * 2: Mode on
     * 3: Pending off
     * 4: Pending on
     * 5: Error
     */
    val teenageDrivingMode: VehicleAttribute<Int, NoUnit>,

    /**
     * Valet Driving Mode state
     * 0: Unknown
     * 1: Mode off
     * 2: Mode on
     * 3: Pending off
     * 4: Pending on
     * 5: Error
     */
    val valetDrivingMode: VehicleAttribute<Int, NoUnit>
)
