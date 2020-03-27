package com.daimler.mbprotokit.mapping.car

internal enum class ApiVehicleKey(val id: String) {
    // ---- Auxheat ----
    AUXHEAT_ACTIVE("auxheatActive"),
    AUXHEAT_RUNTIME("auxheatruntime"),
    AUXHEAT_STATUS("auxheatstatus"),
    AUXHEAT_TIME_1("auxheattime1"),
    AUXHEAT_TIME_2("auxheattime2"),
    AUXHEAT_TIME_3("auxheattime3"),
    AUXHEAT_TIME_SELECTION("auxheattimeselection"),
    AUXHEAT_WARNINGS("auxheatwarnings"),

    // ---- DOORS ----
    DOOR_LOCK_STATE_FRONT_LEFT("doorlockstatusfrontleft"),
    DOOR_LOCK_STATE_REAR_LEFT("doorlockstatusrearleft"),
    DOOR_LOCK_STATE_FRONT_RIGHT("doorlockstatusfrontright"),
    DOOR_LOCK_STATE_REAR_RIGHT("doorlockstatusrearright"),
    DOOR_STATE_FRONT_LEFT("doorstatusfrontleft"),
    DOOR_STATE_REAR_LEFT("doorstatusrearleft"),
    DOOR_STATE_FRONT_RIGHT("doorstatusfrontright"),
    DOOR_STATE_REAR_RIGHT("doorstatusrearright"),

    // ---- Engine ----
    IGNITION_STATE("ignitionstate"),
    ENGINE_STATE("engineState"),
    REMOTE_START_ACTIVE("remoteStartActive"),
    REMOTE_START_TEMPERATURE("remoteStartTemperature"),
    REMOTE_START_ENDTIME("remoteStartEndtime"),

    // ---- Headunit ----
    TEMPERATURE_UNIT_HU("temperatureUnitHU"),
    LANGUAGE_HU("languageHU"),
    TIME_FORMAT_HU("timeFormatHU"),
    TRACKING_STATE_HU("trackingStateHU"),
    WEEKLY_PROFILE("weeklyProfile"),
    WEEKLY_SET_HU("weeklySetHU"),

    // ---- Position ----
    POSITION_HEADING("positionHeading"),
    POSITION_LAT("positionLat"),
    POSITION_LONG("positionLong"),
    POSITION_ERROR_CODE("vehiclePositionErrorCode"),
    PROXIMITY_CALCULATION_POSITION_REQUIRED("proximityCalculationForVehiclePositionRequired"),

    // ---- Sunroof ----
    SUNROOF_EVENT("sunroofEvent"),
    SUNROOF_EVENT_ACTIVE("sunroofEventActive"),
    SUNROOF_STATUS("sunroofstatus"),
    SUNROOF_BLIND_STATUS_FRONT("sunroofStatusFrontBlind"),
    SUNROOF_BLIND_STATUS_REAR("sunroofStatusRearBlind"),

    // ---- Collision ----
    LAST_PARK_EVENT("lastParkEvent"),
    LAST_PARK_EVENT_NOT_CONFIRMED("lastParkEventNotConfirmed"),
    PARK_EVENT_SENSOR_STATUS("parkEventSensorStatus"),
    PARK_EVENT_LEVEL("parkEventLevel"),
    PARK_EVENT_TYPE("parkEventType"),

    // ---- Theft ----
    INTERIOR_PROTECTION_SENSOR_STATE("interiorProtectionSensorStatus"),
    KEY_ACTIVATION_STATE("keyActivationState"),
    THEFT_ALARM_ACTIVE("theftAlarmActive"),
    THEFT_SYSTEM_ARMED("theftSystemArmed"),
    TOW_PROTECTION_SENSOR_STATE("towProtectionSensorStatus"),
    LAST_THEFT_WARNING_REASON("lastTheftWarningReason"),
    LAST_THEFT_WARNING("lastTheftWarning"),

    // ---- Tires ----
    TIRE_PRESSURE_FRONT_LEFT("tirepressureFrontLeft"),
    TIRE_PRESSURE_FRONT_RIGHT("tirepressureFrontRight"),
    TIRE_PRESSURE_REAR_LEFT("tirepressureRearLeft"),
    TIRE_PRESSURE_REAR_RIGHT("tirepressureRearRight"),
    TIRE_PRESSURE_MEASUREMENT_TIMESTAMP("tirePressureMeasurementTimestamp"),
    TIRE_SENSOR_AVAILABLE("tireSensorAvailable"),
    TIRE_MARKER_FRONT_LEFT("tireMarkerFrontLeft"),
    TIRE_MARKER_FRONT_RIGHT("tireMarkerFrontRight"),
    TIRE_MARKER_REAR_LEFT("tireMarkerRearLeft"),
    TIRE_MARKER_REAR_RIGHT("tireMarkerRearRight"),
    TIRE_WARNING_SRDK("tirewarningsrdk"),

