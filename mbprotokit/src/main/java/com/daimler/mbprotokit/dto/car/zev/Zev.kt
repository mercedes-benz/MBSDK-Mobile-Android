package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.dto.car.Day
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Zev(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Charging active
     * false: charging inactive
     * true: charging active
     */
    val chargingActive: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_ACTIVE
    )

    /**
     * Charging error
     * 0: No message"
     * 1: Unlock Charging cable not possible
     * 2: Charging error, please switch charging mode
     * 3: Vehicle not charging. Charging station error
     * 4: Charging mode not available. Please try again or switch charging mode
     */
    val chargingError: Pair<ChargingError?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_ERROR_DETAILS,
        ChargingError.map()
    )

    /**
     * Charging mode
     * 0: No icon
     * 1: Conductive AC
     * 2: Inductive
     * 3: Conductive AC and inductive at the same time
     * 4: Conductive DC
     */
    val chargingMode: Pair<ChargingMode?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_MODE,
        ChargingMode.map()
    )

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
    val chargingStatus: Pair<ChargingStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_STATUS,
        ChargingStatus.map()
    )

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
    val hybridWarnings: Pair<HybridWarningState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.HYBRID_WARNINGS,
        HybridWarningState.map()
    )

    val activeState: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ACTIVE
    )

    /**
     * EMM maximum SOC display request
     * Maximum HV battery State of Charge in percent as set in vehicle
     * Range: 0..100
     */
    val maxSoc: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.MAX_SOC
    )

    /**
     * Maximum HV battery SOC lower limit in percent as calculated by vehicle
     * Range: 0..100
     */
    val maxSocLowerLimit: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.MAX_SOC_LOWER_LIMIT
    )

    /**
     * Selected Charge Program which is set in the vehicle:
     * 0: DEFAULT CHARGE PROGRAM
     * 1: INSTANT CHARGE PROGRAM
     * 2: HOME CHARGE PROGRAM
     * 3: WORK CHARGE PROGRAM
     */
    val selectedChargeProgram: Pair<ChargingProgram?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SELECTED_CHARGE_PROGRAMM,
        ChargingProgram.map()
    )

    /**
     * Status optimized/intelligent charging
     * 0: Wallbox (Ladesäule) is active
     * 1: Smart Charge Communication is active
     * 2: On/Off-Peak setting is active (Tag-/Nachtstrom)
     */
    val smartCharging: Pair<SmartCharging?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SMART_CHARGING,
        SmartCharging.map()
    )

    /**
     * Smart charging at departure
     * 0: Inactive
     * 1: Requested
     */
    val smartChargingAtDeparture: Pair<SmartChargingDeparture?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SMART_CHARGING_AT_DEPARTURE,
        SmartChargingDeparture.map()
    )

    /**
     * Smart charging at departure
     * 0: Inactive
     * 1: Requested
     */
    val smartChargingAtDeparture2: Pair<SmartChargingDeparture?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SMART_CHARGING_AT_DEPARTURE2,
        SmartChargingDeparture.map()
    )

    /**
     * Map of temperature (front: left, center, right; rear: left, center, right)
     * Temperature in given temperature unit
     * Range: 0..30
     */
    val temperaturePoints: Pair<Map<TemperatureZone, Double?>?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TEMPERATURE_POINTS,
        TemperatureSeats.mapToTemperaturePoints()
    )

    /**
     * List of Tariffs for weekdays
     */
    val weekdayTariff: Pair<List<ZevTariff>?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WEEKDAY_TARIFF,
        ZevTariff.mapToWeekdayTariffs()
    )

    /**
     * List of Tariffs for weekends
     */
    val weekendTariff: Pair<List<ZevTariff>?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WEEKEND_TARIFF,
        ZevTariff.mapToWeekendTariffs()
    )

    /**
     * Current charging Power in kW (only valid while charging)
     * Range: -102.4..309.2
     */
    val chargingPower: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_POWER
    )

    /**
     * Departure time in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    val departureTime: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DEPARTURE_TIME
    )

    /**
     * Departure time mode
     * 0: Inactive
     * 1: Adhoc Active
     * 2: Weeklyset Active
     */
    val departureTimeMode: Pair<DepartureTimeMode?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DEPARTURE_TIME_MODE,
        DepartureTimeMode.map()
    )

    /**
     * State of charge at departure time in percent
     * Range: 0.0..100.0
     */
    val departureTimeSoc: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DEPARTURE_TIME_SOC
    )

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
    val departureTimeWeekday: Pair<Day?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DEPARTURE_TIME_WEEKDAY,
        Day.map()
    )

    /**
     * End of charge time counted in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    val endOfChargeTime: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.END_OF_CHARGE_TIME
    )

    /**
     * End of relative charge time in minutes from midnight (vehicle HeadUnit time)
     * Range: 0..1439
     */
    val endOfChargeTimeRelative: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.END_OF_CHARGE_TIME_RELATIVE
    )

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
    val endOfChargeTimeWeekday: Pair<Day?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.END_OF_CHARGE_TIME_WEEKDAY,
        Day.map()
    )

    /**
     * Max. electrical range with 100% State of Charge in km
     * Range: 0..1500
     */
    val maxRange: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.MAX_RANGE
    )

    /**
     * Preconditioning active
     */
    val precondActive: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_ACTIVE
    )

    /**
     * Preconditioning at departure time
     * false: inactive
     * true: active
     */
    val precondAtDeparture: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_AT_DEPARTURE
    )

    /**
     * Preconditioning disabled because of too many trials triggered by week profile
     * 0: Not disabled
     * 1: Disabled
     */
    val precondAtDepartureDisable: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_AT_DEPARTURE_DISABLE
    )

    /**
     * Preconditioning runtime in minutes
     * Range: 0..254
     */
    val precondDuration: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_DURATION
    )

    /**
     * Preconditioning error
     * 0: No request/attribute is changed
     * 1: PreConditioning not possible, battery low/fuel low(CH2)
     * 2: PreConditioning available after restart engine
     * 3: PreConditioning not possible, charging not finished
     * 4: PreConditioning general error (SmartEdison)
     */
    val precondError: Pair<PrecondErrorState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_ERROR,
        PrecondErrorState.map()
    )

    /**
     * Immediate preconditioning
     * false: Inactive
     * true: Active
     */
    val precondNow: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_NOW
    )

    /**
     * Immediate preconditioning error
     * 0: no request/attribute is changed
     * 1: PreConditioning not possible, battery low/fuel low(CH2)
     * 2: PreConditioning available after restart engine
     * 3: PreConditioning not possible, charging not finished
     */
    val precondNowError: Pair<PrecondErrorState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_NOW_ERROR,
        PrecondErrorState.map()
    )

    /**
     * Seat preconditioning front right
     */
    val precondSeatFrontRight: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_SEAT_FRONT_RIGHT
    )

    /**
     * Seat preconditioning front left
     */
    val precondSeatFrontLeft: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_SEAT_FRONT_LEFT
    )

    /**
     * Seat preconditioning rear right
     */
    val precondSeatRearRight: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_SEAT_REAR_RIGHT
    )

    /**
     * Seat preconditioning rear left
     */
    val precondSeatRearLeft: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PRECOND_SEAT_REAR_LEFT
    )

    /**
     * State of Charge history as list of value-timestamp pairs
     */
    val socprofile: Pair<List<SocProfile>?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.SOC_PROFILE,
        SocProfile.mapToSocProfiles()
    )

    /**
     * Current parameters for charging programs.
     */
    val chargingPrograms: Pair<List<VehicleChargingProgramParameter>?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_PROGRAMS,
        VehicleChargingProgramParameter.mapToVehicleChargingProgramParameters()
    )

    /**
     * Activation status of bidirectional charging
     * 0.. Not active
     * 1.. Active
     */
    val bidirectionalChargingActive: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.BIDIRECTIONAL_CHARGING_ACTIVE
    )

    /**
     * EMM minimum SOC display request
     * 0..100 % minimum HV battery SOC as set in vehicle
     * Range: 0..100
     */
    val minSoc: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.MIN_SOC
    )

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
    val acChargingCurrentLimitation: Pair<ChargingLimitation?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AC_CHARGING_CURRENT_LIMITATION,
        ChargingLimitation.map()
    )

    /**
     * Charging error infrastructure
     * 0.. NO_ERROR No Error
     * 1.. AC_INFRA_ERROR_AC Infrastructure Error
     * 2.. DC_INFRA_ERROR_DC Infrastructure Error
     * 3.. IND_INFRA_ERROR Inductive_Infrastructure Error
     * 4.. AC_DC_INFRA_ERROR AC DC Infrastructure Error
     * 5.. AC_IND_INFRA_ERROR_AC Inductive Infrastructure Error
     * 6.. DC_IND_INFRA_ERROR_DC Inductive Infrastructure Error
     * 7.. CHARGE_ERROR Charge Error
     * Range: 0..7
     */
    val chargingErrorInfrastructure: Pair<ChargingErrorInfrastructure?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_ERROR_INFRASTRUCTURE,
        ChargingErrorInfrastructure.map()
    )

    /**
     * Display flag absolute or relative charging time
     * 0.. Absolute time
     * 1.. Relative time
     */
    val chargingTimeType: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_TIME_TYPE
    )

    /**
     * EMM minimum SOC lower limit request
     * 0..100 % minimum HV battery SOC lower limit as calculated by vehicle
     */
    val minSocLowerLimit: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.MIN_SOC_LOWER_LIMIT
    )

    /**
     * departure time hour
     * departure time minute
     * 0..1439: Departure time counted in minutes from midnight depending on vtime
     * -1: departure time inactive (< Star 3 only)
     * Range: -1..1439
     */
    val nextDepartureTime: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.NEXT_DEPARTURE_TIME
    )

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
    val nextDepartureTimeWeekday: Pair<Day?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.NEXT_DEPARTURE_TIME_WEEKDAY,
        Day.map()
    )

    /**
     * PNHV departure time icon display request
     * 0.. INACTIVE
     * 1.. ADHOC_ACTIVE
     * 2.. WEEK_DEP_TM_ACTIVE
     * 3.. SKIP
     * 4.. TRIP_ACTIVE
     */
    val departureTimeIcon: Pair<DepartureTimeIcon?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DEPARTURE_TIME_ICON,
        DepartureTimeIcon.map()
    )

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
    val chargingErrorWim: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_ERROR_WIM
    )

    /**
     * Maximum HV battery SOC upper limit as calculated by vehicle.
     * 0..100 %
     */
    val maxSocUpperLimit: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.MAX_SOC_UPPER_LIMIT
    )

    /**
     * Minimum HV battery SOC upper limit as calculated by vehicle.
     * 0..100 %
     */
    val minSocUpperLimit: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.MIN_SOC_UPPER_LIMIT
    )

    /**
     * Max. charging power when ECO charging is active.
     * 0..1021 kW
     * Range: 0..1023
     */
    val chargingPowerEcoLimit: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_POWER_ECO_LIMIT
    )

    /**
     * Charging flap DC status
     * 0.. Open
     * 1.. Closed
     * 2.. Flap pressed
     */
    val chargeFlapDCStatus: Pair<ChargeFlapStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGE_FLAP_DC_STATUS,
        ChargeFlapStatus.map()
    )

    /**
     * Charging flap AC status
     * 0.. Open
     * 1.. Closed
     * 2.. Flap pressed
     */
    val chargeFlapACStatus: Pair<ChargeFlapStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGE_FLAP_AC_STATUS,
        ChargeFlapStatus.map()
    )

    /**
     * Charging coupler DC lock status
     * 0.. Connector locked
     * 1.. Connector unlocked
     * 2.. Locking state not clear
     */
    val chargeCouplerDCLockStatus: Pair<CouplerLockStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGE_COUPLER_DC_LOCK_STATUS,
        CouplerLockStatus.map()
    )

    /**
     * Charging coupler AC lock status
     * 0.. Connector locked
     * 1.. Connector unlocked
     * 2.. Locking state not clear
     */
    val chargeCouplerACLockStatus: Pair<CouplerLockStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGE_COUPLER_AC_LOCK_STATUS,
        CouplerLockStatus.map()
    )

    /**
     * Charging coupler DC status
     * 0.. Charging wire plugged on both sides
     * 1.. Charging wire plugged on vehicle side
     * 2.. Charging wire not plugged on vehicle side
     * 3.. Plugged state unknown
     * 4.. Plugged state unknown due defect
     */
    val chargeCouplerDCStatus: Pair<CouplerStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGE_COUPLER_DC_STATUS,
        CouplerStatus.map()
    )

    /**
     * Charging coupler AC status
     * 0.. Charging wire plugged on both sides
     * 1.. Charging wire plugged on vehicle side
     * 2.. Charging wire not plugged on vehicle side
     * 3.. Plugged state unknown
     * 4.. Plugged state unknown due defect
     */
    val chargeCouplerACStatus: Pair<CouplerStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGE_COUPLER_AC_STATUS,
        CouplerStatus.map()
    )

    /**
     * Predicted continuation of the trip for assisted routes as Unix timestamp (local time)
     * Range: 0..2^32-1
     */
    val evRangeAssistDriveOnTime: Pair<Long?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.EV_RANGE_ASSIST_DRIVE_ON_TIME
    )

    /**
     * Predicted charge level at the continuation of the trip for assisted routes in %
     * Range: 0..1023
     */
    val evRangeAssistDriveOnSOC: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.EV_RANGE_ASSIST_DRIVE_ON_SOC
    )

    /**
     * Charging break ClockTimer
     */
    val chargingBreakClockTimers: Pair<List<VehicleChargingBreakClockTimer>?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_BREAK_CLOCK_TIMER,
        VehicleChargingBreakClockTimer.mapVehicleChargingBreakClockTimer()
    )

    /**
     * Remote control of EV charging power by backend use cases
     */
    val chargingPowerControl: Pair<ChargingPowerControl?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.CHARGING_POWER_CONTROL,
        ChargingPowerControl.mapToChargingPowerControl()
    )
}
