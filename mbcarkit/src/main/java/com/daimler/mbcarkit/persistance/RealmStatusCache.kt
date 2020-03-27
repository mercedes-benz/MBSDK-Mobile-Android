package com.daimler.mbcarkit.persistance

import com.daimler.mbcarkit.business.model.vehicle.AcChargingCurrentLimitation
import com.daimler.mbcarkit.business.model.vehicle.ActiveSelectionState
import com.daimler.mbcarkit.business.model.vehicle.ActiveState
import com.daimler.mbcarkit.business.model.vehicle.ArmedState
import com.daimler.mbcarkit.business.model.vehicle.AuxheatState
import com.daimler.mbcarkit.business.model.vehicle.AuxheatTimeSelectionState
import com.daimler.mbcarkit.business.model.vehicle.AuxheatWarnings
import com.daimler.mbcarkit.business.model.vehicle.BatteryState
import com.daimler.mbcarkit.business.model.vehicle.ChargeCouplerLockStatus
import com.daimler.mbcarkit.business.model.vehicle.ChargeCouplerStatus
import com.daimler.mbcarkit.business.model.vehicle.ChargeFlapStatus
import com.daimler.mbcarkit.business.model.vehicle.ChargingBreakClockTimerAction
import com.daimler.mbcarkit.business.model.vehicle.ChargingError
import com.daimler.mbcarkit.business.model.vehicle.ChargingErrorInfrastructure
import com.daimler.mbcarkit.business.model.vehicle.ChargingErrorWim
import com.daimler.mbcarkit.business.model.vehicle.ChargingMode
import com.daimler.mbcarkit.business.model.vehicle.ChargingProgram
import com.daimler.mbcarkit.business.model.vehicle.ChargingStatus
import com.daimler.mbcarkit.business.model.vehicle.ChargingStatusForPowerControl
import com.daimler.mbcarkit.business.model.vehicle.ChargingTimeType
import com.daimler.mbcarkit.business.model.vehicle.Day
import com.daimler.mbcarkit.business.model.vehicle.DayTime
import com.daimler.mbcarkit.business.model.vehicle.DepartureTimeIcon
import com.daimler.mbcarkit.business.model.vehicle.DepartureTimeMode
import com.daimler.mbcarkit.business.model.vehicle.DisabledState
import com.daimler.mbcarkit.business.model.vehicle.DoorLockOverallStatus
import com.daimler.mbcarkit.business.model.vehicle.DoorLockState
import com.daimler.mbcarkit.business.model.vehicle.DoorOverallStatus
import com.daimler.mbcarkit.business.model.vehicle.DrivingModeState
import com.daimler.mbcarkit.business.model.vehicle.FilterParticleState
import com.daimler.mbcarkit.business.model.vehicle.HybridWarningState
import com.daimler.mbcarkit.business.model.vehicle.IgnitionState
import com.daimler.mbcarkit.business.model.vehicle.KeyActivationState
import com.daimler.mbcarkit.business.model.vehicle.LanguageState
import com.daimler.mbcarkit.business.model.vehicle.LastParkEventState
import com.daimler.mbcarkit.business.model.vehicle.LockStatus
import com.daimler.mbcarkit.business.model.vehicle.OnOffState
import com.daimler.mbcarkit.business.model.vehicle.OpenStatus
import com.daimler.mbcarkit.business.model.vehicle.ParkEventLevel
import com.daimler.mbcarkit.business.model.vehicle.ParkEventType
import com.daimler.mbcarkit.business.model.vehicle.PrecondErrorState
import com.daimler.mbcarkit.business.model.vehicle.Rate
import com.daimler.mbcarkit.business.model.vehicle.RequiredState
import com.daimler.mbcarkit.business.model.vehicle.RooftopState
import com.daimler.mbcarkit.business.model.vehicle.RunningState
import com.daimler.mbcarkit.business.model.vehicle.SensorState
import com.daimler.mbcarkit.business.model.vehicle.SmartCharging
import com.daimler.mbcarkit.business.model.vehicle.SmartChargingDeparture
import com.daimler.mbcarkit.business.model.vehicle.SocProfile
import com.daimler.mbcarkit.business.model.vehicle.SpeedAlertConfiguration
import com.daimler.mbcarkit.business.model.vehicle.SpeedUnitType
import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.SunroofBlindState
import com.daimler.mbcarkit.business.model.vehicle.SunroofEventState
import com.daimler.mbcarkit.business.model.vehicle.SunroofState
import com.daimler.mbcarkit.business.model.vehicle.TemperatureType
import com.daimler.mbcarkit.business.model.vehicle.TheftWarningReasonState
import com.daimler.mbcarkit.business.model.vehicle.TimeFormatType
import com.daimler.mbcarkit.business.model.vehicle.TimeProfile
import com.daimler.mbcarkit.business.model.vehicle.TireLampState
import com.daimler.mbcarkit.business.model.vehicle.TireLevelPrwState
import com.daimler.mbcarkit.business.model.vehicle.TireMarkerState
import com.daimler.mbcarkit.business.model.vehicle.TireSrdkState
import com.daimler.mbcarkit.business.model.vehicle.VehicleAttribute
import com.daimler.mbcarkit.business.model.vehicle.VehicleChargingBreakClockTimer
import com.daimler.mbcarkit.business.model.vehicle.VehicleChargingPowerControl
import com.daimler.mbcarkit.business.model.vehicle.VehicleChargingProgramParameter
import com.daimler.mbcarkit.business.model.vehicle.VehicleLocationErrorState
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.acChargingCurrentLimitationForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.activeSelectionStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.activeStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.armedStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.auxheatStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.auxheatTimeSelectionStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.auxheatWarningsFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.batteryStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargeCouplerLockStatusForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargeCouplerStatusForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargeFlapStatusForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargingErrorForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargingErrorInfrastructureForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargingErrorWimForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargingModeForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargingProgramForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargingStatusForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.chargingTimeTypeForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.dayForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.departureTimeIconForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.departureTimeModeStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.doorLockStateOverallForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.doorStateOverallForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.drivingModeStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.enableStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.filterParticelStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.hybridWarningStatusForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.ignitionStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.keyActivationStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.languageHuFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.lastParkEventStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.lockStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.onOffStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.openStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.parkEventLevelForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.parkEventTypeForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.precondErrorStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.requiredStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.rooftopStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.runningStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.sensorStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.smartChargingDepartureForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.smartChargingForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.speedUnitForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.sunroofBlindStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.sunroofEventStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.sunroofStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.temperatureTypeFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.theftWarningReasonStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.timeFormatFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.tireLampStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.tireLevelPrwStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.tireMarkerStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.tireSrdkStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.vehicleDoorLockstateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.vehicleLocationErrorStateForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.windowBlindStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.windowStateFromInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus.Companion.windowStateOverallForInt
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdate
import com.daimler.mbcarkit.business.model.vehicle.WeeklyProfile
import com.daimler.mbcarkit.business.model.vehicle.WindowBlindState
import com.daimler.mbcarkit.business.model.vehicle.WindowState
import com.daimler.mbcarkit.business.model.vehicle.WindowsOverallStatus
import com.daimler.mbcarkit.business.model.vehicle.ZevTariff
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.SpeedUnit
import com.daimler.mbcarkit.persistance.RealmUtil.Companion.FIELD_VIN_OR_FIN
import com.daimler.mbcarkit.persistance.model.RealmChargingProgram
import com.daimler.mbcarkit.persistance.model.RealmTimeObject
import com.daimler.mbcarkit.persistance.model.RealmTimeProfile
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeChargingBreakClockTimer
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeChargingPowerControl
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeChargingProgram
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeDouble
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeInt
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeLong
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeSocProfile
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeSpeedAlertConfiguration
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeTariff
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeWeeklyProfile
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeWeeklySetHU
import com.daimler.mbcarkit.persistance.model.RealmVehicleChargingBreakClockTimer
import com.daimler.mbcarkit.persistance.model.RealmVehicleState
import com.daimler.mbcarkit.persistance.model.fromVehicleAttribute
import com.daimler.mbcarkit.persistance.model.toVehicleAttribute
import com.daimler.mbcarkit.socket.VehicleStatusCache
import com.daimler.mbloggerkit.MBLoggerKit
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import io.realm.kotlin.where
import java.util.Date
import java.util.EnumSet
import kotlin.collections.HashMap

class RealmStatusCache(private val realm: Realm) : VehicleStatusCache {