    // ---- Vehicle Data ----
    ODO("odo"),
    PARK_BRAKE_STATUS("parkbrakestatus"),
    ROOFTOP_STATUS("rooftopstatus"),
    SERVICE_INTERVAL_DAYS("serviceintervaldays"),
    SERVICE_INTERVAL_DISTANCE("serviceintervaldistance"),
    SOC("soc"),
    SPEED_ALERT_CONFIGURATION("speedAlertConf"),
    SPEED_UNIT_FROM_IC("speedUnitFromIC"),
    STARTER_BATTERY_STATE("starterBatteryState"),
    VEHICLE_DATA_CONNECTION_STATE("vehicleDataConnectionState"),
    V_TIME("vtime"),
    DOOR_LOCK_STATUS_GAS("doorlockstatusgas"),
    DOOR_LOCK_STATUS_VEHICLE("doorlockstatusvehicle"),
    DOOR_LOCK_STATUS_OVERALL("doorLockStatusOverall"),
    FILTER_PARTICLE_LOADING("filterParticleLoading"),
    ENGINE_HOOD_STATUS("engineHoodStatus"),

    // ---- Windows ----
    WINDOW_STATUS_FRONT_LEFT("windowstatusfrontleft"),
    WINDOW_STATUS_FRONT_RIGHT("windowstatusfrontright"),
    WINDOW_STATUS_REAR_LEFT("windowstatusrearleft"),
    WINDOW_STATUS_REAR_RIGHT("windowstatusrearright"),
    WINDOW_STATUS_OVERALL("windowStatusOverall"),
    WINDOW_STATUS_FLIP("flipWindowStatus"),
    WINDOW_BLIND_STATUS_REAR("windowStatusRearBlind"),
    WINDOW_BLIND_STATUS_REAR_LEFT("windowStatusRearLeftBlind"),
    WINDOW_BLIND_STATUS_REAR_RIGHT("windowStatusRearRightBlind"),

    // ---- Consumption ----
    ELECTRIC_CONSUMPTION_RESET("electricconsumptionreset"),
    ELECTRIC_CONSUMPTION_START("electricconsumptionstart"),
    GAS_CONSUMPTION_RESET("gasconsumptionreset"),
    GAS_CONSUMPTION_START("gasconsumptionstart"),
    LIQUID_CONSUMPTION_RESET("liquidconsumptionreset"),
    LIQUID_CONSUMPTION_START("liquidconsumptionstart"),

    // ---- Decklid ----
    DOOR_LOCK_STATUS_DECKLID("doorlockstatusdecklid"),
    DECKLID_STATUS("decklidstatus"),

    // ---- Distance ----
    DISTANCE_ELECTRICAL_RESET("distanceElectricalReset"),
    DISTANCE_ELECTRICAL_START("distanceElectricalStart"),
    DISTANCE_GAS_RESET("distanceGasReset"),
    DISTANCE_GAS_START("distanceGasStart"),
    DISTANCE_RESET("distanceReset"),
    DISTANCE_START("distanceStart"),
    DISTANCE_ZERESET("distanceZEReset"),
    DISTANCE_ZESTART("distanceZEStart"),

    // ---- DrivenTime ----
    DRIVEN_TIME_RESET("drivenTimeReset"),
    DRIVEN_TIME_START("drivenTimeStart"),
    DRIVEN_TIME_ZERESET("drivenTimeZEReset"),
    DRIVEN_TIME_ZESTART("drivenTimeZEStart"),

    // ---- EcoScore ----
    ECO_SCORE_ACCELL("ecoscoreaccel"),
    ECO_SCORE_BONUS_RANGE("ecoscorebonusrange"),
    ECO_SCORE_CONST("ecoscoreconst"),
    ECO_SCORE_FREE_WHL("ecoscorefreewhl"),
    ECO_SCORE_TOTAL("ecoscoretotal"),

    // ---- Speed ----
    AVERAGE_SPEED_RESET("averageSpeedReset"),
    AVERAGE_SPEED_START("averageSpeedStart"),

    // ---- Tank ----
    GAS_TANK_LEVEL("gasTankLevel"),
    GAS_TANK_RANGE("gasTankRange"),
    OVERALL_RANGE("overallRange"),
    RANGE_ELECTRIC("rangeelectric"),
    RANGE_LIQUID("rangeliquid"),
    TANK_LEVEL_ADBLUE("tankLevelAdBlue"),
    TANK_LEVEL_PERCENT("tanklevelpercent"),

