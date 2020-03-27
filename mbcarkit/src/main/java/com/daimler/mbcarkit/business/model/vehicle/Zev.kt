package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.ClockHourUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.RatioUnit

data class Zev(
    /**
     * Charging active
     */
    var chargingActive: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Charging error
     * 0: No message"
     * 1: Unlock Charging cable not possible
     * 2: Charging error, please switch charging mode
     * 3: Vehicle not charging. Charging station error
     * 4: Charging mode not available. Please try again or switch charging mode
     */
    var chargingError: VehicleAttribute<ChargingError, NoUnit>,

    /**
     * Charging mode, e.g. 'conductive DC' or 'inductive'
     */
    var chargingMode: VehicleAttribute<ChargingMode, NoUnit>,

    /**
     * Charging status, e.g. 'fast' or 'unplugged'
     */
    var chargingStatus: VehicleAttribute<ChargingStatus, NoUnit>,

    /**
     * HybridWarnings, e.g. 'starter battery warning' or 'HV power net fault'
     */
    var hybridWarnings: VehicleAttribute<HybridWarningState, NoUnit>,

    /**
     *
     */
    var activeState: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * EMM maximum SOC display request
     * Maximum HV battery State of Charge in percent as set in vehicle
     * Range: 0..100
     */
    var maxSoc: VehicleAttribute<Int, RatioUnit>,

    /**
     * Maximum HV battery SOC lower limit in percent as calculated by vehicle
     * Range: 0..100
     */
    var maxSocLowerLimit: VehicleAttribute<Int, RatioUnit>,

    /**
     * Selected Charge Program which is set in the vehicle, e.g. 'Home' or 'Instant'
     */
    var selectedChargeProgram: VehicleAttribute<ChargingProgram, NoUnit>,

    /**
     * Status optimized/intelligent charging, e.g. 'Wallbox' or 'Peek'
     * 0: Wallbox (Ladesäule) is active
     * 1: Smart Charge Communication is active
     * 2: On/Off-Peak setting is active (Tag-/Nachtstrom)
     */
    var smartCharging: VehicleAttribute<SmartCharging, NoUnit>,

    /**
     *
     */
    var smartChargingAtDeparture: VehicleAttribute<SmartChargingDeparture, NoUnit>,

    /**
     *
     */
    var smartChargingAtDeparture2: VehicleAttribute<SmartChargingDeparture, NoUnit>,

    /**
     * Temperatures inside the vehicle for individual temperature zones
     */
    val temperature: ZevTemperature,

    /**
     *
     */
    var weekdayTariff: VehicleAttribute<List<ZevTariff>, NoUnit>,

    /**
     *
     */
    var weekendTariff: VehicleAttribute<List<ZevTariff>, NoUnit>,

    /**
     * Current charging Power in kW (only valid while charging)
     * Range: -102.4..309.2
     */
    var chargingPower: VehicleAttribute<Double, NoUnit>,

    /**
     * Departure time in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    var departureTime: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * Departure time mode, e.g. 'Weekly Set' or 'Once'
     */
    var departureTimeMode: VehicleAttribute<DepartureTimeMode, NoUnit>,

    /**
     * State of charge at departure time in percent
     * Range: 0.0..100.0
     */
    var departureTimeSoc: VehicleAttribute<Int, RatioUnit>,

    /**
     * Departure time weekday
     */
    var departureTimeWeekday: VehicleAttribute<Day, NoUnit>,

    /**
     * End of charge time counted in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    var endOfChargeTime: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * End of relative charge time in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    var endOfChargeTimeRelative: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * End of charge time weekday
     */
    var endOfChargeTimeWeekday: VehicleAttribute<Day, NoUnit>,

    /**
     * Max. electrical range with 100% State of Charge in given distance unit
     * Range: 0..1500
     */
    var maxRange: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Preconditioning active state
     */
    var precondActive: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Preconditioning setting at departure time
     */
    var precondAtDeparture: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Preconditioning disabled because of too many trials triggered by week profile
     */
    var precondAtDepartureDisable: VehicleAttribute<DisabledState, NoUnit>,

    /**
     * Preconditioning runtime in minutes
     * Range: 0..254
     */
    var precondDuration: VehicleAttribute<Int, NoUnit>,

    /**
     * Preconditioning error, e.g. 'battery/fuel low'
     */
    var precondError: VehicleAttribute<PrecondErrorState, NoUnit>,

    /**
     * Immediate preconditioning active
     */
    var precondNow: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Immediate preconditioning error, e.g. 'battery/fuel low'
     */
    var precondNowError: VehicleAttribute<PrecondErrorState, NoUnit>,

    /**
     * Seat preconditioning front right
     */
    var precondSeatFrontRight: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Seat preconditioning front left
     */
    var precondSeatFrontLeft: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Seat preconditioning rear right
     */
    var precondSeatRearRight: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Seat preconditioning rear left
     */
    var precondSeatRearLeft: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * State of Charge history as list of value-timestamp pairs
     */
    var socprofile: VehicleAttribute<List<SocProfile>, NoUnit>,

    /**
     * Current parameters for charging programs.
     */
    var chargingPrograms: VehicleAttribute<List<VehicleChargingProgramParameter>, NoUnit>,

    /**
     * Activation status of bidirectional charging
     */
    var bidirectionalChargingActive: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * EMM minimum SOC display request
     */
    var minSoc: VehicleAttribute<Int, RatioUnit>,

    /**
     * Display actual charging current limitation by customer
     */
    var acChargingCurrentLimitation: VehicleAttribute<AcChargingCurrentLimitation, NoUnit>,

    /**
     * Charging error infrastructure
     */
    var chargingErrorInfrastructure: VehicleAttribute<ChargingErrorInfrastructure, NoUnit>,

    /**
     * Display flag absolute or relative charging time
     */
    var chargingTimeType: VehicleAttribute<ChargingTimeType, NoUnit>,

    /**
     * EMM minimum SOC lower limit request
     */
    var minSocLowerLimit: VehicleAttribute<Int, RatioUnit>,

    /**
     * Departure time in minutes from midnight (vehicle HeadUnit time)
     */
    var nextDepartureTime: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * weeklyset day
     */
    var nextDepartureTimeWeekday: VehicleAttribute<Day, NoUnit>,

    /**
     * PNHV departure time icon display request
     */
    var departureTimeIcon: VehicleAttribute<DepartureTimeIcon, NoUnit>,

    /**
     * Charging error warning and information messages (WIM)
     */
    var chargingErrorWim: VehicleAttribute<ChargingErrorWim, NoUnit>,

    /**
     * Maximum HV battery SOC upper limit as calculated by vehicle.
     */
    var maxSocUpperLimit: VehicleAttribute<Int, RatioUnit>,

    /**
     * Minimum HV battery SOC upper limit as calculated by vehicle.
     */
    var minSocUpperLimit: VehicleAttribute<Int, RatioUnit>,

    /**
     * Max. charging power when ECO charging is active.
     */
    var chargingPowerEcoLimit: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging flap DC status
     */
    var chargeFlapDCStatus: VehicleAttribute<ChargeFlapStatus, NoUnit>,

    /**
     * Charging flap AC status
     */
    var chargeFlapACStatus: VehicleAttribute<ChargeFlapStatus, NoUnit>,

    /**
     * Charging coupler DC lock status
     */
    var chargeCouplerDCLockStatus: VehicleAttribute<ChargeCouplerLockStatus, NoUnit>,

    /**
     * Charging coupler AC lock status
     */
    var chargeCouplerACLockStatus: VehicleAttribute<ChargeCouplerLockStatus, NoUnit>,

    /**
     * Charging coupler DC status
     */
    var chargeCouplerDCStatus: VehicleAttribute<ChargeCouplerStatus, NoUnit>,

    /**
     * Charging coupler AC status
     */
    var chargeCouplerACStatus: VehicleAttribute<ChargeCouplerStatus, NoUnit>,

    /**
     * Predicted continuation of the trip for assisted routes
     */
    var evRangeAssistDriveOnTime: VehicleAttribute<Long, NoUnit>,

    /**
     * Predicted charge level at the continuation of the trip for assisted routes
     */
    var evRangeAssistDriveOnSOC: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging break ClockTimer.
     */
    var vehicleChargingBreakClockTimers: VehicleAttribute<List<VehicleChargingBreakClockTimer>, NoUnit>,

    /**
     * Remote control of EV charging power by back-end use cases.
     */
    var vehicleChargingPowerControl: VehicleAttribute<VehicleChargingPowerControl, NoUnit>
)