    private companion object {
        const val NAME_AUXHEAT_ACTIVE_STATE = "auxheatActiveState"
        const val NAME_AUXHEAT_RUNTIME = "auxHeatRuntime"
        const val NAME_AUXHEAT_STATE = "auxheatState"
        const val NAME_AUXHEAT_TIME1 = "auxheatTime1"
        const val NAME_AUXHEAT_TIME2 = "auxheatTime2"
        const val NAME_AUXHEAT_TIME3 = "auxheatTime3"
        const val NAME_AUXHEAT_TIME_SELECTION = "auxheatTimeSelection"
        const val NAME_AUXHEAT_WARNINGS = "auxheatWarnings"
        const val NAME_AVG_SPEED_RESET = "averageSpeedReset"
        const val NAME_AVG_SPEED_START = "averageSpeedStart"
        const val NAME_BATTERY_STATE = "batteryState"
        const val NAME_CHARGING_ACTIVE = "chargingActive"
        const val NAME_CHARGING_ERROR = "chargingErrorDetails"
        const val NAME_CHARGING_MODE = "chargingMode"
        const val NAME_CHARGING_STATUS = "chargingstatus"
        const val NAME_CLIENT_MESSAGE_DATA = "clientMessageData"
        const val NAME_DECKLID_LOCK_STATE = "decklidLockState"
        const val NAME_DECKLID_STATE = "decklidState"
        const val NAME_DISTANCE_ELETRICAL_RESET = "distanceElectricalReset"
        const val NAME_DISTANCE_ELETRICAL_START = "distanceElectricalStart"
        const val NAME_DISTANCE_GAS_RESET = "distanceGasReset"
        const val NAME_DISTANCE_GAS_START = "distanceGasStart"
        const val NAME_DISTANCE_RESET = "distanceReset"
        const val NAME_DISTANCE_START = "distanceStart"
        const val NAME_DISTANCE_ZE_RESET = "distanceZeReset"
        const val NAME_DISTANCE_ZE_START = "distanceZeStart"
        const val NAME_DOOR_LOCK_STATE_FRONT_LEFT = "doorLockStateFrontLeft"
        const val NAME_DOOR_STATE_FRONT_LEFT = "doorStateFrontLeft"
        const val NAME_DOOR_LOCK_STATE_FRONT_RIGHT = "doorLockStateFrontRight"
        const val NAME_DOOR_STATE_FRONT_RIGHT = "doorStateFrontRight"
        const val NAME_DOOR_LOCK_STATE_GAS = "doorLockStateGas"
        const val NAME_DOOR_LOCK_STATE_REAR_LEFT = "doorLockStateRearLeft"
        const val NAME_DOOR_STATE_REAR_LEFT = "doorStateRearLeft"
        const val NAME_DOOR_LOCK_STATE_REAR_RIGHT = "doorLockStateRearRight"
        const val NAME_DOOR_STATE_REAR_RIGHT = "doorStateRearRight"
        const val NAME_DRIVEN_TIME_RESET = "drivenTimeReset"
        const val NAME_DRIVEN_TIME_START = "drivenTimeStart"
        const val NAME_DRIVEN_TIME_ZE_RESET = "drivenTimeZEReset"
        const val NAME_DRIVEN_TIME_ZE_START = "drivenTimeZEStart"
        const val NAME_ECO_SCORE_ACCEL = "ecoScoreAccel"
        const val NAME_ECO_SCORE_BONUS_RANGE = "ecoScoreBonusRange"
        const val NAME_ECO_SCORE_CONST = "ecoScoreConst"
        const val NAME_ECO_SCORE_FREEWHL = "ecoScoreFreeWhl"
        const val NAME_ECO_SCORE_TOTAL = "ecoScoreTotal"
        const val NAME_ELECTRIC_CONSUMPTION_RESET = "electricConsumptionReset"
        const val NAME_ELECTRIC_CONSUMPTION_START = "electricConsumptionStart"
        const val NAME_ELECTRIC_RANGE_SKIP_INDICATION = "electricalRangeSkipIndication"
        const val NAME_ENGINE_STATE = "engineState"
        const val NAME_ENGINE_HOOD_STATUS = "engineHoodStatus"
        const val NAME_FILTER_PARTICEL_STATE = "filterParticelState"
        const val NAME_GAS_CONSUMPTION_RESET = "gasConsumptionReset"
        const val NAME_GAS_CONSUMPTION_START = "gasConsumptionStart"
        const val NAME_HEALT_STATUS = "healthStatus"
        const val NAME_HYBRID_WARNINGS = "hybridWarnings"
        const val NAME_IGNITION_STATE = "ignitionState"
        const val NAME_INTERIOR_PROT_STATE = "interiorProtectionStatus"
        const val NAME_KEY_ACTIVATION_STATE = "keyActivationState"
        const val NAME_LANGUAGE_HU = "languageHu"
        const val NAME_LAST_PARK_EVENT = "lastParkEvent"
        const val NAME_LAST_PARK_EVENT_NOT_CONFIRMED = "lastParkEventNotConfirmed"
        const val NAME_PARK_EVENT_SENSOR_STATUS = "parkEventSensorStatus"
        const val NAME_LIQUID_CONSUMPTION_RESET = "liquidConsumptionReset"
        const val NAME_LIQUID_CONSUMPTION_START = "liquidConsumptionStart"
        const val NAME_LIQUID_CONSUMPTION_SKIP_INDICATION = "liquidRangeSkipIndication"
        const val NAME_LOCK_STATE_OVERALL = "lockStateOverall"
        const val NAME_MAX_SOC = "maxSoc"
        const val NAME_MAX_SOC_LOWER_LIMIT = "maxSocLowerLimit"
        const val NAME_ODO = "odo"
        const val NAME_OVERALL_RANGE = "overallRange"
        const val NAME_PARK_BRAKE_STATE = "parkBrakeState"
        const val NAME_PARK_EVENT_LEVEL = "parkEventLevel"
        const val NAME_PARK_EVENT_TYPE = "parkEventType"
        const val NAME_POSTION_HEADING = "positionHeading"
        const val NAME_POSITION_LAT = "positionLat"
        const val NAME_POSITION_LONG = "positionLong"
        const val NAME_POSITION_ERROR_CODE = "vehiclePositionErrorCode"
        const val NAME_PROXIMITY_CALCULATION_POSITION_REQUIRED = "proximityCalculationForVehiclePositionRequired"
        const val NAME_REMOTE_START_ACTIVE = "remoteStartActive"
        const val NAME_REMOTE_START_TEMPERATURE = "remoteStartTemperature"
        const val NAME_REMOTE_START_ENDTIME = "remoteStartEndtime"
        const val NAME_ROOF_TOP_STATE = "rooftopState"
        const val NAME_SELECTED_CHARGE_PROGRAM = "selectedChargeProgram"
        const val NAME_SERVICE_INTERVAL_DAYS = "serviceIntervalDays"
        const val NAME_SERVICE_INTERVAL_DISTANCE = "serviceIntervalDistance"
        const val NAME_SMART_CHARGING = "smartCharging"
        const val NAME_SMART_CHARGING_AT_DEPARTURE = "smartChargingAtDeparture"
        const val NAME_SMART_CHARGING_AT_DEPARTURE2 = "smartChargingAtDeparture2"
        const val NAME_SOC = "soc"
        const val NAME_SPEED_ALERT_CONFIGURATION = "speedAlertConfiguration"
        const val NAME_SPEED_UNIT_FROM_IC = "speedUnitFromIc"
        const val NAME_STATE_OVERALL = "stateOverall"
        const val NAME_SUNROOF_EVENT_STATE = "sunroofEventState"
        const val NAME_SUNROOF_EVENT_ACTIVE = "sunroofEventActive"
        const val NAME_SUNROOF_STATE = "sunroofState"
        const val NAME_SUNROOF_BLIND_FRONT_STATE = "sunroofStatusFrontBlind"
        const val NAME_SUNROOF_BLIND_REAR_STATE = "sunroofStatusRearBlind"
        const val NAME_TANK_AD_BLUE_LEVEL = "tankAdBlueLevel"
        const val NAME_TANK_ELECTRICAL_LEVEL = "tankElectricalLevel"
        const val NAME_TANK_ELETRICAL_RANGE = "tankElectricalRange"
        const val NAME_TANK_GAS_LEVEL = "tankGasLevel"
        const val NAME_TANK_GAS_RANGE = "tankGasRange"
        const val NAME_TANK_LIQUID_LEVEL = "tankLiquidLevel"
        const val NAME_TANK_LIQUID_RANGE = "tankLiquidRange"
        const val NAME_TEENAGE_DRIVING_MODE = "teenageDrivingMode"
        const val NAME_TEMPERATURE_UNIT_HU = "temperatureUnitHu"
        const val NAME_TEMPERATURE_POINT_FRONT_LEFT = "temperaturePointFrontLeft"
        const val NAME_TEMPERATURE_POINT_FRONT_CENTER = "temperaturePointFrontCenter"
        const val NAME_TEMPERATURE_POINT_FRONT_RIGHT = "temperaturePointFrontRight"
        const val NAME_TEMPERATURE_POINT_REAR_LEFT = "temperaturePointRearLeft"
        const val NAME_TEMPERATURE_POINT_REAR_CENTER = "temperaturePointRearCenter"
        const val NAME_TEMPERATURE_POINT_REAR_CENTER2 = "temperaturePointRearCenter2"
        const val NAME_TEMPERATURE_POINT_REAR_RIGHT = "temperaturePointRearRight"
        const val NAME_THEFT_ALARM_ACTIVE = "theftAlarmActive"
        const val NAME_THEFT_SYSTEM_ARMED = "theftSystemArmed"
        const val NAME_TIME_FORMAT_HU = "timeFormatHu"
        const val NAME_TIRE_PRESSURE_FRONT_LEFT = "tirePressureFrontLeft"
        const val NAME_TIRE_PRESSURE_FRONT_RIGHT = "tirePressureFrontRight"
        const val NAME_TIRE_PRESSURE_REAR_LEFT = "tirePressureRearLeft"
        const val NAME_TIRE_PRESSURE_REAR_RIGHT = "tirePressureRearRight"
        const val NAME_TIRE_LEVEL_PRW = "tireLevelPrw"
        const val NAME_TOW_PROTECTION_SENSOR_STATUS = "towProtectionSensorStatus"
        const val NAME_TRACKING_STATE_HU = "trackingStateHu"
        const val NAME_VALET_DRIVING_MODE = "valetDrivingMode"
        const val NAME_VEHICLE_DATA_CONNECTION_STATE = "vehicleDataConnectionState"
        const val NAME_VEHICLE_LOCK_STATE = "vehicleLockState"
        const val NAME_VTIME = "vTime"
        const val NAME_WARNING_BRAKE_FLUID = "warningBrakeFluid"
        const val NAME_WARNING_BRAKE_LINING_WEAR = "warningBrakeLiningWear"
        const val NAME_WARNING_COOLANT_LEVEL_LOW = "warningCoolantLevelLow"
        const val NAME_WARNING_ENGINE_LIGHT = "warningEngineLight"
        const val NAME_WARNING_LOW_BATTERY = "warningLowBattery"
        const val NAME_WARNING_TIRE_LAMP = "warningTireLamp"
        const val NAME_WARNING_TIRE_SPRW = "warningTireSprw"
        const val NAME_WARNING_TIRE_SRDK = "warningTireSrdk"
        const val NAME_WARNING_WASH_WATER = "warningWashWater"
        const val NAME_WEEKDAY_TARIFF = "weekdayTariff"
        const val NAME_WEEKEND_TARIFF = "weekendTariff"
        const val NAME_WEEKLY_SET_HU = "weeklySetHU"
        const val NAME_WEEKLY_PROFILE = "weeklyProfile"
        const val NAME_WINDOW_STATE_FRONT_LEFT = "windowStateFrontLeft"
        const val NAME_WINDOW_STATE_FRONT_RIGHT = "windowStateFrontRight"
        const val NAME_WINDOW_STATE_REAR_LEFT = "windowStateRearLeft"
        const val NAME_WINDOW_STATE_REAR_RIGHT = "windowStateRearRight"
        const val NAME_WINDOW_STATE_OVERALL = "windowStateOverall"
        const val NAME_WINDOW_STATE_FLIP = "flipWindowStatus"
        const val NAME_WINDOW_REAR_BLIND_STATE = "windowStatusRearBlind"
        const val NAME_WINDOW_REAR_LEFT_BLIND_STATE = "windowStatusRearLeftBlind"
        const val NAME_WINDOW_REAR_RIGHT_BLIND_STATE = "windowStatusRearRightBlind"
        const val NAME_ZEV_ACTIVE = "active"
        const val NAME_CHARGING_POWER = "chargingPower"
        const val NAME_DEPARTURE_TIME = "departureTime"
        const val NAME_DEPARTURE_TIME_MODE = "departureTimeMode"
        const val NAME_DEPARTURE_TIME_SOC = "departureTimeSoc"
        const val NAME_DEPARTURE_TIME_WEEKDAY = "departureTimeWeekday"
        const val NAME_END_OF_CHARGE_TIME = "endOfChargeTime"
        const val NAME_END_OF_CHARGE_TIME_RELATIVE = "endOfChargeTimeRelative"
        const val NAME_END_OF_CHARGE_TIME_WEEKDAY = "endofChargeTimeWeekday"
        const val NAME_MAX_RANGE = "maxRange"
        const val NAME_PRECOND_ACTIVE = "precondActive"
        const val NAME_PRECOND_AT_DEPARTURE = "precondAtDeparture"
        const val NAME_PRECOND_AT_DEPARTURE_DISABLE = "precondAtDepartureDisable"
        const val NAME_PRECOND_DURATION = "precondDuration"
        const val NAME_PRECOND_ERROR = "precondError"
        const val NAME_PRECOND_NOW = "precondNow"
        const val NAME_PRECOND_NOW_ERROR = "precondNowError"
        const val NAME_PRECOND_SEAT_FRONT_RIGHT = "precondSeatFrontRight"
        const val NAME_PRECOND_SEAT_FRONT_LEFT = "precondSeatFrontLeft"
        const val NAME_PRECOND_SEAT_REAR_RIGHT = "precondSeatRearRight"
        const val NAME_PRECOND_SEAT_REAR_LEFT = "precondSeatRearLeft"
        const val NAME_SOC_PROFILE = "socProfile"
        const val NAME_CHARGING_PROGRAMS = "chargePrograms"
        const val NAME_BIDIRECTIONAL_CHARGING_ACTIVE = "bidirectionalChargingActive"
        const val NAME_MIN_SOC = "minSOC"
        const val NAME_AC_CHARGING_CURRENT_LIMITATION = "acChargingCurrentLimitation"
        const val NAME_CHARGING_ERROR_INFRASTRUCTURE = "chargingErrorInfrastructure"
        const val NAME_CHARGING_TIME_TYPE = "chargingTimeType"
        const val NAME_MIN_SOC_LOWER_LIMIT = "minSocLowerLimit"
        const val NAME_NEXT_DEPARTURE_TIME = "nextDepartureTime"
        const val NAME_NEXT_DEPARTURE_TIME_WEEKDAY = "nextDepartureTimeWeekday"
        const val NAME_DEPARTURE_TIME_ICON = "departureTimeIcon"
        const val NAME_CHARGING_ERROR_WIM = "chargingErrorWim"
        const val NAME_MAX_SOC_UPPER_LIMIT = "maxSocUpperLimit"
        const val NAME_MIN_SOC_UPPER_LIMIT = "minSocUpperLimit"
        const val NAME_CHARGING_POWER_ECO_LIMIT = "chargingPowerEcoLimit"
        const val NAME_CHARGE_FLAP_DC_STATUS = "chargeFlapDCStatus"
        const val NAME_CHARGE_FLAP_AC_STATUS = "chargeFlapACStatus"
        const val NAME_CHARGE_COUPLER_DC_LOCK_STATUS = "chargeCouplerDCLockStatus"
        const val NAME_CHARGE_COUPLER_AC_LOCK_STATUS = "chargeCouplerACLockStatus"
        const val NAME_CHARGE_COUPLER_DC_STATUS = "chargeCouplerDCStatus"
        const val NAME_CHARGE_COUPLER_AC_STATUS = "chargeCouplerACStatus"
        const val NAME_EV_RANGE_ASSIST_DRIVE_ON_TIME = "evRangeAssistDriveOnTime"
        const val NAME_EV_RANGE_ASSIST_DRIVE_ON_SOC = "evRangeAssistDriveOnSOC"
        const val NAME_CHARGING_BREAK_CLOCK_TIMER = "chargingBreakClockTimer"
        const val NAME_CHARGING_POWER_CONTROL = "chargingPowerControl"
        const val NAME_LAST_THEFT_WARNING_REASON = "lastTheftWarningReason"
        const val NAME_LAST_THEFT_WARNING = "lastTheftWarning"
        const val NAME_TIRE_MARKER_FRONT_LEFT = "tireMarkerFrontLeft"
        const val NAME_TIRE_MARKER_FRONT_RIGHT = "tireMarkerFrontRight"
        const val NAME_TIRE_MARKER_REAR_LEFT = "tireMarkerRearLeft"
        const val NAME_TIRE_MARKER_REAR_RIGHT = "tireMarkerRearRight"
        const val NAME_TIRE_PRESSURE_MEASUREMENT_TIMESTAMP = "tirePressureMeasurementTimestamp"
        const val NAME_TIRE_SENSOR_AVAILABLE = "tireSensorAvailable"
        const val NAME_TIRE_WARNING_LEVEL_PRW = "tireWarningLevelPrw"
    }