    // ---- Zev ----
    ACTIVE("active"),
    CHARGING_ERROR_DETAILS("chargingErrorDetails"),
    CHARGING_STATUS("chargingstatus"),
    HYBRID_WARNINGS("hybridWarnings"),
    MAX_SOC("maxSoc"),
    MAX_SOC_LOWER_LIMIT("maxSocLowerLimit"),
    SELECTED_CHARGE_PROGRAMM("selectedChargeProgram"),
    SMART_CHARGING("smartCharging"),
    SMART_CHARGING_AT_DEPARTURE("smartChargingAtDeparture"),
    SMART_CHARGING_AT_DEPARTURE2("smartChargingAtDeparture2"),
    TEMPERATURE_POINTS("temperaturePoints"),
    WEEKDAY_TARIFF("weekdaytariff"),
    WEEKEND_TARIFF("weekendtariff"),
    CHARGING_POWER("chargingPower"),
    DEPARTURE_TIME("departuretime"),
    DEPARTURE_TIME_MODE("departureTimeMode"),
    DEPARTURE_TIME_SOC("departuretimesoc"),
    DEPARTURE_TIME_WEEKDAY("departureTimeWeekday"),
    END_OF_CHARGE_TIME("endofchargetime"),
    END_OF_CHARGE_TIME_RELATIVE("endOfChargeTimeRelative"),
    END_OF_CHARGE_TIME_WEEKDAY("endofChargeTimeWeekday"),
    MAX_RANGE("maxrange"),
    PRECOND_AT_DEPARTURE_DISABLE("precondAtDepartureDisable"),
    PRECOND_DURATION("precondDuration"),
    PRECOND_ERROR("precondError"),
    PRECOND_NOW("precondNow"),
    PRECOND_NOW_ERROR("precondNowError"),
    PRECOND_SEAT_FRONT_RIGHT("precondSeatFrontRight"),
    PRECOND_SEAT_FRONT_LEFT("precondSeatFrontLeft"),
    PRECOND_SEAT_REAR_RIGHT("precondSeatRearRight"),
    PRECOND_SEAT_REAR_LEFT("precondSeatRearLeft"),
    SOC_PROFILE("socprofile"),
    CHARGING_PROGRAMS("chargePrograms"),
    CHARGING_ACTIVE("chargingactive"),
    CHARGING_MODE("chargingMode"),
    PRECOND_ACTIVE("precondActive"),
    BIDIRECTIONAL_CHARGING_ACTIVE("bidirectionalChargingActive"),
    MIN_SOC("minSOC"),
    AC_CHARGING_CURRENT_LIMITATION("acChargingCurrentLimitation"),
    CHARGING_ERROR_INFRASTRUCTURE("chargingErrorInfrastructure"),
    CHARGING_TIME_TYPE("chargingTimeType"),
    MIN_SOC_LOWER_LIMIT("minSocLowerLimit"),
    NEXT_DEPARTURE_TIME("nextDepartureTime"),
    NEXT_DEPARTURE_TIME_WEEKDAY("nextDepartureTimeWeekday"),
    DEPARTURE_TIME_ICON("departureTimeIcon"),
    CHARGING_ERROR_WIM("chargingErrorWim"),
    MAX_SOC_UPPER_LIMIT("maxSocUpperLimit"),
    MIN_SOC_UPPER_LIMIT("minSocUpperLimit"),
    CHARGING_POWER_ECO_LIMIT("chargingPowerEcoLimit"),
    CHARGE_FLAP_DC_STATUS("chargeFlapDCStatus"),
    CHARGE_FLAP_AC_STATUS("chargeFlapACStatus"),
    CHARGE_COUPLER_DC_LOCK_STATUS("chargeCouplerDCLockStatus"),
    CHARGE_COUPLER_AC_LOCK_STATUS("chargeCouplerACLockStatus"),
    CHARGE_COUPLER_DC_STATUS("chargeCouplerDCStatus"),
    CHARGE_COUPLER_AC_STATUS("chargeCouplerACStatus"),
    EV_RANGE_ASSIST_DRIVE_ON_TIME("evRangeAssistDriveOnTime"),
    EV_RANGE_ASSIST_DRIVE_ON_SOC("evRangeAssistDriveOnSOC"),
    CHARGING_BREAK_CLOCK_TIMER("chargingBreakClockTimer"),
    CHARGING_POWER_CONTROL("chargingPowerControl"),

    // ---- Vehicle Update ----
    DOOR_STATE_OVERALL("doorStatusOverall"),
    VEHICLE_HEALTH_STATUS("vehicleHealthStatus"),

    // ---- Warning ----
    ELECTRIC_RANGE_SKIP_INDICATION("electricalRangeSkipIndication"),
    LIQUID_RANGE_SKIP_INDICATION("liquidRangeSkipIndication"),
    TIRE_WARNING_LAMP("tirewarninglamp"),
    TIRE_WARNING_LEVEL_PRW("tireWarningLevelPrw"),
    TIRE_WARNING_SPRW("tirewarningsprw"),
    WARNING_BRAKE_FLUID("warningbrakefluid"),
    WARNING_BRAKE_LINING_WEAR("warningbrakeliningwear"),
    WARNING_COOLANT_LEVEL_LOW("warningcoolantlevellow"),
    WARNING_ENGINE_LIGHT("warningenginelight"),
    WARNING_LOW_BATTERY("warninglowbattery"),
    WARNING_WASH_WATER("warningwashwater"),

    // ---- Driving Modes ----
    TEENAGE_DRIVING_MODE("teenageDrivingMode"),
    VALET_DRIVING_MODE("valetDrivingMode"),

    // currently not used but prepared
    PRECOND_AT_DEPARTURE("precondatdeparture"),
    EVENT_TIMESTAMP("eventTimestamp"),
    GAS_TANK_LEVEL_PERCENT("gasTankLevelPercent")
}
