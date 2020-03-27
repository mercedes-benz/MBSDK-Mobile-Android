package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.ClockHourUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.RatioUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.TemperatureUnit

data class ZevStatusUpdate(
    /**
     * Charging active
     * 0: charging inactive
     * 1: charging active
     */
    val chargingActive: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging error
     * 0: No message"
     * 1: Unlock Charging cable not possible
     * 2: Charging error, please switch charging mode
     * 3: Vehicle not charging. Charging station error
     * 4: Charging mode not available. Please try again or switch charging mode
     */
    val chargingError: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging mode
     * 0: No icon
     * 1: Conductive AC
     * 2: Inductive
     * 3: Conductive AC and inductive at the same time
     * 4: Conductive DC
     */
    val chargingMode: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging status
     * 0: Vehicle charging
     * 1: End of Charge
     * 2: Charge break
     * 3: Charge cable unplugged
     * 4: Charging failure
     * 5: Slow Charging
     * 6: Fast Charging
     * 7: Discharging
     * 8: No charging
     * 9: Charging foreign object detection
     */
    val chargingStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * HybridWarnings
     *
     * 0: No request
     * 1: Warning "Hybrid: seek service without engine stop" red
     * 2: Warning symbol "Hybrid: high voltage powernet fault" red
     * 3: Warning symbol "Hybrid: powertrain fault" white
     * 4: Warning symbol "Hybrid: starter battery"
     * 5: Warning symbol "Stop vehicle and charge battery"
     * 6: Warning "PlugIn: only EMode possible, red. power" red
     * 7: Warning "PlugIn: vehicle still active" red
     * 8: Warning symbol "Hybrid: power reduced“ yellow
     * 9: Warning symbol "Hybrid: Stop, Engine off“ red
     */
    val hybridWarnings: VehicleAttribute<Int, NoUnit>,

    /**
     *
     */
    val activeState: VehicleAttribute<Int, NoUnit>,

    /**
     * EMM maximum SOC display request
     * Maximum HV battery State of Charge in percent as set in vehicle
     * Range: 0..100
     */
    val maxSoc: VehicleAttribute<Int, RatioUnit>,

    /**
     * Maximum HV battery SOC lower limit in percent as calculated by vehicle
     * Range: 0..100
     */
    val maxSocLowerLimit: VehicleAttribute<Int, RatioUnit>,

    /**
     * Selected Charge Program which is set in the vehicle:
     * 0: DEFAULT CHARGE PROGRAM
     * 1: INSTANT CHARGE PROGRAM
     * 2: HOME CHARGE PROGRAM
     * 3: WORK CHARGE PROGRAM
     */
    val selectedChargeProgram: VehicleAttribute<Int, NoUnit>,

    /**
     * Status optimized/intelligent charging
     * 0: Wallbox (Ladesäule) is active
     * 1: Smart Charge Communication is active
     * 2: On/Off-Peak setting is active (Tag-/Nachtstrom)
     */
    val smartCharging: VehicleAttribute<Int, NoUnit>,

    /**
     * Smart charging at departure
     * 0: Inactive
     * 1: Requested
     */
    val smartChargingAtDeparture: VehicleAttribute<Int, NoUnit>,

    /**
     * Smart charging at departure
     * 0: Inactive
     * 1: Requested
     */
    val smartChargingAtDeparture2: VehicleAttribute<Int, NoUnit>,

    /**
     * Temperature front center in given temperature unit
     * Range: 0..30
     */
    val temperaturePointFrontCenter: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature front left in given temperature unit
     * Range: 0..30
     */
    val temperaturePointFrontLeft: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature front right in given temperature unit
     * Range: 0..30
     */
    val temperaturePointFrontRight: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature rear center (1) in given temperature unit
     * Range: 0..30
     */
    val temperaturePointRearCenter: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature rear center (2) in given temperature unit
     * Range: 0..30
     */
    val temperaturePointRearCenter2: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature rear left in given temperature unit
     * Range: 0..30
     */
    val temperaturePointRearLeft: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * Temperature rear right in given temperature unit
     * Range: 0..30
     */
    val temperaturePointRearRight: VehicleAttribute<Double, TemperatureUnit>,

    /**
     * List of Tariffs for weekdays
     */
    val weekdayTariff: VehicleAttribute<List<ZevTariff>, NoUnit>,

    /**
     * List of Tariffs for weekends
     */
    val weekendTariff: VehicleAttribute<List<ZevTariff>, NoUnit>,

    /**
     * Current charging Power in kW (only valid while charging)
     * Range: -102.4..309.2
     */
    val chargingPower: VehicleAttribute<Double, NoUnit>,

    /**
     * Departure time in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    val departureTime: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * Departure time mode
     * 0: Inactive
     * 1: Adhoc Active
     * 2: Weeklyset Active
     */
    val departureTimeMode: VehicleAttribute<Int, NoUnit>,

    /**
     * State of charge at departure time in percent
     * Range: 0.0..100.0
     */
    val departureTimeSoc: VehicleAttribute<Int, RatioUnit>,

    /**
     * Departure time weekday
     * 0: Monday
     * 1: Tuesday
     * 2: Wednesday
     * 3: Thursday
     * 4: Friday
     * 5: Saturday
     * 6: Sunday
     */
    val departureTimeWeekday: VehicleAttribute<Int, NoUnit>,

    /**
     * End of charge time counted in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    val endOfChargeTime: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * End of relative charge time in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    val endOfChargeTimeRelative: VehicleAttribute<Int, ClockHourUnit>,

    /**
     * End of charge time weekday
     * 0: Monday
     * 1: Tuesday
     * 2: Wednesday
     * 3: Thursday
     * 4: Friday
     * 5: Saturday
     * 6: Sunday
     */
    val endOfChargeTimeWeekday: VehicleAttribute<Int, NoUnit>,

    /**
     * Max. electrical range with 100% State of Charge in km
     * Range: 0..1500
     */
    val maxRange: VehicleAttribute<Int, DistanceUnit>,

    /**
     * Preconditioning active
     * 0: False
     * 1: True
     */
    val precondActive: VehicleAttribute<Int, NoUnit>,

    /**
     * Preconditioning at departure time
     * 0: inactive
     * 1: active
     */
    val precondAtDeparture: VehicleAttribute<Int, NoUnit>,

    /**
     * Preconditioning disabled because of too many trials triggered by week profile
     * 0: Not disabled
     * 1: Disabled
     */
    val precondAtDepartureDisable: VehicleAttribute<Int, NoUnit>,

    /**
     * Preconditioning runtime in minutes
     * Range: 0..254
     */
    val precondDuration: VehicleAttribute<Int, NoUnit>,

    /**
     * Preconditioning error
     * 0: No request/attribute is changed
     * 1: PreConditioning not possible, battery low/fuel low(CH2)
     * 2: PreConditioning available after restart engine
     * 3: PreConditioning not possible, charging not finished
     * 4: PreConditioning general error (SmartEdison)
     */
    val precondError: VehicleAttribute<Int, NoUnit>,

    /**
     * Immediate preconditioning
     * 0: Inactive
     * 1: Active
     */
    val precondNow: VehicleAttribute<Int, NoUnit>,

    /**
     * Immediate preconditioning error
     * 0: no request/attribute is changed
     * 1: PreConditioning not possible, battery low/fuel low(CH2)
     * 2: PreConditioning available after restart engine
     * 3: PreConditioning not possible, charging not finished
     */
    val precondNowError: VehicleAttribute<Int, NoUnit>,

    /**
     * Seat preconditioning front right
     * 0: False
     * 1: True
     */
    val precondSeatFrontRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Seat preconditioning front left
     * 0: False
     * 1: True
     */
    val precondSeatFrontLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * Seat preconditioning rear right
     * 0: False
     * 1: True
     */
    val precondSeatRearRight: VehicleAttribute<Int, NoUnit>,

    /**
     * Seat preconditioning rear left
     * 0: False
     * 1: True
     */
    val precondSeatRearLeft: VehicleAttribute<Int, NoUnit>,

    /**
     * State of Charge history as list of value-timestamp pairs
     */
    val socprofile: VehicleAttribute<List<SocProfile>, NoUnit>,

    /**
     * Current parameters for charging programs.
     */
    val chargingPrograms: VehicleAttribute<List<VehicleChargingProgramParameter>, NoUnit>,

    /**
     * Activation status of bidirectional charging
     * 0.. Not active
     * 1.. Active
     */
    val bidirectionalChargingActive: VehicleAttribute<Int, NoUnit>,

    /**
     * EMM minimum SOC display request
     * 0..100 % minimum HV battery SOC as set in vehicle
     * Range: 0..100
     */
    val minSoc: VehicleAttribute<Int, RatioUnit>,

    /**
     * Display actual charging current limitation by customer
     * 0.. Limit to 6A
     * 1.. Limit to 8A
     * 2.. not defined
     * 3.. not defined
     * 4.. not defined
     * 5.. not defined
     * 6.. No limit
     */
    val acChargingCurrentLimitation: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging error infrastructure
     * 0.. NO_ERRNo Error
     * 1.. AC_INFRA_ERRAC Infrastructure Error
     * 2.. DC_INFRA_ERRDC Infrastructure Error
     * 3.. IND_INFRA_ERRInductive_Infrastructure Error
     * 4.. AC_DC_INFRA_ERRAC DC Infrastructure Error
     * 5.. AC_IND_INFRA_ERRAC Inductive Infrastructure Error
     * 6.. DC_IND_INFRA_ERRDC Inductive Infrastructure Error
     * 7.. CHRG_ERRCharge Error
     * Range: 0..7
     */
    val chargingErrorInfrastructure: VehicleAttribute<Int, NoUnit>,

    /**
     * Display flag absolute or relative charging time
     * 0.. Absolute time
     * 1.. Relative time
     */
    val chargingTimeType: VehicleAttribute<Int, NoUnit>,

    /**
     * EMM minimum SOC lower limit request
     * 0..100 % minimum HV battery SOC lower limit as calculated by vehicle
     */
    val minSocLowerLimit: VehicleAttribute<Int, RatioUnit>,

    /**
     * departure time hour
     * departure time minute
     * 0..1439: Departure time counted in minutes from midnight depending on vtime
     * -1: departure time inactive (< Star 3 only)
     * Range: -1..1439
     */
    val nextDepartureTime: VehicleAttribute<Int, NoUnit>,

    /**
     * weeklyset day
     * 0.. Monday
     * 1.. Tuesday
     * 2.. Wednesday
     * 3.. Thursday
     * 4.. Friday
     * 5.. Saturday
     * 6.. Sunday
     */
    val nextDepartureTimeWeekday: VehicleAttribute<Int, NoUnit>,

    /**
     * PNHV departure time icon display request
     * 0.. INACTV
     * 1.. ADHOC_ACTV
     * 2.. WEEK_DEP_TM_ACTV
     * 3.. SKIP
     * 4.. TRIP_ACTV
     */
    val departureTimeIcon: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging error warning and information messages (WIM)
     * 0.. No error
     * 1.. Message1
     * 2... Message2
     * 3... Message3
     * 4... Message4
     * 5... Message5
     * 6... Message6
     * 7... Message7
     * 8... Message8
     * 9... Message9
     * 10... Message10
     * 11... Message11
     * 12... Message12
     * 13... Message13
     * 14.. Message14
     */
    val chargingErrorWim: VehicleAttribute<Int, NoUnit>,

    /**
     * Maximum HV battery SOC upper limit as calculated by vehicle.
     * 0..100 %
     */
    val maxSocUpperLimit: VehicleAttribute<Int, RatioUnit>,

    /**
     * Minimum HV battery SOC upper limit as calculated by vehicle.
     * 0..100 %
     */
    val minSocUpperLimit: VehicleAttribute<Int, RatioUnit>,

    /**
     * Max. charging power when ECO charging is active.
     * 0..1021 kW
     * Range: 0..1023
     */
    val chargingPowerEcoLimit: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging flap DC status
     * 0.. Open
     * 1.. Closed
     * 2.. Flap pressed
     */
    val chargeFlapDCStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging flap AC status
     * 0.. Open
     * 1.. Closed
     * 2.. Flap pressed
     */
    val chargeFlapACStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging coupler DC lock status
     * 0.. Connector locked
     * 1.. Connector unlocked
     * 2.. Locking state not clear
     */
    val chargeCouplerDCLockStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging coupler AC lock status
     * 0.. Connector locked
     * 1.. Connector unlocked
     * 2.. Locking state not clear
     */
    val chargeCouplerACLockStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging coupler DC status
     * 0.. Charging wire plugged on both sides
     * 1.. Charging wire plugged on vehicle side
     * 2.. Charging wire not plugged on vehicle side
     * 3.. Plugged state unknown
     * 4.. Plugged state unknown due defect
     */
    val chargeCouplerDCStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging coupler AC status
     * 0.. Charging wire plugged on both sides
     * 1.. Charging wire plugged on vehicle side
     * 2.. Charging wire not plugged on vehicle side
     * 3.. Plugged state unknown
     * 4.. Plugged state unknown due defect
     */
    val chargeCouplerACStatus: VehicleAttribute<Int, NoUnit>,

    /**
     * Predicted continuation of the trip for assisted routes as Unix timestamp (local time)
     * Range: 0..2^32-1
     */
    val evRangeAssistDriveOnTime: VehicleAttribute<Long, NoUnit>,

    /**
     * Predicted charge level at the continuation of the trip for assisted routes in %
     * Range: 0..1023
     */
    val evRangeAssistDriveOnSOC: VehicleAttribute<Int, NoUnit>,

    /**
     * Charging break ClockTimer.
     */
    val vehicleChargingBreakClockTimers: VehicleAttribute<List<VehicleChargingBreakClockTimer>, NoUnit>,

    /**
     * Remote control of EV charging power by back-end use cases.
     */
    val vehicleChargingPowerControl: VehicleAttribute<VehicleChargingPowerControl, NoUnit>
)