    //region VehicleStatusCache
    override fun update(finOrVin: String, vehicleStatusUpdate: VehicleStatusUpdate): VehicleStatus {
        var realmVehicleState = realm.where<RealmVehicleState>().equalTo(FIELD_VIN_OR_FIN, finOrVin).findFirst()
        realm.executeTransaction {
            if (realmVehicleState == null) {
                MBLoggerKit.d("Create vehicle status update for $finOrVin in Realm")
                realmVehicleState = realm.createObject(finOrVin)
            } else {
                MBLoggerKit.d("Update vehicle status update for $finOrVin in Realm. FullUpdate=${vehicleStatusUpdate.fullUpdate}")
                if (vehicleStatusUpdate.fullUpdate) {
                    realmVehicleState?.cascadeDeleteFromRealm()
                    realmVehicleState = it.where<RealmVehicleState>().equalTo(FIELD_VIN_OR_FIN, finOrVin).findFirst() ?: it.createObject(finOrVin)
                }
            }
            realmVehicleState = realmVehicleState?.apply {
                auxheatActiveState = mapVehicleAttributeToDbIntAttribute(NAME_AUXHEAT_ACTIVE_STATE, vehicleStatusUpdate.auxheatActiveState, auxheatActiveState)
                auxHeatRuntime = mapVehicleAttributeToDbIntAttribute(NAME_AUXHEAT_RUNTIME, vehicleStatusUpdate.auxHeatRuntime, auxHeatRuntime)
                auxheatState = mapVehicleAttributeToDbIntAttribute(NAME_AUXHEAT_STATE, vehicleStatusUpdate.auxheatState, auxheatState)
                auxheatTime1 = mapVehicleAttributeToDbIntAttribute(NAME_AUXHEAT_TIME1, vehicleStatusUpdate.auxheatTime1, auxheatTime1)
                auxheatTime2 = mapVehicleAttributeToDbIntAttribute(NAME_AUXHEAT_TIME2, vehicleStatusUpdate.auxheatTime2, auxheatTime2)
                auxheatTime3 = mapVehicleAttributeToDbIntAttribute(NAME_AUXHEAT_TIME3, vehicleStatusUpdate.auxheatTime3, auxheatTime3)
                auxheatTimeSelection = mapVehicleAttributeToDbIntAttribute(NAME_AUXHEAT_TIME_SELECTION, vehicleStatusUpdate.auxheatTimeSelection, auxheatTimeSelection)
                auxheatWarnings = mapVehicleAttributeToDbIntAttribute(NAME_AUXHEAT_WARNINGS, vehicleStatusUpdate.auxheatWarnings, auxheatWarnings)
                averageSpeedReset = mapVehicleAttributeToDbDoubleAttribute(NAME_AVG_SPEED_RESET, vehicleStatusUpdate.averageSpeedReset, averageSpeedReset)
                averageSpeedStart = mapVehicleAttributeToDbDoubleAttribute(NAME_AVG_SPEED_START, vehicleStatusUpdate.averageSpeedStart, averageSpeedStart)
                batteryState = mapVehicleAttributeToDbIntAttribute(NAME_BATTERY_STATE, vehicleStatusUpdate.batteryState, batteryState)
                chargingActive = mapVehicleAttributeToDbIntAttribute(NAME_CHARGING_ACTIVE, vehicleStatusUpdate.zevUpdate.chargingActive, chargingActive)
                chargingError = mapVehicleAttributeToDbIntAttribute(NAME_CHARGING_ERROR, vehicleStatusUpdate.zevUpdate.chargingError, chargingError)
                chargingMode = mapVehicleAttributeToDbIntAttribute(NAME_CHARGING_MODE, vehicleStatusUpdate.zevUpdate.chargingMode, chargingMode)
                chargingStatus = mapVehicleAttributeToDbIntAttribute(NAME_CHARGING_STATUS, vehicleStatusUpdate.zevUpdate.chargingStatus, chargingStatus)
                clientMessageData = mapVehicleAttributeToDbDoubleAttribute(NAME_CLIENT_MESSAGE_DATA, vehicleStatusUpdate.clientMessageData, clientMessageData)
                decklidLockState = mapVehicleAttributeToDbIntAttribute(NAME_DECKLID_LOCK_STATE, vehicleStatusUpdate.decklidLockState, decklidLockState)
                decklidState = mapVehicleAttributeToDbIntAttribute(NAME_DECKLID_STATE, vehicleStatusUpdate.decklidState, decklidState)
                distanceElectricalReset = mapVehicleAttributeToDbDoubleAttribute(NAME_DISTANCE_ELETRICAL_RESET, vehicleStatusUpdate.distanceElectricalReset, distanceElectricalReset)
                distanceElectricalStart = mapVehicleAttributeToDbDoubleAttribute(NAME_DISTANCE_ELETRICAL_START, vehicleStatusUpdate.distanceElectricalStart, distanceElectricalStart)
                distanceGasReset = mapVehicleAttributeToDbDoubleAttribute(NAME_DISTANCE_GAS_RESET, vehicleStatusUpdate.distanceGasReset, distanceGasReset)
                distanceGasStart = mapVehicleAttributeToDbDoubleAttribute(NAME_DISTANCE_GAS_START, vehicleStatusUpdate.distanceGasStart, distanceGasStart)
                distanceReset = mapVehicleAttributeToDbDoubleAttribute(NAME_DISTANCE_RESET, vehicleStatusUpdate.distanceReset, distanceReset)
                distanceStart = mapVehicleAttributeToDbDoubleAttribute(NAME_DISTANCE_START, vehicleStatusUpdate.distanceStart, distanceStart)
                distanceZeReset = mapVehicleAttributeToDbIntAttribute(NAME_DISTANCE_ZE_RESET, vehicleStatusUpdate.distanceZeReset, distanceZeReset)
                distanceZeStart = mapVehicleAttributeToDbIntAttribute(NAME_DISTANCE_ZE_START, vehicleStatusUpdate.distanceZeStart, distanceZeStart)
                doorLockStateFrontLeft = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_LOCK_STATE_FRONT_LEFT, vehicleStatusUpdate.doorLockStateFrontLeft, doorLockStateFrontLeft)
                doorStateFrontLeft = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_STATE_FRONT_LEFT, vehicleStatusUpdate.doorStateFrontLeft, doorStateFrontLeft)
                doorLockStateFrontRight = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_LOCK_STATE_FRONT_RIGHT, vehicleStatusUpdate.doorLockStateFrontRight, doorLockStateFrontRight)
                doorStateFrontRight = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_STATE_FRONT_RIGHT, vehicleStatusUpdate.doorStateFrontRight, doorStateFrontRight)
                doorLockStateGas = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_LOCK_STATE_GAS, vehicleStatusUpdate.doorLockStateGas, doorLockStateGas)
                doorLockStateRearLeft = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_LOCK_STATE_REAR_LEFT, vehicleStatusUpdate.doorLockStateRearLeft, doorLockStateRearLeft)
                doorStateRearLeft = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_STATE_REAR_LEFT, vehicleStatusUpdate.doorStateRearLeft, doorStateRearLeft)
                doorLockStateRearRight = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_LOCK_STATE_REAR_RIGHT, vehicleStatusUpdate.doorLockStateRearRight, doorLockStateRearRight)
                doorStateRearRight = mapVehicleAttributeToDbIntAttribute(NAME_DOOR_STATE_REAR_RIGHT, vehicleStatusUpdate.doorStateRearRight, doorStateRearRight)
                drivenTimeReset = mapVehicleAttributeToDbIntAttribute(NAME_DRIVEN_TIME_RESET, vehicleStatusUpdate.drivenTimeReset, drivenTimeReset)
                drivenTimeStart = mapVehicleAttributeToDbIntAttribute(NAME_DRIVEN_TIME_START, vehicleStatusUpdate.drivenTimeStart, drivenTimeStart)
                drivenTimeZEReset = mapVehicleAttributeToDbIntAttribute(NAME_DRIVEN_TIME_ZE_RESET, vehicleStatusUpdate.drivenTimeZEReset, drivenTimeZEReset)
                drivenTimeZEStart = mapVehicleAttributeToDbIntAttribute(NAME_DRIVEN_TIME_ZE_START, vehicleStatusUpdate.drivenTimeZEStart, drivenTimeZEStart)
                ecoScoreAccel = mapVehicleAttributeToDbIntAttribute(NAME_ECO_SCORE_ACCEL, vehicleStatusUpdate.ecoScoreAccel, ecoScoreAccel)
                ecoScoreBonusRange = mapVehicleAttributeToDbDoubleAttribute(NAME_ECO_SCORE_BONUS_RANGE, vehicleStatusUpdate.ecoScoreBonusRange, ecoScoreBonusRange)
                ecoScoreConst = mapVehicleAttributeToDbIntAttribute(NAME_ECO_SCORE_CONST, vehicleStatusUpdate.ecoScoreConst, ecoScoreConst)
                ecoScoreFreeWhl = mapVehicleAttributeToDbIntAttribute(NAME_ECO_SCORE_FREEWHL, vehicleStatusUpdate.ecoScoreFreeWhl, ecoScoreFreeWhl)
                ecoScoreTotal = mapVehicleAttributeToDbIntAttribute(NAME_ECO_SCORE_TOTAL, vehicleStatusUpdate.ecoScoreTotal, ecoScoreTotal)
                electricConsumptionReset = mapVehicleAttributeToDbDoubleAttribute(NAME_ELECTRIC_CONSUMPTION_RESET, vehicleStatusUpdate.electricConsumptionReset, electricConsumptionReset)
                electricConsumptionStart = mapVehicleAttributeToDbDoubleAttribute(NAME_ELECTRIC_CONSUMPTION_START, vehicleStatusUpdate.electricConsumptionStart, electricConsumptionStart)
                electricalRangeSkipIndication = mapVehicleAttributeToDbIntAttribute(NAME_ELECTRIC_RANGE_SKIP_INDICATION, vehicleStatusUpdate.electricalRangeSkipIndication, electricalRangeSkipIndication)
                engineState = mapVehicleAttributeToDbIntAttribute(NAME_ENGINE_STATE, vehicleStatusUpdate.engineState, engineState)
                engineHoodStatus = mapVehicleAttributeToDbIntAttribute(NAME_ENGINE_HOOD_STATUS, vehicleStatusUpdate.engineHoodStatus, engineHoodStatus)
                eventTimeStamp = vehicleStatusUpdate.eventTimeStamp
                filterParticelState = mapVehicleAttributeToDbIntAttribute(NAME_FILTER_PARTICEL_STATE, vehicleStatusUpdate.filterParticelState, filterParticelState)
                gasConsumptionReset = mapVehicleAttributeToDbDoubleAttribute(NAME_GAS_CONSUMPTION_RESET, vehicleStatusUpdate.gasConsumptionReset, gasConsumptionReset)
                gasConsumptionStart = mapVehicleAttributeToDbDoubleAttribute(NAME_GAS_CONSUMPTION_START, vehicleStatusUpdate.gasConsumptionStart, gasConsumptionStart)
                healthStatus = mapVehicleAttributeToDbIntAttribute(NAME_HEALT_STATUS, vehicleStatusUpdate.healthStatus, healthStatus)
                hybridWarnings = mapVehicleAttributeToDbIntAttribute(NAME_HYBRID_WARNINGS, vehicleStatusUpdate.zevUpdate.hybridWarnings, hybridWarnings)
                ignitionState = mapVehicleAttributeToDbIntAttribute(NAME_IGNITION_STATE, vehicleStatusUpdate.ignitionState, ignitionState)
                interiorProtectionStatus = mapVehicleAttributeToDbIntAttribute(NAME_INTERIOR_PROT_STATE, vehicleStatusUpdate.interiorProtectionStatus, interiorProtectionStatus)
                keyActivationState = mapVehicleAttributeToDbIntAttribute(NAME_KEY_ACTIVATION_STATE, vehicleStatusUpdate.keyActivationState, keyActivationState)
                languageHu = mapVehicleAttributeToDbIntAttribute(NAME_LANGUAGE_HU, vehicleStatusUpdate.languageHu, languageHu)
                lastParkEvent = mapVehicleAttributeToDbLongAttribute(NAME_LAST_PARK_EVENT, vehicleStatusUpdate.lastParkEvent, lastParkEvent)
                lastParkEventNotConfirmed = mapVehicleAttributeToDbIntAttribute(NAME_LAST_PARK_EVENT_NOT_CONFIRMED, vehicleStatusUpdate.lastParkEventNotConfirmed, lastParkEventNotConfirmed)
                parkEventSensorStatus = mapVehicleAttributeToDbIntAttribute(NAME_PARK_EVENT_SENSOR_STATUS, vehicleStatusUpdate.parkEventSensorStatus, parkEventSensorStatus)
                liquidConsumptionReset = mapVehicleAttributeToDbDoubleAttribute(NAME_LIQUID_CONSUMPTION_RESET, vehicleStatusUpdate.liquidConsumptionReset, liquidConsumptionReset)
                liquidConsumptionStart = mapVehicleAttributeToDbDoubleAttribute(NAME_LIQUID_CONSUMPTION_START, vehicleStatusUpdate.liquidConsumptionStart, liquidConsumptionStart)
                liquidRangeSkipIndication = mapVehicleAttributeToDbIntAttribute(NAME_LIQUID_CONSUMPTION_SKIP_INDICATION, vehicleStatusUpdate.liquidRangeSkipIndication, liquidRangeSkipIndication)
                lockStatusOverall = mapVehicleAttributeToDbIntAttribute(NAME_LOCK_STATE_OVERALL, vehicleStatusUpdate.lockStateOverall, lockStatusOverall)
                maxSoc = mapVehicleAttributeToDbIntAttribute(NAME_MAX_SOC, vehicleStatusUpdate.zevUpdate.maxSoc, maxSoc)
                maxSocLowerLimit = mapVehicleAttributeToDbIntAttribute(NAME_MAX_SOC_LOWER_LIMIT, vehicleStatusUpdate.zevUpdate.maxSocLowerLimit, maxSocLowerLimit)
                odo = mapVehicleAttributeToDbIntAttribute(NAME_ODO, vehicleStatusUpdate.odo, odo)
                overallRange = mapVehicleAttributeToDbDoubleAttribute(NAME_OVERALL_RANGE, vehicleStatusUpdate.overallRange, overallRange)
                parkBrakeState = mapVehicleAttributeToDbIntAttribute(NAME_PARK_BRAKE_STATE, vehicleStatusUpdate.parkBrakeState, parkBrakeState)
                parkEventLevel = mapVehicleAttributeToDbIntAttribute(NAME_PARK_EVENT_LEVEL, vehicleStatusUpdate.parkEventLevel, parkEventLevel)
                parkEventType = mapVehicleAttributeToDbIntAttribute(NAME_PARK_EVENT_TYPE, vehicleStatusUpdate.parkEventType, parkEventType)
                positionHeading = mapVehicleAttributeToDbDoubleAttribute(NAME_POSTION_HEADING, vehicleStatusUpdate.positionHeading, positionHeading)
                positionLat = mapVehicleAttributeToDbDoubleAttribute(NAME_POSITION_LAT, vehicleStatusUpdate.positionLat, positionLat)
                positionLong = mapVehicleAttributeToDbDoubleAttribute(NAME_POSITION_LONG, vehicleStatusUpdate.positionLong, positionLong)
                positionErrorCode = mapVehicleAttributeToDbIntAttribute(NAME_POSITION_ERROR_CODE, vehicleStatusUpdate.positionErrorCode, positionErrorCode)
                proximityRequiredForLocation = mapVehicleAttributeToDbIntAttribute(NAME_PROXIMITY_CALCULATION_POSITION_REQUIRED, vehicleStatusUpdate.proximityCalculationRequiredForVehicleLocation, proximityRequiredForLocation)
                remoteStartActive = mapVehicleAttributeToDbIntAttribute(NAME_REMOTE_START_ACTIVE, vehicleStatusUpdate.remoteStartActive, remoteStartActive)
                remoteStartEndtime = mapVehicleAttributeToDbLongAttribute(NAME_REMOTE_START_ENDTIME, vehicleStatusUpdate.remoteStartEndtime, remoteStartEndtime)
                remoteStartTemperature = mapVehicleAttributeToDbDoubleAttribute(NAME_REMOTE_START_TEMPERATURE, vehicleStatusUpdate.remoteStartTemperature, remoteStartTemperature)
                rooftopState = mapVehicleAttributeToDbIntAttribute(NAME_ROOF_TOP_STATE, vehicleStatusUpdate.rooftopState, rooftopState)
                selectedChargeProgram = mapVehicleAttributeToDbIntAttribute(NAME_SELECTED_CHARGE_PROGRAM, vehicleStatusUpdate.zevUpdate.selectedChargeProgram, selectedChargeProgram)
                serviceIntervalDays = mapVehicleAttributeToDbIntAttribute(NAME_SERVICE_INTERVAL_DAYS, vehicleStatusUpdate.serviceIntervalDays, serviceIntervalDays)
                serviceIntervalDistance = mapVehicleAttributeToDbIntAttribute(NAME_SERVICE_INTERVAL_DISTANCE, vehicleStatusUpdate.serviceIntervalDistance, serviceIntervalDistance)
                smartCharging = mapVehicleAttributeToDbIntAttribute(NAME_SMART_CHARGING, vehicleStatusUpdate.zevUpdate.smartCharging, smartCharging)
                smartChargingDeparture = mapVehicleAttributeToDbIntAttribute(NAME_SMART_CHARGING_AT_DEPARTURE, vehicleStatusUpdate.zevUpdate.smartChargingAtDeparture, smartChargingDeparture)
                smartChargingDeparture2 = mapVehicleAttributeToDbIntAttribute(NAME_SMART_CHARGING_AT_DEPARTURE2, vehicleStatusUpdate.zevUpdate.smartChargingAtDeparture2, smartChargingDeparture2)
                soc = mapVehicleAttributeToDbIntAttribute(NAME_SOC, vehicleStatusUpdate.soc, soc)
                speedAlertConfiguration = mapVehicleAttributeToDbSpeedAlertConfigurationAttribute(NAME_SPEED_ALERT_CONFIGURATION, vehicleStatusUpdate.speedAlertConfiguration, speedAlertConfiguration)
                speedUnitFromIc = mapVehicleAttributeToDbIntAttribute(NAME_SPEED_UNIT_FROM_IC, vehicleStatusUpdate.speedUnitFromIc, speedUnitFromIc)
                stateOverall = mapVehicleAttributeToDbIntAttribute(NAME_STATE_OVERALL, vehicleStatusUpdate.stateOverall, stateOverall)
                sunroofEventState = mapVehicleAttributeToDbIntAttribute(NAME_SUNROOF_EVENT_STATE, vehicleStatusUpdate.sunroofEventState, sunroofEventState)
                sunroofEventActive = mapVehicleAttributeToDbIntAttribute(NAME_SUNROOF_EVENT_ACTIVE, vehicleStatusUpdate.sunroofEventActive, sunroofEventActive)
                sunroofState = mapVehicleAttributeToDbIntAttribute(NAME_SUNROOF_STATE, vehicleStatusUpdate.sunroofState, sunroofState)
                sunroofStatusFrontBlind = mapVehicleAttributeToDbIntAttribute(NAME_SUNROOF_BLIND_FRONT_STATE, vehicleStatusUpdate.sunroofStatusFrontBlind, sunroofStatusFrontBlind)
                sunroofStatusRearBlind = mapVehicleAttributeToDbIntAttribute(NAME_SUNROOF_BLIND_REAR_STATE, vehicleStatusUpdate.sunroofStatusRearBlind, sunroofStatusRearBlind)
                tankAdBlueLevel = mapVehicleAttributeToDbIntAttribute(NAME_TANK_AD_BLUE_LEVEL, vehicleStatusUpdate.tankAdBlueLevel, tankAdBlueLevel)
                tankElectricalLevel = mapVehicleAttributeToDbIntAttribute(NAME_TANK_ELECTRICAL_LEVEL, vehicleStatusUpdate.tankElectricalLevel, tankElectricalLevel)
                tankElectricalRange = mapVehicleAttributeToDbIntAttribute(NAME_TANK_ELETRICAL_RANGE, vehicleStatusUpdate.tankElectricalRange, tankElectricalRange)
                tankGasLevel = mapVehicleAttributeToDbDoubleAttribute(NAME_TANK_GAS_LEVEL, vehicleStatusUpdate.tankGasLevel, tankGasLevel)
                tankGasRange = mapVehicleAttributeToDbDoubleAttribute(NAME_TANK_GAS_RANGE, vehicleStatusUpdate.tankGasRange, tankGasRange)
                tankLiquidLevel = mapVehicleAttributeToDbIntAttribute(NAME_TANK_LIQUID_LEVEL, vehicleStatusUpdate.tankLiquidLevel, tankLiquidLevel)
                tankLiquidRange = mapVehicleAttributeToDbIntAttribute(NAME_TANK_LIQUID_RANGE, vehicleStatusUpdate.tankLiquidRange, tankLiquidRange)
                teenageDrivingMode = mapVehicleAttributeToDbIntAttribute(NAME_TEENAGE_DRIVING_MODE, vehicleStatusUpdate.teenageDrivingMode, teenageDrivingMode)
                temperatureUnitHu = mapVehicleAttributeToDbIntAttribute(NAME_TEMPERATURE_UNIT_HU, vehicleStatusUpdate.temperatureUnitHu, temperatureUnitHu)
                temperaturePointFrontLeft = mapVehicleAttributeToDbDoubleAttribute(NAME_TEMPERATURE_POINT_FRONT_LEFT, vehicleStatusUpdate.zevUpdate.temperaturePointFrontLeft, temperaturePointFrontLeft)
                temperaturePointFrontCenter = mapVehicleAttributeToDbDoubleAttribute(NAME_TEMPERATURE_POINT_FRONT_CENTER, vehicleStatusUpdate.zevUpdate.temperaturePointFrontCenter, temperaturePointFrontCenter)
                temperaturePointFrontRight = mapVehicleAttributeToDbDoubleAttribute(NAME_TEMPERATURE_POINT_FRONT_RIGHT, vehicleStatusUpdate.zevUpdate.temperaturePointFrontRight, temperaturePointFrontRight)
                temperaturePointRearLeft = mapVehicleAttributeToDbDoubleAttribute(NAME_TEMPERATURE_POINT_REAR_LEFT, vehicleStatusUpdate.zevUpdate.temperaturePointRearLeft, temperaturePointRearLeft)
                temperaturePointRearCenter = mapVehicleAttributeToDbDoubleAttribute(NAME_TEMPERATURE_POINT_REAR_CENTER, vehicleStatusUpdate.zevUpdate.temperaturePointRearCenter, temperaturePointRearCenter)
                temperaturePointRearCenter2 = mapVehicleAttributeToDbDoubleAttribute(NAME_TEMPERATURE_POINT_REAR_CENTER2, vehicleStatusUpdate.zevUpdate.temperaturePointRearCenter2, temperaturePointRearCenter2)
                temperaturePointRearRight = mapVehicleAttributeToDbDoubleAttribute(NAME_TEMPERATURE_POINT_REAR_RIGHT, vehicleStatusUpdate.zevUpdate.temperaturePointRearRight, temperaturePointRearRight)
                theftAlarmActive = mapVehicleAttributeToDbIntAttribute(NAME_THEFT_ALARM_ACTIVE, vehicleStatusUpdate.theftAlarmActive, theftAlarmActive)
                theftSystemArmed = mapVehicleAttributeToDbIntAttribute(NAME_THEFT_SYSTEM_ARMED, vehicleStatusUpdate.theftSystemArmed, theftSystemArmed)
                timeFormatHu = mapVehicleAttributeToDbIntAttribute(NAME_TIME_FORMAT_HU, vehicleStatusUpdate.timeFormatHu, timeFormatHu)
                tirePressureFrontLeft = mapVehicleAttributeToDbDoubleAttribute(NAME_TIRE_PRESSURE_FRONT_LEFT, vehicleStatusUpdate.tirePressureFrontLeft, tirePressureFrontLeft)
                tirePressureFrontRight = mapVehicleAttributeToDbDoubleAttribute(NAME_TIRE_PRESSURE_FRONT_RIGHT, vehicleStatusUpdate.tirePressureFrontRight, tirePressureFrontRight)
                tirePressureRearLeft = mapVehicleAttributeToDbDoubleAttribute(NAME_TIRE_PRESSURE_REAR_LEFT, vehicleStatusUpdate.tirePressureRearLeft, tirePressureRearLeft)
                tirePressureRearRight = mapVehicleAttributeToDbDoubleAttribute(NAME_TIRE_PRESSURE_REAR_RIGHT, vehicleStatusUpdate.tirePressureRearRight, tirePressureRearRight)
                tireMarkerFrontLeft = mapVehicleAttributeToDbIntAttribute(NAME_TIRE_MARKER_FRONT_LEFT, vehicleStatusUpdate.tireMarkerFrontLeft, tireMarkerFrontLeft)
                tireMarkerFrontRight = mapVehicleAttributeToDbIntAttribute(NAME_TIRE_MARKER_FRONT_RIGHT, vehicleStatusUpdate.tireMarkerFrontRight, tireMarkerFrontRight)
                tireMarkerRearLeft = mapVehicleAttributeToDbIntAttribute(NAME_TIRE_MARKER_REAR_LEFT, vehicleStatusUpdate.tireMarkerRearLeft, tireMarkerRearLeft)
                tireMarkerRearRight = mapVehicleAttributeToDbIntAttribute(NAME_TIRE_MARKER_REAR_RIGHT, vehicleStatusUpdate.tireMarkerRearRight, tireMarkerRearRight)
                tirePressureMeasurementTimestamp = mapVehicleAttributeToDbLongAttribute(NAME_TIRE_PRESSURE_MEASUREMENT_TIMESTAMP, vehicleStatusUpdate.tirePressureMeasurementTimestamp, tirePressureMeasurementTimestamp)
                tireSensorAvailable = mapVehicleAttributeToDbIntAttribute(NAME_TIRE_SENSOR_AVAILABLE, vehicleStatusUpdate.tireSensorAvailable, tireSensorAvailable)
                tireLevelPrw = mapVehicleAttributeToDbIntAttribute(NAME_TIRE_LEVEL_PRW, vehicleStatusUpdate.tireLevelPrw, tireLevelPrw)
                towProtectionSensorStatus = mapVehicleAttributeToDbIntAttribute(NAME_TOW_PROTECTION_SENSOR_STATUS, vehicleStatusUpdate.towProtectionSensorStatus, towProtectionSensorStatus)
                trackingStateHu = mapVehicleAttributeToDbIntAttribute(NAME_TRACKING_STATE_HU, vehicleStatusUpdate.trackingStateHu, trackingStateHu)
                valetDrivingMode = mapVehicleAttributeToDbIntAttribute(NAME_VALET_DRIVING_MODE, vehicleStatusUpdate.valetDrivingMode, valetDrivingMode)
                vehicleDataConnectionState = mapVehicleAttributeToDbIntAttribute(NAME_VEHICLE_DATA_CONNECTION_STATE, vehicleStatusUpdate.vehicleDataConnectionState, vehicleDataConnectionState)
                vehicleLockState = mapVehicleAttributeToDbIntAttribute(NAME_VEHICLE_LOCK_STATE, vehicleStatusUpdate.vehicleLockState, vehicleLockState)
                vTime = mapVehicleAttributeToDbIntAttribute(NAME_VTIME, vehicleStatusUpdate.vTime, vTime)
                warningBrakeFluid = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_BRAKE_FLUID, vehicleStatusUpdate.warningBrakeFluid, warningBrakeFluid)
                warningBrakeLiningWear = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_BRAKE_LINING_WEAR, vehicleStatusUpdate.warningBrakeLiningWear, warningBrakeLiningWear)
                warningCoolantLevelLow = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_COOLANT_LEVEL_LOW, vehicleStatusUpdate.warningCoolantLevelLow, warningCoolantLevelLow)
                warningEngineLight = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_ENGINE_LIGHT, vehicleStatusUpdate.warningEngineLight, warningEngineLight)
                warningLowBattery = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_LOW_BATTERY, vehicleStatusUpdate.warningLowBattery, warningLowBattery)
                warningTireLamp = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_TIRE_LAMP, vehicleStatusUpdate.warningTireLamp, warningTireLamp)
                warningTireSprw = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_TIRE_SPRW, vehicleStatusUpdate.warningTireSprw, warningTireSprw)
                warningTireSrdk = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_TIRE_SRDK, vehicleStatusUpdate.warningTireSrdk, warningTireSrdk)
                warningWashWater = mapVehicleAttributeToDbIntAttribute(NAME_WARNING_WASH_WATER, vehicleStatusUpdate.warningWashWater, warningWashWater)
                weekdayTariff = mapVehicleAttributeToDbTariffAttribute(NAME_WEEKDAY_TARIFF, vehicleStatusUpdate.zevUpdate.weekdayTariff, weekdayTariff)
                weekendTariff = mapVehicleAttributeToDbTariffAttribute(NAME_WEEKEND_TARIFF, vehicleStatusUpdate.zevUpdate.weekendTariff, weekendTariff)
                weeklySetHU = mapVehicleAttributeToDbWeeklySetAttribute(NAME_WEEKLY_SET_HU, vehicleStatusUpdate.weeklySetHU, weeklySetHU)
                weeklyProfile = mapVehicleAttributeToDbWeeklyProfileAttribute(NAME_WEEKLY_PROFILE, vehicleStatusUpdate.weeklyProfile, weeklyProfile)
                windowStateFrontLeft = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_STATE_FRONT_LEFT, vehicleStatusUpdate.windowStateFrontLeft, windowStateFrontLeft)
                windowStateFrontRight = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_STATE_FRONT_RIGHT, vehicleStatusUpdate.windowStateFrontRight, windowStateFrontRight)
                windowStateRearLeft = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_STATE_REAR_LEFT, vehicleStatusUpdate.windowStateRearLeft, windowStateRearLeft)
                windowStateRearRight = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_STATE_REAR_RIGHT, vehicleStatusUpdate.windowStateRearRight, windowStateRearRight)
                windowStateOverall = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_STATE_OVERALL, vehicleStatusUpdate.windowStateOverall, windowStateOverall)
                windowStatusRearBlind = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_REAR_BLIND_STATE, vehicleStatusUpdate.windowStatusRearBlind, windowStatusRearBlind)
                windowStatusRearLeftBlind = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_REAR_LEFT_BLIND_STATE, vehicleStatusUpdate.windowStatusRearLeftBlind, windowStatusRearLeftBlind)
                windowStatusRearRightBlind = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_REAR_RIGHT_BLIND_STATE, vehicleStatusUpdate.windowStatusRearRightBlind, windowStatusRearRightBlind)
                flipWindowStatus = mapVehicleAttributeToDbIntAttribute(NAME_WINDOW_STATE_FLIP, vehicleStatusUpdate.flipWindowStatus, flipWindowStatus)
                zevActive = mapVehicleAttributeToDbIntAttribute(NAME_ZEV_ACTIVE, vehicleStatusUpdate.zevUpdate.activeState, zevActive)
                chargingPower = mapVehicleAttributeToDbDoubleAttribute(NAME_CHARGING_POWER, vehicleStatusUpdate.zevUpdate.chargingPower, chargingPower)
                departureTime = mapVehicleAttributeToDbIntAttribute(NAME_DEPARTURE_TIME, vehicleStatusUpdate.zevUpdate.departureTime, departureTime)
                departureTimeMode = mapVehicleAttributeToDbIntAttribute(NAME_DEPARTURE_TIME_MODE, vehicleStatusUpdate.zevUpdate.departureTimeMode, departureTimeMode)
                departureTimeSoc = mapVehicleAttributeToDbIntAttribute(NAME_DEPARTURE_TIME_SOC, vehicleStatusUpdate.zevUpdate.departureTimeSoc, departureTimeSoc)
                departureTimeWeekday = mapVehicleAttributeToDbIntAttribute(NAME_DEPARTURE_TIME_WEEKDAY, vehicleStatusUpdate.zevUpdate.departureTimeWeekday, departureTimeWeekday)
                endOfChargeTime = mapVehicleAttributeToDbIntAttribute(NAME_END_OF_CHARGE_TIME, vehicleStatusUpdate.zevUpdate.endOfChargeTime, endOfChargeTime)
                endOfChargeTimeRelative = mapVehicleAttributeToDbIntAttribute(NAME_END_OF_CHARGE_TIME_RELATIVE, vehicleStatusUpdate.zevUpdate.endOfChargeTimeRelative, endOfChargeTimeRelative)
                endOfChargeTimeWeekday = mapVehicleAttributeToDbIntAttribute(NAME_END_OF_CHARGE_TIME_WEEKDAY, vehicleStatusUpdate.zevUpdate.endOfChargeTimeWeekday, endOfChargeTimeWeekday)
                maxRange = mapVehicleAttributeToDbIntAttribute(NAME_MAX_RANGE, vehicleStatusUpdate.zevUpdate.maxRange, maxRange)
                precondActive = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_ACTIVE, vehicleStatusUpdate.zevUpdate.precondActive, precondActive)
                precondAtDeparture = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_AT_DEPARTURE, vehicleStatusUpdate.zevUpdate.precondAtDeparture, precondAtDeparture)
                precondAtDepartureDisable = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_AT_DEPARTURE_DISABLE, vehicleStatusUpdate.zevUpdate.precondAtDepartureDisable, precondAtDepartureDisable)
                precondDuration = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_DURATION, vehicleStatusUpdate.zevUpdate.precondDuration, precondDuration)
                precondError = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_ERROR, vehicleStatusUpdate.zevUpdate.precondError, precondError)
                precondNow = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_NOW, vehicleStatusUpdate.zevUpdate.precondNow, precondNow)
                precondNowError = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_NOW_ERROR, vehicleStatusUpdate.zevUpdate.precondNowError, precondNowError)
                precondSeatFrontRight = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_SEAT_FRONT_RIGHT, vehicleStatusUpdate.zevUpdate.precondSeatFrontRight, precondSeatFrontRight)
                precondSeatFrontLeft = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_SEAT_FRONT_LEFT, vehicleStatusUpdate.zevUpdate.precondSeatFrontLeft, precondSeatFrontLeft)
                precondSeatRearRight = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_SEAT_REAR_RIGHT, vehicleStatusUpdate.zevUpdate.precondSeatRearRight, precondSeatRearRight)
                precondSeatRearLeft = mapVehicleAttributeToDbIntAttribute(NAME_PRECOND_SEAT_REAR_LEFT, vehicleStatusUpdate.zevUpdate.precondSeatRearLeft, precondSeatRearLeft)
                socprofile = mapVehicleAttributeToDbSocProfileAttribute(NAME_SOC_PROFILE, vehicleStatusUpdate.zevUpdate.socprofile, socprofile)
                chargingProgram = mapVehicleAttributeToDbChargingProgramAttribute(NAME_CHARGING_PROGRAMS, vehicleStatusUpdate.zevUpdate.chargingPrograms, chargingProgram)
                lastTheftWarningReason = mapVehicleAttributeToDbIntAttribute(NAME_LAST_THEFT_WARNING_REASON, vehicleStatusUpdate.lastTheftWarningReason, lastTheftWarningReason)
                lastTheftWarning = mapVehicleAttributeToDbLongAttribute(NAME_LAST_THEFT_WARNING, vehicleStatusUpdate.lastTheftWarning, lastTheftWarning)
                bidirectionalChargingActive = mapVehicleAttributeToDbIntAttribute(NAME_BIDIRECTIONAL_CHARGING_ACTIVE, vehicleStatusUpdate.zevUpdate.bidirectionalChargingActive, bidirectionalChargingActive)
                minSoc = mapVehicleAttributeToDbIntAttribute(NAME_MIN_SOC, vehicleStatusUpdate.zevUpdate.bidirectionalChargingActive, bidirectionalChargingActive)
                acChargingCurrentLimitation = mapVehicleAttributeToDbIntAttribute(NAME_AC_CHARGING_CURRENT_LIMITATION, vehicleStatusUpdate.zevUpdate.acChargingCurrentLimitation, acChargingCurrentLimitation)
                chargingErrorInfrastructure = mapVehicleAttributeToDbIntAttribute(NAME_CHARGING_ERROR_INFRASTRUCTURE, vehicleStatusUpdate.zevUpdate.chargingErrorInfrastructure, chargingErrorInfrastructure)
                chargingTimeType = mapVehicleAttributeToDbIntAttribute(NAME_CHARGING_TIME_TYPE, vehicleStatusUpdate.zevUpdate.chargingTimeType, chargingTimeType)
                minSocLowerLimit = mapVehicleAttributeToDbIntAttribute(NAME_MIN_SOC_LOWER_LIMIT, vehicleStatusUpdate.zevUpdate.minSocLowerLimit, minSocLowerLimit)
                nextDepartureTime = mapVehicleAttributeToDbIntAttribute(NAME_NEXT_DEPARTURE_TIME, vehicleStatusUpdate.zevUpdate.nextDepartureTime, nextDepartureTime)
                nextDepartureTimeWeekday = mapVehicleAttributeToDbIntAttribute(NAME_DEPARTURE_TIME_WEEKDAY, vehicleStatusUpdate.zevUpdate.nextDepartureTimeWeekday, nextDepartureTimeWeekday)
                departureTimeIcon = mapVehicleAttributeToDbIntAttribute(NAME_DEPARTURE_TIME_ICON, vehicleStatusUpdate.zevUpdate.departureTimeIcon, departureTimeIcon)
                chargingErrorWim = mapVehicleAttributeToDbIntAttribute(NAME_CHARGING_ERROR_WIM, vehicleStatusUpdate.zevUpdate.chargingErrorWim, chargingErrorWim)
                maxSocUpperLimit = mapVehicleAttributeToDbIntAttribute(NAME_MAX_SOC_UPPER_LIMIT, vehicleStatusUpdate.zevUpdate.maxSocUpperLimit, maxSocUpperLimit)
                minSocUpperLimit = mapVehicleAttributeToDbIntAttribute(NAME_MIN_SOC_UPPER_LIMIT, vehicleStatusUpdate.zevUpdate.minSocUpperLimit, minSocUpperLimit)
                chargingPowerEcoLimit = mapVehicleAttributeToDbIntAttribute(NAME_CHARGING_POWER_ECO_LIMIT, vehicleStatusUpdate.zevUpdate.chargingPowerEcoLimit, chargingPowerEcoLimit)
                chargeFlapDCStatus = mapVehicleAttributeToDbIntAttribute(NAME_CHARGE_FLAP_DC_STATUS, vehicleStatusUpdate.zevUpdate.chargeFlapDCStatus, chargeFlapDCStatus)
                chargeFlapACStatus = mapVehicleAttributeToDbIntAttribute(NAME_CHARGE_FLAP_AC_STATUS, vehicleStatusUpdate.zevUpdate.chargeFlapACStatus, chargeFlapACStatus)
                chargeCouplerDCLockStatus = mapVehicleAttributeToDbIntAttribute(NAME_CHARGE_COUPLER_DC_LOCK_STATUS, vehicleStatusUpdate.zevUpdate.chargeCouplerDCLockStatus, chargeCouplerDCLockStatus)
                chargeCouplerACLockStatus = mapVehicleAttributeToDbIntAttribute(NAME_CHARGE_COUPLER_AC_LOCK_STATUS, vehicleStatusUpdate.zevUpdate.chargeCouplerACLockStatus, chargeCouplerACLockStatus)
                chargeCouplerDCStatus = mapVehicleAttributeToDbIntAttribute(NAME_CHARGE_COUPLER_DC_STATUS, vehicleStatusUpdate.zevUpdate.chargeCouplerDCStatus, chargeCouplerDCStatus)
                chargeCouplerACStatus = mapVehicleAttributeToDbIntAttribute(NAME_CHARGE_COUPLER_AC_STATUS, vehicleStatusUpdate.zevUpdate.chargeCouplerACStatus, chargeCouplerACStatus)
                evRangeAssistDriveOnTime = mapVehicleAttributeToDbLongAttribute(NAME_EV_RANGE_ASSIST_DRIVE_ON_TIME, vehicleStatusUpdate.zevUpdate.evRangeAssistDriveOnTime, evRangeAssistDriveOnTime)
                evRangeAssistDriveOnSOC = mapVehicleAttributeToDbIntAttribute(NAME_EV_RANGE_ASSIST_DRIVE_ON_SOC, vehicleStatusUpdate.zevUpdate.evRangeAssistDriveOnSOC, evRangeAssistDriveOnSOC)
                chargingBreakClockTimer = mapVehicleAttributeToDbClockTimers(NAME_CHARGING_BREAK_CLOCK_TIMER, vehicleStatusUpdate.zevUpdate.vehicleChargingBreakClockTimers, chargingBreakClockTimer)
                chargingPowerControl = mapVehicleAttributeToDbChargingPowerControl(NAME_CHARGING_POWER_CONTROL, vehicleStatusUpdate.zevUpdate.vehicleChargingPowerControl, chargingPowerControl)
            }
            realm.copyToRealmOrUpdate(realmVehicleState!!)
        }

        return mapRealmStateToVehicleState(finOrVin, realmVehicleState)
    }

    private fun mapAttributeIntToVehicleAttribute(value: RealmVehicleAttributeInt?, displayUnitCase: DisplayUnitCase): VehicleAttribute<Int, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
        }
        return VehicleAttribute(status, lastChanged, value?.value, mapDbUnitDataToUnit(value?.displayValue, displayUnitCase, value?.displayUnitOrdinal))
    }

    private fun mapVehicleAttributeToDbIntAttribute(
        attributeName: String,
        value: VehicleAttribute<Int, *>,
        currentModel: RealmVehicleAttributeInt?
    ): RealmVehicleAttributeInt {
        val dbModel = currentModel
            ?: realm.createObject(RealmVehicleAttributeInt::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: Status = ${dbModel.status} | Value = ${dbModel.value} | Time = ${dbModel.timestamp} -> Status = ${value.status.value} | Value = ${value.value} | Time = ${value.lastChanged}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.value = value.value
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapAttributeDoubleToVehicleAttribute(value: RealmVehicleAttributeDouble?, displayUnitCase: DisplayUnitCase): VehicleAttribute<Double, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
        }
        return VehicleAttribute(status, lastChanged, value?.value, mapDbUnitDataToUnit(value?.displayValue, displayUnitCase, value?.displayUnitOrdinal))
    }

    private fun mapVehicleAttributeToDbDoubleAttribute(
        attributeName: String,
        value: VehicleAttribute<Double, *>,
        currentModel: RealmVehicleAttributeDouble?
    ): RealmVehicleAttributeDouble {
        val dbModel = currentModel
            ?: realm.createObject(RealmVehicleAttributeDouble::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.value} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.value = value.value
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapAttributeLongToVehicleAttribute(value: RealmVehicleAttributeLong?, displayUnitCase: DisplayUnitCase): VehicleAttribute<Long, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
        }
        return VehicleAttribute(status, lastChanged, value?.value, mapDbUnitDataToUnit(value?.displayValue, displayUnitCase, value?.displayUnitOrdinal))
    }

    private fun mapVehicleAttributeToDbLongAttribute(
        attributeName: String,
        value: VehicleAttribute<Long, *>,
        currentModel: RealmVehicleAttributeLong?
    ): RealmVehicleAttributeLong {
        val dbModel = currentModel
            ?: realm.createObject(RealmVehicleAttributeLong::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.value} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged?.time}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.value = value.value
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapAttributeTariffToVehicleAttribute(value: RealmVehicleAttributeTariff?): VehicleAttribute<List<ZevTariff>, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
        }
        val zevTariffs = value?.times?.zip(value.rates)?.map { ZevTariff(Rate.values().getOrElse(it.first) { Rate.UNRECOGNIZED }, it.first) }
        return VehicleAttribute(status, lastChanged, zevTariffs, mapDbUnitDataToUnit(value?.displayValue, DisplayUnitCase.DISPLAYUNIT_NOT_SET, value?.displayUnitOrdinal))
    }

    private fun mapVehicleAttributeToDbTariffAttribute(
        attributeName: String,
        value: VehicleAttribute<List<ZevTariff>, *>,
        currentModel: RealmVehicleAttributeTariff?
    ): RealmVehicleAttributeTariff {
        val dbModel = currentModel ?: realm.createObject(RealmVehicleAttributeTariff::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.times}, ${dbModel.rates} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged?.time}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.times.clear()
                dbModel.times.addAll(value.value.map { it.time })
                dbModel.rates.clear()
                dbModel.rates.addAll(value.value.map { it.rate.ordinal })
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapAttributeSpeedAlertConfigurationToVehicleAttribute(value: RealmVehicleAttributeSpeedAlertConfiguration?): VehicleAttribute<List<SpeedAlertConfiguration>, *> {
        return value?.toVehicleAttribute(mapDbUnitDataToUnit(value.displayValue, DisplayUnitCase.SPEED_UNIT, value.displayUnitOrdinal))
            ?: VehicleAttribute(StatusEnum.INVALID, null, null as? List<SpeedAlertConfiguration>, null as? VehicleAttribute.Unit<*>)
    }

    private fun mapVehicleAttributeToDbSpeedAlertConfigurationAttribute(
        attributeName: String,
        value: VehicleAttribute<List<SpeedAlertConfiguration>, SpeedUnit>,
        currentModel: RealmVehicleAttributeSpeedAlertConfiguration?
    ): RealmVehicleAttributeSpeedAlertConfiguration? {
        val dbModel = currentModel
            ?: realm.createObject(RealmVehicleAttributeSpeedAlertConfiguration::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.endTimestampsInSeconds}, ${dbModel.thresholdsInKilometersPerHour} , ${dbModel.thresholdDisplayValue} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged?.time}")
                dbModel.fromVehicleAttribute(value)
            }
        }
    }

    private fun mapAttributeWeeklySetToVehicleAttribute(value: RealmVehicleAttributeWeeklySetHU?): VehicleAttribute<List<DayTime>, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
        }
        val weeklySets = value?.day?.zip(value.time)?.map { DayTime(Day.values().getOrElse(it.first) { Day.UNKNOWN }, it.second) }
        return VehicleAttribute(status, lastChanged, weeklySets, mapDbUnitDataToUnit(value?.displayValue, DisplayUnitCase.DISPLAYUNIT_NOT_SET, value?.displayUnitOrdinal))
    }

    private fun mapAttributeWeeklyProfileToVehicleAttribute(value: RealmVehicleAttributeWeeklyProfile?): VehicleAttribute<WeeklyProfile, *> {
        // The time profiles from the vva always have an id. Therefore this filter should never sort out any time profile.
        val timeProfiles = value?.timeProfiles?.filter { it.identifier != null }
        var result = WeeklyProfile(
            value?.singleEntriesActivatable ?: false,
            value?.maxSlots ?: 0,
            value?.maxTimeProfiles ?: 0,
            value?.currentSlots ?: 0,
            value?.currentTimeProfiles ?: 0,
            allTimeProfiles = timeProfiles?.map { convertRealmTimeProfile(it) }?.toMutableList() ?: ArrayList(),
            backupProfiles = timeProfiles?.map {
                Pair(it.identifier ?: 0, convertRealmTimeProfile(it))
            }?.toMap() ?: HashMap()

        )
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
            value
        }
        return VehicleAttribute<WeeklyProfile, NoUnit>(status, lastChanged, result, null)
    }

    private fun convertRealmTimeProfile(it: RealmTimeProfile): TimeProfile {
        return TimeProfile(
            identifier = it.identifier,
            hour = it.hour,
            minute = it.minute,
            active = it.active,
            days = it.days?.map {
                Day.values()[it]
            }?.toSet() ?: EnumSet.noneOf<Day>(Day::class.java),
            applicationIdentifier = it.applicationIdentifier
        )
    }

    private fun mapVehicleAttributeToDbWeeklySetAttribute(
        attributeName: String,
        value: VehicleAttribute<List<DayTime>, *>,
        currentModel: RealmVehicleAttributeWeeklySetHU?
    ): RealmVehicleAttributeWeeklySetHU {
        val dbModel = currentModel
            ?: realm.createObject(RealmVehicleAttributeWeeklySetHU::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.day}, ${dbModel.time} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged?.time}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.day.clear()
                dbModel.day.addAll(value.value.map { it.day.ordinal })
                dbModel.time.clear()
                dbModel.time.addAll(value.value.map { it.time })
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapVehicleAttributeToDbWeeklyProfileAttribute(
        attributeName: String,
        value: VehicleAttribute<WeeklyProfile, *>,
        currentModel: RealmVehicleAttributeWeeklyProfile?
    ): RealmVehicleAttributeWeeklyProfile {
        val dbModel = currentModel
            ?: realm.createObject(RealmVehicleAttributeWeeklyProfile::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update $attributeName: $dbModel")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.currentSlots = value.value.currentSlots
                dbModel.currentTimeProfiles = value.value.currentTimeProfiles
                dbModel.maxSlots = value.value.maxSlots
                dbModel.maxTimeProfiles = value.value.maxTimeProfiles
                dbModel.timeProfiles.forEach { profile -> profile.days?.deleteAllFromRealm() }
                dbModel.timeProfiles.deleteAllFromRealm()
                dbModel.timeProfiles.clear()
                value.value.timeProfiles.forEach {
                    realm.createObject<RealmTimeProfile>().apply {
                        identifier = it.identifier
                        active = it.active
                        days = mapDaysSetToInt(it.days)
                        hour = it.hour
                        minute = it.minute
                        applicationIdentifier = it.applicationIdentifier
                        dbModel.timeProfiles.add(this)
                    }
                }
                dbModel
            }
        }
    }

    private fun mapDaysSetToInt(days: Set<Day>): RealmList<Int> {
        val result = RealmList<Int>()
        days.map { result.add(it.ordinal) }
        return result
    }

    private fun mapAttributeSocProfileToVehicleAttribute(value: RealmVehicleAttributeSocProfile?): VehicleAttribute<List<SocProfile>, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
        }
        val socProfiles = value?.times?.zip(value.soc)?.map { SocProfile(it.first, it.second) }
        return VehicleAttribute(status, lastChanged, socProfiles, mapDbUnitDataToUnit(value?.displayValue, DisplayUnitCase.DISPLAYUNIT_NOT_SET, value?.displayUnitOrdinal))
    }

    private fun mapAttributeChargingProgramToVehicleAttribute(value: RealmVehicleAttributeChargingProgram?): VehicleAttribute<List<VehicleChargingProgramParameter>, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
        }
        val values = ChargingProgram.values()
        val programs = value?.chargingPrograms?.map {
            VehicleChargingProgramParameter(
                values.getOrElse(it.program ?: -1) { ChargingProgram.UNKNOWN },
                it.maxSoc ?: 0,
                it.autoUnlock ?: false,
                it.locationBasedCharging ?: false,
                it.weeklyProfile ?: false,
                it.clockTimer ?: false,
                it.maxChargingCurrent ?: 0,
                it.ecoCharging ?: false
            )
        }
        return VehicleAttribute(status, lastChanged, programs, mapDbUnitDataToUnit(value?.displayValue, DisplayUnitCase.DISPLAYUNIT_NOT_SET, value?.displayUnitOrdinal))
    }

    private fun mapVehicleAttributeToDbSocProfileAttribute(
        attributeName: String,
        value: VehicleAttribute<List<SocProfile>, *>,
        currentModel: RealmVehicleAttributeSocProfile?
    ): RealmVehicleAttributeSocProfile {
        val dbModel = currentModel
            ?: realm.createObject(RealmVehicleAttributeSocProfile::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.times}, ${dbModel.soc} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged?.time}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.times.clear()
                dbModel.times.addAll(value.value.map { it.time })
                dbModel.soc.clear()
                dbModel.soc.addAll(value.value.map { it.soc })
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapVehicleAttributeToDbChargingProgramAttribute(
        attributeName: String,
        value: VehicleAttribute<List<VehicleChargingProgramParameter>, *>,
        currentModel: RealmVehicleAttributeChargingProgram?
    ): RealmVehicleAttributeChargingProgram {
        val dbModel = currentModel ?: realm.createObject()
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.chargingPrograms} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged?.time}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.chargingPrograms.deleteAllFromRealm()
                dbModel.chargingPrograms.clear()
                value.value.forEach {
                    realm.createObject<RealmChargingProgram>().apply {
                        program = it.program.ordinal
                        maxSoc = it.maxSoc
                        autoUnlock = it.autoUnlock
                        locationBasedCharging = it.locationBasedCharging
                        weeklyProfile = it.weeklyProfile
                        clockTimer = it.clockTimer
                        maxChargingCurrent = it.maxChargingCurrent
                        ecoCharging = it.ecoCharging

                        dbModel.chargingPrograms.add(this)
                    }
                }
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapAttributeChargingBreakClockTimersToClockTimers(value: RealmVehicleAttributeChargingBreakClockTimer?): VehicleAttribute<List<VehicleChargingBreakClockTimer>, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
        }
        val actions = ChargingBreakClockTimerAction.values()
        val timers = value?.clockTimers?.map {
            VehicleChargingBreakClockTimer(
                actions.getOrElse(it.action ?: -1) { ChargingBreakClockTimerAction.UNKNOWN },
                it.endTimeHour ?: 0,
                it.endTimeMin ?: 0,
                it.startTimeHour ?: 0,
                it.startTimeMin ?: 0,
                it.timerId ?: 0
            )
        }
        return VehicleAttribute(status, lastChanged, timers, mapDbUnitDataToUnit(value?.displayValue, DisplayUnitCase.DISPLAYUNIT_NOT_SET, value?.displayUnitOrdinal))
    }

    private fun mapAttributeChargingPowerControlToPowerControl(value: RealmVehicleAttributeChargingPowerControl?): VehicleAttribute<VehicleChargingPowerControl, *> {
        var status = StatusEnum.INVALID
        var lastChanged: Date? = null
        var data: VehicleChargingPowerControl? = null
        value?.let {
            status = StatusEnum.from(it.status)
            lastChanged = it.timestamp?.let { ts -> Date(ts) }
            data = VehicleChargingPowerControl(
                ChargingStatusForPowerControl.values().getOrElse(it.chargingStatus) { ChargingStatusForPowerControl.NOT_DEFINED },
                it.controlDuration ?: 0,
                it.controlInfo ?: 0,
                it.chargingPower ?: 0,
                it.serviceStatus ?: 0,
                it.serviceAvailable ?: 0,
                it.useCase ?: 0
            )
        }
        return VehicleAttribute(status, lastChanged, data, mapDbUnitDataToUnit(value?.displayValue, DisplayUnitCase.DISPLAYUNIT_NOT_SET, value?.displayUnitOrdinal))
    }

    private fun mapVehicleAttributeToDbClockTimers(
        attributeName: String,
        value: VehicleAttribute<List<VehicleChargingBreakClockTimer>, *>,
        currentModel: RealmVehicleAttributeChargingBreakClockTimer?
    ): RealmVehicleAttributeChargingBreakClockTimer {
        val dbModel = currentModel ?: realm.createObject()
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.clockTimers} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged?.time}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.clockTimers.deleteAllFromRealm()
                dbModel.clockTimers.clear()
                value.value.forEach {
                    realm.createObject<RealmVehicleChargingBreakClockTimer>().apply {
                        action = it.action.ordinal
                        endTimeHour = it.endTimeHour
                        endTimeMin = it.endTimeMin
                        startTimeHour = it.startTimeHour
                        startTimeMin = it.startTimeMin
                        timerId = it.timerId

                        dbModel.clockTimers.add(this)
                    }
                }
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapVehicleAttributeToDbChargingPowerControl(
        attributeName: String,
        value: VehicleAttribute<VehicleChargingPowerControl, *>,
        currentModel: RealmVehicleAttributeChargingPowerControl?
    ): RealmVehicleAttributeChargingPowerControl {
        val dbModel = currentModel
            ?: realm.createObject(RealmVehicleAttributeChargingPowerControl::class.java)
        return when {
            value.value == null -> dbModel
            isNewerAttribute(value, dbModel).not() -> dbModel
            else -> {
                MBLoggerKit.d("Update attribute $attributeName: ${dbModel.status} | ${dbModel.chargingStatus}, ${dbModel.chargingPower} | ${dbModel.timestamp} -> ${value.status.value} | ${value.value} | ${value.lastChanged?.time}")
                dbModel.status = value.status.value
                dbModel.timestamp = value.lastChanged?.time
                dbModel.chargingStatus = value.value.chargingStatus.ordinal
                dbModel.controlDuration = value.value.controlDuration
                dbModel.controlInfo = value.value.controlInfo
                dbModel.chargingPower = value.value.chargingPower
                dbModel.serviceStatus = value.value.serviceStatus
                dbModel.serviceAvailable = value.value.serviceAvailable
                dbModel.useCase = value.value.useCase
                value.unit?.displayUnit?.let {
                    dbModel.displayValue = value.unit.displayValue
                    dbModel.displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
                    dbModel.displayUnitOrdinal = value.unit.displayUnit.ordinal
                }
                dbModel
            }
        }
    }

    private fun mapDbUnitDataToUnit(displayValue: String?, displayUnitCase: DisplayUnitCase, displayUnitOrdinal: Int?): VehicleAttribute.Unit<*>? {
        return if (displayValue != null && displayUnitOrdinal != null) {
            createUnit(displayValue, displayUnitCase, displayUnitOrdinal)
        } else {
            null
        }
    }

    private fun createUnit(displayValue: String, displayUnitCase: DisplayUnitCase, displayUnitOrdinal: Int): VehicleAttribute.Unit<*>? {
        return when (displayUnitCase) {
            DisplayUnitCase.CLOCK_HOUR_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.clockHourUnitFromInt(displayUnitOrdinal))
            }
            DisplayUnitCase.COMBUSTION_CONSUMPTION_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.combustionConsumptionUnitFromInt(displayUnitOrdinal))
            }
            DisplayUnitCase.ELECTRICITY_CONSUMPTION_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.electricityConsumptionUnitFromInt(displayUnitOrdinal))
            }
            DisplayUnitCase.GAS_CONSUMPTION_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.gasConsumptionUnitFromInt(displayUnitOrdinal))
            }
            DisplayUnitCase.PRESSURE_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.pressureUnitFromInt(displayUnitOrdinal))
            }
            DisplayUnitCase.RATIO_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.ratioUnitFromInt(displayUnitOrdinal))
            }
            DisplayUnitCase.DISTANCE_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.distanceUnitFromInt(displayUnitOrdinal))
            }
            DisplayUnitCase.SPEED_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.speedUnitFromInt(displayUnitOrdinal))
            }
            DisplayUnitCase.TEMPERATURE_UNIT -> {
                VehicleAttribute.Unit(displayValue, displayUnitCase, VehicleStatus.temperatureUnitFromInt(displayUnitOrdinal))
            }
            else -> null
        }
    }

    private fun isNewerAttribute(attribute: VehicleAttribute<out Any, *>, timeObject: RealmTimeObject): Boolean {
        return when {
            attribute.lastChanged == null -> false
            timeObject.timestamp == null -> true
            else -> timeObject.timestamp?.let {
                attribute.lastChanged.time >= it
            } ?: false
        }
    }

    override fun currentVehicleState(finOrVin: String): VehicleStatus {
        return mapRealmStateToVehicleState(finOrVin, realm.where<RealmVehicleState>().equalTo(FIELD_VIN_OR_FIN, finOrVin).findFirst())
    }

    override fun clearVehicleStates() {
        realm.executeTransaction { realm ->
            realm.delete<RealmVehicleAttributeInt>()
            realm.delete<RealmVehicleAttributeDouble>()
            realm.delete<RealmVehicleAttributeLong>()
            realm.delete<RealmVehicleAttributeSocProfile>()
            realm.delete<RealmVehicleAttributeTariff>()
            realm.delete<RealmTimeProfile>()
            realm.delete<RealmVehicleAttributeWeeklyProfile>()
            realm.delete<RealmChargingProgram>()
            realm.delete<RealmVehicleAttributeChargingProgram>()
            realm.delete<RealmVehicleState>()
        }
    }

    // TODO: 2019-05-10

    override fun clearVehicleState(finOrVin: String): Boolean {
        val realmVehicleState = realm
            .where<RealmVehicleState>().equalTo(FIELD_VIN_OR_FIN, finOrVin).findFirst()
            ?: return false
        realm.executeTransaction {
            realmVehicleState.cascadeDeleteFromRealm()
            realmVehicleState.deleteFromRealm()
        }
        return true
    }

    //endregion

    @Suppress("UNCHECKED_CAST", "USELESS_IS_CHECK")
    private fun <T, R : Enum<*>> mapAttribute(attributeName: String, default: VehicleAttribute<T, R>, mapper: () -> VehicleAttribute<T, *>): VehicleAttribute<T, R> {
        return try {
            val attr = mapper() as VehicleAttribute<T, R>
            when {
                attr.unit == null -> attr
                attr.unit.displayUnit is R -> attr
                else -> {
                    MBLoggerKit.e("Failed to map unit for $attributeName. given: ${attr.unit.displayUnit::class.java.simpleName}")
                    attr.copy(unit = null)
                }
            }
        } catch (e: ClassCastException) {
            MBLoggerKit.e("Failed to map $attributeName", null, e)
            default
        }
    }

    private fun mapRealmStateToVehicleState(finOrVin: String, realmVehicleState: RealmVehicleState?): VehicleStatus {
        val vehicleState = VehicleStatus.initialState(finOrVin)
        return realmVehicleState?.let { realmVS ->
            vehicleState.auxheat.activeState = mapAttribute(
                NAME_AUXHEAT_ACTIVE_STATE,
                VehicleStatus.initialNoUnitVehicleAttribute(ActiveState.INACTIVE)
            ) { activeStateForInt(mapAttributeIntToVehicleAttribute(realmVS.auxheatActiveState, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            realmVS.auxHeatRuntime?.let {
                vehicleState.auxheat.runtime = mapAttribute(
                    NAME_AUXHEAT_RUNTIME,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.auxheatState?.let {
                vehicleState.auxheat.state = mapAttribute(
                    NAME_AUXHEAT_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute(AuxheatState.INACTIVE)
                ) { auxheatStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.auxheatTime1?.let {
                vehicleState.auxheat.time1 = mapAttribute(
                    NAME_AUXHEAT_TIME1,
                    VehicleStatus.initialClockHourAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.CLOCK_HOUR_UNIT) }
            }
            realmVS.auxheatTime2?.let {
                vehicleState.auxheat.time2 = mapAttribute(
                    NAME_AUXHEAT_TIME2,
                    VehicleStatus.initialClockHourAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.CLOCK_HOUR_UNIT) }
            }
            realmVS.auxheatTime3?.let {
                vehicleState.auxheat.time3 = mapAttribute(
                    NAME_AUXHEAT_TIME3,
                    VehicleStatus.initialClockHourAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.CLOCK_HOUR_UNIT) }
            }
            realmVS.auxheatTimeSelection?.let {
                vehicleState.auxheat.timeSelection = mapAttribute(
                    NAME_AUXHEAT_TIME_SELECTION,
                    VehicleStatus.initialNoUnitVehicleAttribute<AuxheatTimeSelectionState>(null)
                ) { auxheatTimeSelectionStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.auxheatWarnings?.let {
                vehicleState.auxheat.warnings = mapAttribute(
                    NAME_AUXHEAT_WARNINGS,
                    VehicleStatus.initialNoUnitVehicleAttribute<AuxheatWarnings>(null)
                ) { auxheatWarningsFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.averageSpeedReset.let {
                vehicleState.statistics.averageSpeed.reset = mapAttribute(
                    NAME_AVG_SPEED_RESET,
                    VehicleStatus.initialSpeedAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.SPEED_UNIT) }
            }
            realmVS.averageSpeedStart?.let {
                vehicleState.statistics.averageSpeed.start = mapAttribute(
                    NAME_AVG_SPEED_START,
                    VehicleStatus.initialSpeedAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.SPEED_UNIT) }
            }
            realmVS.batteryState?.let {
                vehicleState.vehicle.starterBatteryState = mapAttribute(
                    NAME_BATTERY_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<BatteryState>(null)
                ) { batteryStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargingActive?.let {
                vehicleState.zev.chargingActive = mapAttribute(
                    NAME_CHARGING_ACTIVE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargingError?.let {
                vehicleState.zev.chargingError = mapAttribute(
                    NAME_CHARGING_ERROR,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargingError>(null)
                ) { chargingErrorForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargingMode?.let {
                vehicleState.zev.chargingMode = mapAttribute(
                    NAME_CHARGING_MODE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargingMode>(null)
                ) { chargingModeForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargingStatus?.let {
                vehicleState.zev.chargingStatus = mapAttribute(
                    NAME_CHARGING_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargingStatus>(null)
                ) { chargingStatusForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.clientMessageData?.let { } // todo -> clientMessageData not found
            realmVS.decklidLockState?.let {
                vehicleState.doors.decklid.lockStatus = mapAttribute(
                    NAME_DECKLID_LOCK_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<LockStatus>(null)
                ) { lockStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.decklidState?.let {
                vehicleState.doors.decklid.state = mapAttribute(
                    NAME_DECKLID_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<OpenStatus>(null)
                ) { openStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.distanceElectricalReset?.let {
                vehicleState.statistics.electric.distance.reset = mapAttribute(
                    NAME_DISTANCE_ELETRICAL_RESET,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.distanceElectricalStart?.let {
                vehicleState.statistics.electric.distance.start = mapAttribute(
                    NAME_DISTANCE_ELETRICAL_START,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.distanceGasReset?.let {
                vehicleState.statistics.gas.distance.reset = mapAttribute(
                    NAME_DISTANCE_GAS_RESET,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.distanceGasStart?.let {
                vehicleState.statistics.gas.distance.start = mapAttribute(
                    NAME_DISTANCE_GAS_START,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.distanceReset?.let {
                vehicleState.statistics.distance.reset = mapAttribute(
                    NAME_DISTANCE_RESET,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.distanceStart?.let {
                vehicleState.statistics.distance.start = mapAttribute(
                    NAME_DISTANCE_START,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.distanceZeReset?.let {
                vehicleState.statistics.distance.ze.reset = mapAttribute(
                    NAME_DISTANCE_ZE_RESET,
                    VehicleStatus.initialDistanceAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.distanceZeStart?.let {
                vehicleState.statistics.distance.ze.start = mapAttribute(
                    NAME_DISTANCE_ZE_START,
                    VehicleStatus.initialDistanceAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.doorLockStateFrontLeft?.let {
                vehicleState.doors.frontLeft.lockStatus = mapAttribute(
                    NAME_DOOR_LOCK_STATE_FRONT_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<LockStatus>(null)
                ) { lockStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.doorStateFrontLeft?.let {
                vehicleState.doors.frontLeft.state = mapAttribute(
                    NAME_DOOR_STATE_FRONT_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OpenStatus>(null)
                ) { openStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.doorLockStateFrontRight?.let {
                vehicleState.doors.frontRight.lockStatus = mapAttribute(
                    NAME_DOOR_LOCK_STATE_FRONT_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<LockStatus>(null)
                ) { lockStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.doorStateFrontRight?.let {
                vehicleState.doors.frontRight.state = mapAttribute(
                    NAME_DOOR_STATE_FRONT_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OpenStatus>(null)
                ) { openStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.doorLockStateGas?.let {
                vehicleState.vehicle.lockGasState = mapAttribute(
                    NAME_DOOR_LOCK_STATE_GAS,
                    VehicleStatus.initialNoUnitVehicleAttribute<LockStatus>(null)
                ) { lockStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.doorLockStateRearLeft?.let {
                vehicleState.doors.rearLeft.lockStatus = mapAttribute(
                    NAME_DOOR_LOCK_STATE_REAR_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<LockStatus>(null)
                ) { lockStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.doorStateRearLeft?.let {
                vehicleState.doors.rearLeft.state = mapAttribute(
                    NAME_DOOR_STATE_REAR_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OpenStatus>(null)
                ) { openStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.doorLockStateRearRight?.let {
                vehicleState.doors.rearRight.lockStatus = mapAttribute(
                    NAME_DOOR_LOCK_STATE_REAR_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<LockStatus>(null)
                ) { lockStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.doorStateRearRight?.let {
                vehicleState.doors.rearRight.state = mapAttribute(
                    NAME_DOOR_STATE_REAR_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OpenStatus>(null)
                ) { openStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.drivenTimeReset?.let {
                vehicleState.statistics.drivenTime.reset = mapAttribute(
                    NAME_DRIVEN_TIME_RESET,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.drivenTimeStart?.let {
                vehicleState.statistics.drivenTime.start = mapAttribute(
                    NAME_DRIVEN_TIME_START,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.drivenTimeZEReset?.let {
                vehicleState.statistics.drivenTime.ze.reset = mapAttribute(
                    NAME_DRIVEN_TIME_ZE_RESET,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.drivenTimeZEStart?.let {
                vehicleState.statistics.drivenTime.ze.start = mapAttribute(
                    NAME_DRIVEN_TIME_ZE_START,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.ecoScoreAccel?.let {
                vehicleState.ecoScore.accel = mapAttribute(
                    NAME_ECO_SCORE_ACCEL,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.ecoScoreBonusRange?.let {
                vehicleState.ecoScore.bonusRange = mapAttribute(
                    NAME_ECO_SCORE_BONUS_RANGE,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.ecoScoreConst?.let {
                vehicleState.ecoScore.const = mapAttribute(
                    NAME_ECO_SCORE_CONST,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.ecoScoreFreeWhl?.let {
                vehicleState.ecoScore.freeWhl = mapAttribute(
                    NAME_ECO_SCORE_FREEWHL,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.ecoScoreTotal?.let {
                vehicleState.ecoScore.total = mapAttribute(
                    NAME_ECO_SCORE_TOTAL,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.electricConsumptionReset?.let {
                vehicleState.statistics.electric.consumption.reset = mapAttribute(
                    NAME_ELECTRIC_CONSUMPTION_RESET,
                    VehicleStatus.initialElectricityConsumptionAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.ELECTRICITY_CONSUMPTION_UNIT) }
            }
            realmVS.electricConsumptionStart?.let {
                vehicleState.statistics.electric.consumption.start = mapAttribute(
                    NAME_ELECTRIC_CONSUMPTION_START,
                    VehicleStatus.initialElectricityConsumptionAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.ELECTRICITY_CONSUMPTION_UNIT) }
            }
            realmVS.electricalRangeSkipIndication?.let {
                vehicleState.warnings.electricalRangeScipIndication = mapAttribute(
                    NAME_ELECTRIC_RANGE_SKIP_INDICATION,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.engineState?.let {
                vehicleState.engine.engineRunningState = mapAttribute(
                    NAME_ENGINE_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<RunningState>(null)
                ) { runningStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.engineHoodStatus?.let {
                vehicleState.vehicle.engineHoodStatus = mapAttribute(
                    NAME_ENGINE_HOOD_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<OpenStatus>(null)
                ) { openStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            vehicleState.timestamp = realmVS.eventTimeStamp
            realmVS.filterParticelState?.let {
                vehicleState.vehicle.filterParticleState = mapAttribute(
                    NAME_FILTER_PARTICEL_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<FilterParticleState>(null)
                ) { filterParticelStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.gasConsumptionReset?.let {
                vehicleState.statistics.gas.consumption.reset = mapAttribute(
                    NAME_GAS_CONSUMPTION_RESET,
                    VehicleStatus.initialGasConsumptionAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.GAS_CONSUMPTION_UNIT) }
            }
            realmVS.gasConsumptionStart?.let {
                vehicleState.statistics.gas.consumption.start = mapAttribute(
                    NAME_GAS_CONSUMPTION_START,
                    VehicleStatus.initialGasConsumptionAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.GAS_CONSUMPTION_UNIT) }
            }
            realmVS.hybridWarnings?.let {
                vehicleState.zev.hybridWarnings = mapAttribute(
                    NAME_HYBRID_WARNINGS,
                    VehicleStatus.initialNoUnitVehicleAttribute<HybridWarningState>(null)
                ) { hybridWarningStatusForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.interiorProtectionStatus?.let {
                vehicleState.theft.interiorProtectionStatus = mapAttribute(
                    NAME_INTERIOR_PROT_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveSelectionState>(null)
                ) { activeSelectionStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.keyActivationState?.let {
                vehicleState.theft.keyActivationState = mapAttribute(
                    NAME_KEY_ACTIVATION_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<KeyActivationState>(null)
                ) { keyActivationStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.languageHu?.let {
                vehicleState.hu.language = mapAttribute(
                    NAME_LANGUAGE_HU,
                    VehicleStatus.initialNoUnitVehicleAttribute<LanguageState>(null)
                ) { languageHuFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.weeklyProfile?.let {
                vehicleState.hu.weeklyProfile = mapAttribute(
                    NAME_WEEKLY_PROFILE,
                    VehicleStatus.initialNoUnitVehicleAttribute<WeeklyProfile>(null)
                ) { mapAttributeWeeklyProfileToVehicleAttribute(it) }
            }
            realmVS.lastParkEvent?.let {
                vehicleState.theft.collision.lastParkEvent = mapAttribute(
                    NAME_LAST_PARK_EVENT,
                    VehicleStatus.initialClockHourAttribute<Long>(null)
                ) { mapAttributeLongToVehicleAttribute(it, DisplayUnitCase.CLOCK_HOUR_UNIT) }
            }
            realmVS.lastParkEventNotConfirmed?.let {
                vehicleState.theft.collision.lastParkEventNotConfirmed = mapAttribute(
                    NAME_LAST_PARK_EVENT_NOT_CONFIRMED,
                    VehicleStatus.initialNoUnitVehicleAttribute<LastParkEventState>(null)
                ) { lastParkEventStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.parkEventSensorStatus?.let {
                vehicleState.theft.collision.parkEventSensorStatus = mapAttribute(
                    NAME_PARK_EVENT_SENSOR_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveSelectionState>(null)
                ) { activeSelectionStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.liquidConsumptionReset?.let {
                vehicleState.statistics.liquid.consumption.reset = mapAttribute(
                    NAME_LIQUID_CONSUMPTION_RESET,
                    VehicleStatus.initialCombustionConsumptionAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.COMBUSTION_CONSUMPTION_UNIT) }
            }
            realmVS.liquidConsumptionStart?.let {
                vehicleState.statistics.liquid.consumption.start = mapAttribute(
                    NAME_LIQUID_CONSUMPTION_START,
                    VehicleStatus.initialCombustionConsumptionAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.COMBUSTION_CONSUMPTION_UNIT) }
            }
            realmVS.liquidRangeSkipIndication?.let {
                vehicleState.warnings.liquidRangeSkipIndication = mapAttribute(
                    NAME_LIQUID_CONSUMPTION_SKIP_INDICATION,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.lockStatusOverall?.let {
                vehicleState.doors.lockStateOverall = mapAttribute(
                    NAME_LOCK_STATE_OVERALL,
                    VehicleStatus.initialNoUnitVehicleAttribute<DoorLockOverallStatus>(null)
                ) { doorLockStateOverallForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.maxSoc?.let {
                vehicleState.zev.maxSoc = mapAttribute(
                    NAME_MAX_SOC,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.maxSocLowerLimit?.let {
                vehicleState.zev.maxSocLowerLimit = mapAttribute(
                    NAME_MAX_SOC_LOWER_LIMIT,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.odo?.let {
                vehicleState.vehicle.odo = mapAttribute(
                    NAME_ODO,
                    VehicleStatus.initialDistanceAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.overallRange?.let {
                vehicleState.tank.overallRange = mapAttribute(
                    NAME_OVERALL_RANGE,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.parkBrakeState?.let {
                vehicleState.vehicle.parkBrakeStatus = mapAttribute(
                    NAME_PARK_BRAKE_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.parkEventLevel?.let {
                vehicleState.theft.collision.parkEventLevel = mapAttribute(
                    NAME_PARK_EVENT_LEVEL,
                    VehicleStatus.initialNoUnitVehicleAttribute<ParkEventLevel>(null)
                ) { parkEventLevelForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.parkEventType?.let {
                vehicleState.theft.collision.parkEventType = mapAttribute(
                    NAME_PARK_EVENT_TYPE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ParkEventType>(null)
                ) { parkEventTypeForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.positionHeading?.let {
                vehicleState.location.heading = mapAttribute(
                    NAME_POSTION_HEADING,
                    VehicleStatus.initialNoUnitVehicleAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.positionLat?.let {
                vehicleState.location.latitude = mapAttribute(
                    NAME_POSITION_LAT,
                    VehicleStatus.initialNoUnitVehicleAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.positionLong?.let {
                vehicleState.location.longitude = mapAttribute(
                    NAME_POSITION_LONG,
                    VehicleStatus.initialNoUnitVehicleAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.positionErrorCode?.let {
                vehicleState.location.positionErrorState = mapAttribute(
                    NAME_POSITION_ERROR_CODE,
                    VehicleStatus.initialNoUnitVehicleAttribute<VehicleLocationErrorState>(null)
                ) { vehicleLocationErrorStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.proximityRequiredForLocation?.let {
                vehicleState.location.proximityCalculationRequired = mapAttribute(
                    NAME_PROXIMITY_CALCULATION_POSITION_REQUIRED,
                    VehicleStatus.initialNoUnitVehicleAttribute<RequiredState>(null)
                ) { requiredStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.remoteStartActive?.let {
                vehicleState.engine.remoteStartActiveState = mapAttribute(
                    NAME_REMOTE_START_ACTIVE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.remoteStartTemperature?.let {
                vehicleState.engine.remoteStartTemperature = mapAttribute(
                    NAME_REMOTE_START_TEMPERATURE,
                    VehicleStatus.initialTemperatureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.TEMPERATURE_UNIT) }
            }
            realmVS.remoteStartEndtime?.let {
                vehicleState.engine.remoteStartEndtime = mapAttribute(
                    NAME_REMOTE_START_ENDTIME,
                    VehicleStatus.initialNoUnitVehicleAttribute<Long>(null)
                ) { mapAttributeLongToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.rooftopState?.let {
                vehicleState.vehicle.rooftopState = mapAttribute(
                    NAME_ROOF_TOP_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<RooftopState>(null)
                ) { rooftopStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.selectedChargeProgram?.let {
                vehicleState.zev.selectedChargeProgram = mapAttribute(
                    NAME_SELECTED_CHARGE_PROGRAM,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargingProgram>(null)
                ) { chargingProgramForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.serviceIntervalDays?.let {
                vehicleState.vehicle.serviceIntervalDays = mapAttribute(
                    NAME_SERVICE_INTERVAL_DAYS,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.serviceIntervalDistance?.let {
                vehicleState.vehicle.serviceIntervalDistance = mapAttribute(
                    NAME_SERVICE_INTERVAL_DISTANCE,
                    VehicleStatus.initialDistanceAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.smartCharging?.let {
                vehicleState.zev.smartCharging = mapAttribute(
                    NAME_SMART_CHARGING,
                    VehicleStatus.initialNoUnitVehicleAttribute<SmartCharging>(null)
                ) { smartChargingForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.smartChargingDeparture?.let {
                vehicleState.zev.smartChargingAtDeparture = mapAttribute(
                    NAME_SMART_CHARGING_AT_DEPARTURE,
                    VehicleStatus.initialNoUnitVehicleAttribute<SmartChargingDeparture>(null)
                ) { smartChargingDepartureForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.smartChargingDeparture2?.let {
                vehicleState.zev.smartChargingAtDeparture2 = mapAttribute(
                    NAME_SMART_CHARGING_AT_DEPARTURE2,
                    VehicleStatus.initialNoUnitVehicleAttribute<SmartChargingDeparture>(null)
                ) { smartChargingDepartureForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.soc?.let {
                vehicleState.vehicle.soc = mapAttribute(
                    NAME_SOC,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.speedAlertConfiguration?.let {
                vehicleState.vehicle.speedAlertConfiguration = mapAttribute(
                    NAME_SPEED_ALERT_CONFIGURATION,
                    VehicleStatus.initialSpeedAttribute<List<SpeedAlertConfiguration>>(null)
                ) { mapAttributeSpeedAlertConfigurationToVehicleAttribute(it) }
            }
            realmVS.speedUnitFromIc?.let {
                vehicleState.vehicle.speedUnitFromIC = mapAttribute(
                    NAME_SPEED_UNIT_FROM_IC,
                    VehicleStatus.initialNoUnitVehicleAttribute<SpeedUnitType>(null)
                ) { speedUnitForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.sunroofEventState?.let {
                vehicleState.windows.sunroof.event = mapAttribute(
                    NAME_SUNROOF_EVENT_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<SunroofEventState>(null)
                ) { sunroofEventStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.sunroofEventActive?.let {
                vehicleState.windows.sunroof.isEventActive = mapAttribute(
                    NAME_SUNROOF_EVENT_ACTIVE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.sunroofState?.let {
                vehicleState.windows.sunroof.status = mapAttribute(
                    NAME_SUNROOF_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<SunroofState>(null)
                ) { sunroofStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.sunroofStatusFrontBlind?.let {
                vehicleState.windows.sunroof.blindFront = mapAttribute(
                    NAME_SUNROOF_BLIND_FRONT_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<SunroofBlindState>(null)
                ) { sunroofBlindStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.sunroofStatusRearBlind?.let {
                vehicleState.windows.sunroof.blindRear = mapAttribute(
                    NAME_SUNROOF_BLIND_REAR_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<SunroofBlindState>(null)
                ) { sunroofBlindStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.stateOverall?.let {
                vehicleState.doors.stateOverall = mapAttribute(
                    NAME_STATE_OVERALL,
                    VehicleStatus.initialNoUnitVehicleAttribute<DoorOverallStatus>(null)
                ) { doorStateOverallForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.tankAdBlueLevel?.let {
                vehicleState.tank.adBlueLevel = mapAttribute(
                    NAME_TANK_AD_BLUE_LEVEL,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.tankElectricalLevel?.let {
                vehicleState.tank.electricLevel = mapAttribute(
                    NAME_TANK_ELECTRICAL_LEVEL,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.tankElectricalRange?.let {
                vehicleState.tank.electricRange = mapAttribute(
                    NAME_TANK_ELETRICAL_RANGE,
                    VehicleStatus.initialDistanceAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.tankGasLevel?.let {
                vehicleState.tank.gasLevel = mapAttribute(
                    NAME_TANK_GAS_LEVEL,
                    VehicleStatus.initialRatioAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.tankGasRange?.let {
                vehicleState.tank.gasRange = mapAttribute(
                    NAME_TANK_GAS_RANGE,
                    VehicleStatus.initialDistanceAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.tankLiquidLevel?.let {
                vehicleState.tank.liquidLevel = mapAttribute(
                    NAME_TANK_LIQUID_LEVEL,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.tankLiquidRange?.let {
                vehicleState.tank.liquidRange = mapAttribute(
                    NAME_TANK_LIQUID_RANGE,
                    VehicleStatus.initialDistanceAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.teenageDrivingMode?.let {
                vehicleState.drivingModes.teenageDrivingMode = mapAttribute(
                    NAME_TEENAGE_DRIVING_MODE,
                    VehicleStatus.initialNoUnitVehicleAttribute<DrivingModeState>(null)
                ) { drivingModeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.temperatureUnitHu?.let {
                vehicleState.hu.temperature = mapAttribute(
                    NAME_TEMPERATURE_UNIT_HU,
                    VehicleStatus.initialNoUnitVehicleAttribute<TemperatureType>(null)
                ) { temperatureTypeFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.temperaturePointFrontLeft?.let {
                vehicleState.zev.temperature.frontLeft = mapAttribute(
                    NAME_TEMPERATURE_POINT_FRONT_LEFT,
                    VehicleStatus.initialTemperatureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.TEMPERATURE_UNIT) }
            }
            realmVS.temperaturePointFrontCenter?.let {
                vehicleState.zev.temperature.frontCenter = mapAttribute(
                    NAME_TEMPERATURE_POINT_FRONT_CENTER,
                    VehicleStatus.initialTemperatureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.TEMPERATURE_UNIT) }
            }
            realmVS.temperaturePointFrontRight?.let {
                vehicleState.zev.temperature.frontRight = mapAttribute(
                    NAME_TEMPERATURE_POINT_FRONT_RIGHT,
                    VehicleStatus.initialTemperatureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.TEMPERATURE_UNIT) }
            }
            realmVS.temperaturePointRearLeft?.let {
                vehicleState.zev.temperature.rearLeft = mapAttribute(
                    NAME_TEMPERATURE_POINT_REAR_LEFT,
                    VehicleStatus.initialTemperatureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.TEMPERATURE_UNIT) }
            }
            realmVS.temperaturePointRearCenter?.let {
                vehicleState.zev.temperature.rearCenter = mapAttribute(
                    NAME_TEMPERATURE_POINT_REAR_CENTER,
                    VehicleStatus.initialTemperatureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.TEMPERATURE_UNIT) }
            }
            realmVS.temperaturePointRearCenter2?.let {
                vehicleState.zev.temperature.rearCenter2 = mapAttribute(
                    NAME_TEMPERATURE_POINT_REAR_CENTER2,
                    VehicleStatus.initialTemperatureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.TEMPERATURE_UNIT) }
            }
            realmVS.temperaturePointRearRight?.let {
                vehicleState.zev.temperature.rearRight = mapAttribute(
                    NAME_TEMPERATURE_POINT_REAR_RIGHT,
                    VehicleStatus.initialTemperatureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.TEMPERATURE_UNIT) }
            }
            realmVS.theftAlarmActive?.let {
                vehicleState.theft.alarmActive = mapAttribute(
                    NAME_THEFT_ALARM_ACTIVE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.theftSystemArmed?.let {
                vehicleState.theft.systemArmed = mapAttribute(
                    NAME_THEFT_SYSTEM_ARMED,
                    VehicleStatus.initialNoUnitVehicleAttribute<ArmedState>(null)
                ) { armedStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.timeFormatHu?.let {
                vehicleState.hu.timeformat = mapAttribute(
                    NAME_TIME_FORMAT_HU,
                    VehicleStatus.initialNoUnitVehicleAttribute<TimeFormatType>(null)
                ) { timeFormatFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.tirePressureFrontLeft?.let {
                vehicleState.tires.frontLeft.pressureKpa = mapAttribute(
                    NAME_TIRE_PRESSURE_FRONT_LEFT,
                    VehicleStatus.initialPressureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.PRESSURE_UNIT) }
            }
            realmVS.tirePressureFrontRight?.let {
                vehicleState.tires.frontRight.pressureKpa = mapAttribute(
                    NAME_TIRE_PRESSURE_FRONT_RIGHT,
                    VehicleStatus.initialPressureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.PRESSURE_UNIT) }
            }
            realmVS.tirePressureRearLeft?.let {
                vehicleState.tires.rearLeft.pressureKpa = mapAttribute(
                    NAME_TIRE_PRESSURE_REAR_LEFT,
                    VehicleStatus.initialPressureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.PRESSURE_UNIT) }
            }
            realmVS.tirePressureRearRight?.let {
                vehicleState.tires.rearRight.pressureKpa = mapAttribute(
                    NAME_TIRE_PRESSURE_REAR_RIGHT,
                    VehicleStatus.initialPressureAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.PRESSURE_UNIT) }
            }
            realmVS.tireMarkerFrontLeft?.let {
                vehicleState.tires.frontLeft.warningLevel = mapAttribute(
                    NAME_TIRE_MARKER_FRONT_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<TireMarkerState>(null)
                ) { tireMarkerStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.tireMarkerFrontRight?.let {
                vehicleState.tires.frontRight.warningLevel = mapAttribute(
                    NAME_TIRE_MARKER_FRONT_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<TireMarkerState>(null)
                ) { tireMarkerStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.tireMarkerRearLeft?.let {
                vehicleState.tires.rearLeft.warningLevel = mapAttribute(
                    NAME_TIRE_MARKER_REAR_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<TireMarkerState>(null)
                ) { tireMarkerStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.tireMarkerRearRight?.let {
                vehicleState.tires.rearRight.warningLevel = mapAttribute(
                    NAME_TIRE_MARKER_REAR_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<TireMarkerState>(null)
                ) { tireMarkerStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.tirePressureMeasurementTimestamp?.let {
                vehicleState.tires.pressureMeasurementTimestampInSeconds = mapAttribute(
                    NAME_TIRE_PRESSURE_MEASUREMENT_TIMESTAMP,
                    VehicleStatus.initialNoUnitVehicleAttribute<Long>(null)
                ) { mapAttributeLongToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.tireSensorAvailable?.let {
                vehicleState.tires.sensorAvailable = mapAttribute(
                    NAME_TIRE_SENSOR_AVAILABLE,
                    VehicleStatus.initialNoUnitVehicleAttribute<SensorState>(null)
                ) { sensorStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.tireLevelPrw?.let {
                vehicleState.warnings.tireLevelPrw = mapAttribute(
                    NAME_TIRE_WARNING_LEVEL_PRW,
                    VehicleStatus.initialNoUnitVehicleAttribute<TireLevelPrwState>(null)
                ) { tireLevelPrwStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.towProtectionSensorStatus?.let {
                vehicleState.theft.towProtectionSensorStatus = mapAttribute(
                    NAME_TOW_PROTECTION_SENSOR_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveSelectionState>(null)
                ) { activeSelectionStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.trackingStateHu?.let {
                vehicleState.hu.tracking = mapAttribute(
                    NAME_TRACKING_STATE_HU,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.valetDrivingMode?.let {
                vehicleState.drivingModes.valetDrivingMode = mapAttribute(
                    NAME_VALET_DRIVING_MODE,
                    VehicleStatus.initialNoUnitVehicleAttribute<DrivingModeState>(null)
                ) { drivingModeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.vehicleDataConnectionState?.let {
                vehicleState.vehicle.dataConnectionState = mapAttribute(
                    NAME_VEHICLE_DATA_CONNECTION_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.vehicleLockState?.let {
                vehicleState.vehicle.doorLockState = mapAttribute(
                    NAME_VEHICLE_LOCK_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<DoorLockState>(null)
                ) { vehicleDoorLockstateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.vTime?.let {
                vehicleState.vehicle.time = mapAttribute(
                    NAME_VTIME,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.warningBrakeFluid?.let {
                vehicleState.warnings.brakeFluid = mapAttribute(
                    NAME_WARNING_BRAKE_FLUID,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.warningBrakeLiningWear?.let {
                vehicleState.warnings.brakeLiningWear = mapAttribute(
                    NAME_WARNING_BRAKE_LINING_WEAR,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.warningCoolantLevelLow?.let {
                vehicleState.warnings.coolantLevelLow = mapAttribute(
                    NAME_WARNING_COOLANT_LEVEL_LOW,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.warningEngineLight?.let {
                vehicleState.warnings.engineLight = mapAttribute(
                    NAME_WARNING_ENGINE_LIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.warningLowBattery?.let {
                vehicleState.warnings.lowBattery = mapAttribute(
                    NAME_WARNING_LOW_BATTERY,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.warningTireLamp?.let {
                vehicleState.warnings.tireLamp = mapAttribute(
                    NAME_WARNING_TIRE_LAMP,
                    VehicleStatus.initialNoUnitVehicleAttribute<TireLampState>(null)
                ) { tireLampStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.warningTireSprw?.let {
                vehicleState.warnings.tireSprw = mapAttribute(
                    NAME_WARNING_TIRE_SPRW,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.warningTireSrdk?.let {
                vehicleState.tires.warningLevelOverall = mapAttribute(
                    NAME_WARNING_TIRE_SRDK,
                    VehicleStatus.initialNoUnitVehicleAttribute<TireSrdkState>(null)
                ) { tireSrdkStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.warningWashWater?.let {
                vehicleState.warnings.washWater = mapAttribute(
                    NAME_WARNING_WASH_WATER,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.weekdayTariff?.let {
                vehicleState.zev.weekdayTariff = mapAttribute(
                    NAME_WEEKDAY_TARIFF,
                    VehicleStatus.initialNoUnitVehicleAttribute<List<ZevTariff>>(null)
                ) { mapAttributeTariffToVehicleAttribute(it) }
            }
            realmVS.weekendTariff?.let {
                vehicleState.zev.weekendTariff = mapAttribute(
                    NAME_WEEKEND_TARIFF,
                    VehicleStatus.initialNoUnitVehicleAttribute<List<ZevTariff>>(null)
                ) { mapAttributeTariffToVehicleAttribute(it) }
            }
            realmVS.weeklySetHU?.let {
                vehicleState.hu.weeklySetHU = mapAttribute(
                    NAME_WEEKLY_SET_HU,
                    VehicleStatus.initialNoUnitVehicleAttribute<List<DayTime>>(null)
                ) { mapAttributeWeeklySetToVehicleAttribute(it) }
            }
            realmVS.windowStatusRearBlind?.let {
                vehicleState.windows.blindRear = mapAttribute(
                    NAME_WINDOW_REAR_BLIND_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<WindowBlindState>(null)
                ) { windowBlindStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.windowStatusRearLeftBlind?.let {
                vehicleState.windows.blindRearLeft = mapAttribute(
                    NAME_WINDOW_REAR_LEFT_BLIND_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<WindowBlindState>(null)
                ) { windowBlindStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.windowStatusRearRightBlind?.let {
                vehicleState.windows.blindRearRight = mapAttribute(
                    NAME_WINDOW_REAR_RIGHT_BLIND_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<WindowBlindState>(null)
                ) { windowBlindStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.windowStateFrontLeft?.let {
                vehicleState.windows.frontLeft = mapAttribute(
                    NAME_WINDOW_STATE_FRONT_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<WindowState>(null)
                ) { windowStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.windowStateFrontRight?.let {
                vehicleState.windows.frontRight = mapAttribute(
                    NAME_WINDOW_STATE_FRONT_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<WindowState>(null)
                ) { windowStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.windowStateRearLeft?.let {
                vehicleState.windows.rearLeft = mapAttribute(
                    NAME_WINDOW_STATE_REAR_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<WindowState>(null)
                ) { windowStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.windowStateRearRight?.let {
                vehicleState.windows.rearRight = mapAttribute(
                    NAME_WINDOW_STATE_REAR_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<WindowState>(null)
                ) { windowStateFromInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.windowStateOverall?.let {
                vehicleState.windows.overallState = mapAttribute(
                    NAME_WINDOW_STATE_OVERALL,
                    VehicleStatus.initialNoUnitVehicleAttribute<WindowsOverallStatus>(null)
                ) { windowStateOverallForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.flipWindowStatus?.let {
                vehicleState.windows.flipWindowStatus = mapAttribute(
                    NAME_WINDOW_STATE_FLIP,
                    VehicleStatus.initialNoUnitVehicleAttribute<OpenStatus>(null)
                ) { openStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.ignitionState?.let {
                vehicleState.engine.ignitionState = mapAttribute(
                    NAME_IGNITION_STATE,
                    VehicleStatus.initialNoUnitVehicleAttribute<IgnitionState>(null)
                ) { ignitionStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.zevActive?.let {
                vehicleState.zev.activeState = mapAttribute(
                    NAME_ZEV_ACTIVE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargingPower?.let {
                vehicleState.zev.chargingPower = mapAttribute(
                    NAME_CHARGING_POWER,
                    VehicleStatus.initialNoUnitVehicleAttribute<Double>(null)
                ) { mapAttributeDoubleToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.departureTime?.let {
                vehicleState.zev.departureTime = mapAttribute(
                    NAME_DEPARTURE_TIME,
                    VehicleStatus.initialClockHourAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.CLOCK_HOUR_UNIT) }
            }
            realmVS.departureTimeMode?.let {
                vehicleState.zev.departureTimeMode = mapAttribute(
                    NAME_DEPARTURE_TIME_MODE,
                    VehicleStatus.initialNoUnitVehicleAttribute<DepartureTimeMode>(null)
                ) { departureTimeModeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.departureTimeSoc?.let {
                vehicleState.zev.departureTimeSoc = mapAttribute(
                    NAME_DEPARTURE_TIME_SOC,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.departureTimeWeekday?.let {
                vehicleState.zev.departureTimeWeekday = mapAttribute(
                    NAME_DEPARTURE_TIME_WEEKDAY,
                    VehicleStatus.initialNoUnitVehicleAttribute<Day>(null)
                ) { dayForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.endOfChargeTime?.let {
                vehicleState.zev.endOfChargeTime = mapAttribute(
                    NAME_END_OF_CHARGE_TIME,
                    VehicleStatus.initialClockHourAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.CLOCK_HOUR_UNIT) }
            }
            realmVS.endOfChargeTimeRelative?.let {
                vehicleState.zev.endOfChargeTimeRelative = mapAttribute(
                    NAME_END_OF_CHARGE_TIME_RELATIVE,
                    VehicleStatus.initialClockHourAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.CLOCK_HOUR_UNIT) }
            }
            realmVS.endOfChargeTimeWeekday?.let {
                vehicleState.zev.endOfChargeTimeWeekday = mapAttribute(
                    NAME_END_OF_CHARGE_TIME_WEEKDAY,
                    VehicleStatus.initialNoUnitVehicleAttribute<Day>(null)
                ) { dayForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.maxRange?.let {
                vehicleState.zev.maxRange = mapAttribute(
                    NAME_MAX_RANGE,
                    VehicleStatus.initialDistanceAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISTANCE_UNIT) }
            }
            realmVS.precondActive?.let {
                vehicleState.zev.precondActive = mapAttribute(
                    NAME_PRECOND_ACTIVE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondAtDeparture?.let {
                vehicleState.zev.precondAtDeparture = mapAttribute(
                    NAME_PRECOND_AT_DEPARTURE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondAtDeparture?.let {
                vehicleState.zev.precondAtDeparture = mapAttribute(
                    NAME_PRECOND_AT_DEPARTURE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondAtDepartureDisable?.let {
                vehicleState.zev.precondAtDepartureDisable = mapAttribute(
                    NAME_PRECOND_AT_DEPARTURE_DISABLE,
                    VehicleStatus.initialNoUnitVehicleAttribute<DisabledState>(null)
                ) { enableStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondDuration?.let {
                vehicleState.zev.precondDuration = mapAttribute(
                    NAME_PRECOND_DURATION,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.precondError?.let {
                vehicleState.zev.precondError = mapAttribute(
                    NAME_PRECOND_ERROR,
                    VehicleStatus.initialNoUnitVehicleAttribute<PrecondErrorState>(null)
                ) { precondErrorStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondNow?.let {
                vehicleState.zev.precondNow = mapAttribute(
                    NAME_PRECOND_NOW,
                    VehicleStatus.initialNoUnitVehicleAttribute<ActiveState>(null)
                ) { activeStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondNowError?.let {
                vehicleState.zev.precondNowError = mapAttribute(
                    NAME_PRECOND_NOW_ERROR,
                    VehicleStatus.initialNoUnitVehicleAttribute<PrecondErrorState>(null)
                ) { precondErrorStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondSeatFrontRight?.let {
                vehicleState.zev.precondSeatFrontRight = mapAttribute(
                    NAME_PRECOND_SEAT_FRONT_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondSeatFrontLeft?.let {
                vehicleState.zev.precondSeatFrontLeft = mapAttribute(
                    NAME_PRECOND_SEAT_FRONT_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondSeatRearRight?.let {
                vehicleState.zev.precondSeatRearRight = mapAttribute(
                    NAME_PRECOND_SEAT_REAR_RIGHT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.precondSeatRearLeft?.let {
                vehicleState.zev.precondSeatRearLeft = mapAttribute(
                    NAME_PRECOND_SEAT_REAR_LEFT,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.socprofile?.let {
                vehicleState.zev.socprofile = mapAttribute(
                    NAME_SOC_PROFILE,
                    VehicleStatus.initialNoUnitVehicleAttribute<List<SocProfile>>(null)
                ) { mapAttributeSocProfileToVehicleAttribute(it) }
            }
            realmVS.chargingProgram?.let {
                vehicleState.zev.chargingPrograms = mapAttribute(
                    NAME_CHARGING_PROGRAMS,
                    VehicleStatus.initialNoUnitVehicleAttribute<List<VehicleChargingProgramParameter>>(null)
                ) { mapAttributeChargingProgramToVehicleAttribute(it) }
            }
            realmVS.bidirectionalChargingActive?.let {
                vehicleState.zev.bidirectionalChargingActive = mapAttribute(
                    NAME_BIDIRECTIONAL_CHARGING_ACTIVE,
                    VehicleStatus.initialNoUnitVehicleAttribute<OnOffState>(null)
                ) { onOffStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.minSoc?.let {
                vehicleState.zev.minSoc = mapAttribute(
                    NAME_MIN_SOC,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.acChargingCurrentLimitation?.let {
                vehicleState.zev.acChargingCurrentLimitation = mapAttribute(
                    NAME_AC_CHARGING_CURRENT_LIMITATION,
                    VehicleStatus.initialNoUnitVehicleAttribute<AcChargingCurrentLimitation>(null)
                ) { acChargingCurrentLimitationForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargingErrorInfrastructure?.let {
                vehicleState.zev.chargingErrorInfrastructure = mapAttribute(
                    NAME_CHARGING_ERROR_INFRASTRUCTURE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargingErrorInfrastructure>(null)
                ) { chargingErrorInfrastructureForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargingTimeType?.let {
                vehicleState.zev.chargingTimeType = mapAttribute(
                    NAME_CHARGING_TIME_TYPE,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargingTimeType>(null)
                ) { chargingTimeTypeForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.minSocLowerLimit?.let {
                vehicleState.zev.minSocLowerLimit = mapAttribute(
                    NAME_MIN_SOC_LOWER_LIMIT,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.nextDepartureTime?.let {
                vehicleState.zev.nextDepartureTime = mapAttribute(
                    NAME_NEXT_DEPARTURE_TIME,
                    VehicleStatus.initialClockHourAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.CLOCK_HOUR_UNIT) }
            }
            realmVS.nextDepartureTimeWeekday?.let {
                vehicleState.zev.nextDepartureTimeWeekday = mapAttribute(
                    NAME_NEXT_DEPARTURE_TIME_WEEKDAY,
                    VehicleStatus.initialNoUnitVehicleAttribute<Day>(null)
                ) { dayForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.departureTimeIcon?.let {
                vehicleState.zev.departureTimeIcon = mapAttribute(
                    NAME_DEPARTURE_TIME_ICON,
                    VehicleStatus.initialNoUnitVehicleAttribute<DepartureTimeIcon>(null)
                ) { departureTimeIconForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargingErrorWim?.let {
                vehicleState.zev.chargingErrorWim = mapAttribute(
                    NAME_CHARGING_ERROR_WIM,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargingErrorWim>(null)
                ) { chargingErrorWimForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.maxSocUpperLimit?.let {
                vehicleState.zev.maxSocUpperLimit = mapAttribute(
                    NAME_MAX_SOC_UPPER_LIMIT,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.minSocUpperLimit?.let {
                vehicleState.zev.minSocUpperLimit = mapAttribute(
                    NAME_MIN_SOC_UPPER_LIMIT,
                    VehicleStatus.initialRatioAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.RATIO_UNIT) }
            }
            realmVS.chargingPowerEcoLimit?.let {
                vehicleState.zev.chargingPowerEcoLimit = mapAttribute(
                    NAME_CHARGING_POWER_ECO_LIMIT,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.chargeFlapDCStatus?.let {
                vehicleState.zev.chargeFlapDCStatus = mapAttribute(
                    NAME_CHARGE_FLAP_DC_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargeFlapStatus>(null)
                ) { chargeFlapStatusForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargeFlapACStatus?.let {
                vehicleState.zev.chargeFlapACStatus = mapAttribute(
                    NAME_CHARGE_FLAP_AC_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargeFlapStatus>(null)
                ) { chargeFlapStatusForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargeCouplerDCLockStatus?.let {
                vehicleState.zev.chargeCouplerDCLockStatus = mapAttribute(
                    NAME_CHARGE_COUPLER_DC_LOCK_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargeCouplerLockStatus>(null)
                ) { chargeCouplerLockStatusForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargeCouplerDCStatus?.let {
                vehicleState.zev.chargeCouplerDCStatus = mapAttribute(
                    NAME_CHARGE_COUPLER_DC_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargeCouplerStatus>(null)
                ) { chargeCouplerStatusForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.chargeCouplerACStatus?.let {
                vehicleState.zev.chargeCouplerACStatus = mapAttribute(
                    NAME_CHARGE_COUPLER_AC_STATUS,
                    VehicleStatus.initialNoUnitVehicleAttribute<ChargeCouplerStatus>(null)
                ) { chargeCouplerStatusForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.evRangeAssistDriveOnTime?.let {
                vehicleState.zev.evRangeAssistDriveOnTime = mapAttribute(
                    NAME_EV_RANGE_ASSIST_DRIVE_ON_TIME,
                    VehicleStatus.initialNoUnitVehicleAttribute<Long>(null)
                ) { mapAttributeLongToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.evRangeAssistDriveOnSOC?.let {
                vehicleState.zev.evRangeAssistDriveOnSOC = mapAttribute(
                    NAME_EV_RANGE_ASSIST_DRIVE_ON_SOC,
                    VehicleStatus.initialNoUnitVehicleAttribute<Int>(null)
                ) { mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.lastTheftWarningReason?.let {
                vehicleState.theft.lastTheftWarningReason = mapAttribute(
                    NAME_LAST_THEFT_WARNING_REASON,
                    VehicleStatus.initialNoUnitVehicleAttribute<TheftWarningReasonState>(null)
                ) { theftWarningReasonStateForInt(mapAttributeIntToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET)) }
            }
            realmVS.lastTheftWarning?.let {
                vehicleState.theft.lastTheftWarning = mapAttribute(
                    NAME_LAST_THEFT_WARNING,
                    VehicleStatus.initialNoUnitVehicleAttribute<Long>(null)
                ) { mapAttributeLongToVehicleAttribute(it, DisplayUnitCase.DISPLAYUNIT_NOT_SET) }
            }
            realmVS.chargingBreakClockTimer?.let {
                vehicleState.zev.vehicleChargingBreakClockTimers = mapAttribute(
                    NAME_CHARGING_BREAK_CLOCK_TIMER,
                    VehicleStatus.initialNoUnitVehicleAttribute<List<VehicleChargingBreakClockTimer>>(null)
                ) { mapAttributeChargingBreakClockTimersToClockTimers(it) }
            }
            realmVS.chargingPowerControl?.let {
                vehicleState.zev.vehicleChargingPowerControl = mapAttribute(
                    NAME_CHARGING_POWER_CONTROL,
                    VehicleStatus.initialNoUnitVehicleAttribute<VehicleChargingPowerControl>(null)
                ) { mapAttributeChargingPowerControlToPowerControl(it) }
            }
            vehicleState
        } ?: vehicleState
    }
}
