package com.daimler.mbcarkit.proto.tmp.car

import com.daimler.mbcarkit.business.model.vehicle.ChargingBreakClockTimerAction
import com.daimler.mbcarkit.business.model.vehicle.ChargingProgram
import com.daimler.mbcarkit.business.model.vehicle.ChargingStatusForPowerControl
import com.daimler.mbcarkit.business.model.vehicle.Day
import com.daimler.mbcarkit.business.model.vehicle.DayTime
import com.daimler.mbcarkit.business.model.vehicle.Rate
import com.daimler.mbcarkit.business.model.vehicle.SocProfile
import com.daimler.mbcarkit.business.model.vehicle.SpeedAlertConfiguration
import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.TimeProfile
import com.daimler.mbcarkit.business.model.vehicle.VehicleAttribute
import com.daimler.mbcarkit.business.model.vehicle.VehicleChargingBreakClockTimer
import com.daimler.mbcarkit.business.model.vehicle.VehicleChargingPowerControl
import com.daimler.mbcarkit.business.model.vehicle.VehicleChargingProgramParameter
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdate
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdates
import com.daimler.mbcarkit.business.model.vehicle.WeeklyProfile
import com.daimler.mbcarkit.business.model.vehicle.ZevStatusUpdate
import com.daimler.mbcarkit.business.model.vehicle.ZevTariff
import com.daimler.mbprotokit.dto.car.zev.TemperatureZone

internal fun com.daimler.mbprotokit.dto.car.VehicleStatusUpdates.map() = VehicleStatusUpdates(
    vehiclesByVin = vehiclesByVin.mapValues { it.value.map() },
    sequenceNumber = sequenceNumber
)

/**
 * Temporary mapping from new proto DTO to old proto DTO
 */
internal fun com.daimler.mbprotokit.dto.car.VehicleUpdate.map() = VehicleStatusUpdate(
    fullUpdate = fullUpdate,
    auxheatActiveState = auxheat.activeState.mapTo {
        it?.toInt()
    },
    auxHeatRuntime = auxheat.runtime.mapTo(),
    auxheatState = auxheat.state.mapTo {
        it?.id
    },
    auxheatTime1 = auxheat.time1.mapTo(),
    auxheatTime2 = auxheat.time2.mapTo(),
    auxheatTime3 = auxheat.time3.mapTo(),
    auxheatTimeSelection = auxheat.timeSelection.mapTo {
        it?.id
    },
    auxheatWarnings = auxheat.warnings.mapTo(),
    averageSpeedReset = speed.averageSpeedReset.mapTo(),
    averageSpeedStart = speed.averageSpeedStart.mapTo(),
    batteryState = vehicleData.batteryState.mapTo {
        it?.id
    },
    decklidLockState = decklid.lockState.mapTo {
        it?.toInt()
    },
    clientMessageData = VehicleAttribute(StatusEnum.INVALID, null, null, null),
    decklidState = decklid.state.mapTo() {
        it?.toInt()
    },
    distanceElectricalReset = distance.electricalReset.mapTo(),
    distanceElectricalStart = distance.electricalStart.mapTo(),
    distanceGasReset = distance.gasReset.mapTo(),
    distanceGasStart = distance.gasStart.mapTo(),
    distanceReset = distance.liquidReset.mapTo(),
    distanceStart = distance.liquidStart.mapTo(),
    distanceZeReset = distance.zeReset.mapTo(),
    distanceZeStart = distance.zeStart.mapTo(),
    doorLockStateFrontLeft = door.doorLockStateFrontLeft.mapTo {
        it?.toInt()
    },
    doorStateFrontLeft = door.doorStateFrontLeft.mapTo {
        it?.toInt()
    },
    doorLockStateFrontRight = door.doorLockStateFrontRight.mapTo {
        it?.toInt()
    },
    doorStateFrontRight = door.doorStateFrontRight.mapTo {
        it?.toInt()
    },
    doorLockStateGas = vehicleData.doorLockStateGas.mapTo {
        it?.toInt()
    },
    doorLockStateRearLeft = door.doorLockStateRearLeft.mapTo {
        it?.toInt()
    },
    doorStateRearLeft = door.doorStateRearLeft.mapTo {
        it?.toInt()
    },
    doorLockStateRearRight = door.doorLockStateRearRight.mapTo {
        it?.toInt()
    },
    doorStateRearRight = door.doorStateRearRight.mapTo {
        it?.toInt()
    },
    drivenTimeReset = drivenTime.timeReset.mapTo(),
    drivenTimeStart = drivenTime.timeStart.mapTo(),
    drivenTimeZEReset = drivenTime.zeTimeReset.mapTo {
        // since the other driven times are also Ints, we convert this to Int here
        it?.toInt()
    },
    drivenTimeZEStart = drivenTime.zeTimeStart.mapTo {
        // since the other driven times are also Ints, we convert this to Int here
        it?.toInt()
    },
    ecoScoreAccel = ecoScore.accel.mapTo(),
    ecoScoreBonusRange = ecoScore.bonusRange.mapTo(),
    ecoScoreConst = ecoScore.const.mapTo(),
    ecoScoreFreeWhl = ecoScore.freeWhl.mapTo(),
    ecoScoreTotal = ecoScore.total.mapTo(),
    engineHoodStatus = vehicleData.engineHoodStatus.mapTo {
        it?.toInt()
    },
    electricConsumptionReset = consumption.electricConsumptionReset.mapTo(),
    electricConsumptionStart = consumption.electricConsumptionStart.mapTo(),
    electricalRangeSkipIndication = warnings.electricalRangeSkipIndication.mapTo {
        it?.toInt()
    },
    engineState = engine.engineState.mapTo {
        it?.toInt()
    },
    eventTimeStamp = eventTimeStamp,
    filterParticelState = vehicleData.filterParticleState.mapTo {
        it?.id
    },
    gasConsumptionReset = consumption.gasConsumptionReset.mapTo(),
    gasConsumptionStart = consumption.gasConsumptionStart.mapTo(),
    healthStatus = healthStatus.mapTo(),
    ignitionState = engine.ignitionState.mapTo {
        it?.id
    },
    interiorProtectionStatus = theft.interiorProtectionStatus.mapTo {
        it?.id
    },
    keyActivationState = theft.keyActivationState.mapTo {
        it?.toInt()
    },
    languageHu = headunit.languageHu.mapTo {
        it?.id
    },
    lastParkEvent = theft.collision.lastParkEvent.mapTo(),
    lastParkEventNotConfirmed = theft.collision.lastParkEventNotConfirmed.mapTo {
        it?.toInt()
    },
    parkEventSensorStatus = theft.collision.parkEventSensorStatus.mapTo {
        it?.id
    },
    liquidConsumptionReset = consumption.liquidConsumptionReset.mapTo(),
    liquidConsumptionStart = consumption.liquidConsumptionStart.mapTo(),
    liquidRangeSkipIndication = warnings.liquidRangeSkipIndication.mapTo {
        it?.toInt()
    },
    lockStateOverall = vehicleData.lockStateOverall.mapTo {
        it?.id
    },
    odo = vehicleData.odo.mapTo {
        it ?: 0
    },
    parkBrakeState = vehicleData.parkBrakeState.mapTo {
        it?.toInt()
    },
    parkEventLevel = theft.collision.parkEventLevel.mapTo {
        it?.id
    },
    parkEventType = theft.collision.parkEventType.mapTo {
        it?.id
    },
    positionHeading = position.heading.mapTo(),
    positionLat = position.latitude.mapTo(),
    positionLong = position.longitude.mapTo(),
    positionErrorCode = position.errorCode.mapTo {
        it?.id
    },
    proximityCalculationRequiredForVehicleLocation = position.proximityCalculationRequired.mapTo {
        it?.toInt()
    },
    remoteStartActive = engine.remoteStartActive.mapTo {
        it?.toInt()
    },
    remoteStartTemperature = engine.remoteStartTemperature.mapTo(),
    remoteStartEndtime = engine.remoteStartEndtime.mapTo(),
    rooftopState = vehicleData.rooftopState.mapTo {
        it?.id
    },
    serviceIntervalDays = vehicleData.serviceIntervalDays.mapTo(),
    serviceIntervalDistance = vehicleData.serviceIntervalDistance.mapTo(),
    sequenceNumber = sequenceNumber,
    soc = vehicleData.soc.mapTo(),
    speedAlertConfiguration = vehicleData.speedAlertConfiguration.mapTo {
        it?.map { alertConfig ->
            SpeedAlertConfiguration(
                endTimestampInSeconds = alertConfig.endTimestampInSeconds,
                thresholdInKilometersPerHour = alertConfig.thresholdInKilometersPerHour,
                thresholdDisplayValue = alertConfig.thresholdDisplayValue
            )
        }
    },
    speedUnitFromIc = vehicleData.speedUnitFromIc.mapTo {
        it?.toInt()
    },
    stateOverall = stateOverall.mapTo {
        it?.id
    },
    sunroofEventState = sunroof.eventState.mapTo {
        it?.id
    },
    sunroofEventActive = sunroof.eventActive.mapTo {
        it?.toInt()
    },
    sunroofState = sunroof.state.mapTo {
        it?.id
    },
    sunroofStatusFrontBlind = sunroof.blindFrontState.mapTo {
        it?.id
    },
    sunroofStatusRearBlind = sunroof.blindRearState.mapTo {
        it?.id
    },
    tankAdBlueLevel = tank.adBlueLevel.mapTo(),
    tankElectricalLevel = tank.electricalLevel.mapTo(),
    tankElectricalRange = tank.electricalRange.mapTo(),
    tankGasLevel = tank.gasLevel.mapTo(),
    tankGasRange = tank.gasRange.mapTo(),
    tankLiquidLevel = tank.liquidLevel.mapTo(),
    tankLiquidRange = tank.liquidRange.mapTo(),
    overallRange = tank.overallRange.mapTo(),
    temperatureUnitHu = headunit.temperatureUnitHu.mapTo {
        it?.toInt()
    },
    theftAlarmActive = theft.theftAlarmActive.mapTo {
        it?.toInt()
    },
    theftSystemArmed = theft.theftSystemArmed.mapTo {
        it?.toInt()
    },
    timeFormatHu = headunit.timeFormatHu.mapTo {
        it?.toInt() ?: 0
    },
    tireLevelPrw = warnings.tireLevelPrw.mapTo {
        it?.id
    },
    tirePressureFrontLeft = tire.tirePressureFrontLeft.mapTo(),
    tirePressureFrontRight = tire.tirePressureFrontRight.mapTo(),
    tirePressureRearLeft = tire.tirePressureRearLeft.mapTo(),
    tirePressureRearRight = tire.tirePressureRearRight.mapTo(),
    tireMarkerFrontLeft = tire.tireMarkerFrontLeft.mapTo {
        it?.id
    },
    tireMarkerFrontRight = tire.tireMarkerFrontRight.mapTo {
        it?.id
    },
    tireMarkerRearLeft = tire.tireMarkerRearLeft.mapTo {
        it?.id
    },
    tireMarkerRearRight = tire.tireMarkerRearRight.mapTo {
        it?.id
    },
    tirePressureMeasurementTimestamp = tire.tirePressureMeasurementTimestamp.mapTo(),
    tireSensorAvailable = tire.tireSensorAvailable.mapTo {
        it?.id
    },
    towProtectionSensorStatus = theft.towProtectionSensorStatus.mapTo {
        it?.id
    },
    trackingStateHu = headunit.trackingStateHu.mapTo {
        it?.toInt()
    },
    vehicleDataConnectionState = vehicleData.vehicleDataConnectionState.mapTo {
        it?.toInt()
    },
    vehicleLockState = vehicleLockState.mapTo {
        it?.id
    },
    finOrVin = finOrVin,
    vTime = vehicleData.vTime.mapTo {
        it?.toInt()
    },
    warningBrakeFluid = warnings.warningBrakeFluid.mapTo {
        it?.toInt()
    },
    warningBrakeLiningWear = warnings.warningBrakeLiningWear.mapTo {
        it?.toInt()
    },
    warningCoolantLevelLow = warnings.warningCoolantLevelLow.mapTo {
        it?.toInt()
    },
    warningEngineLight = warnings.warningEngineLight.mapTo {
        it?.toInt()
    },
    warningLowBattery = warnings.warningLowBattery.mapTo {
        it?.toInt()
    },
    warningTireLamp = warnings.warningTireLamp.mapTo {
        it?.id
    },
    warningTireSprw = warnings.warningTireSprw.mapTo {
        it?.toInt()
    },
    warningTireSrdk = tire.warningTireSrdk.mapTo {
        it?.id
    },
    warningWashWater = warnings.warningWashWater.mapTo {
        it?.toInt()
    },
    windowStatusRearBlind = windows.blindRearState.mapTo {
        it?.id
    },
    windowStatusRearLeftBlind = windows.blindRearLeftState.mapTo {
        it?.id
    },
    windowStatusRearRightBlind = windows.blindRearRightState.mapTo {
        it?.id
    },
    windowStateOverall = windows.stateOverall.mapTo {
        it?.id
    },
    windowStateFrontLeft = windows.stateFrontLeft.mapTo {
        it?.id
    },
    windowStateFrontRight = windows.stateFrontRight.mapTo {
        it?.id
    },
    windowStateRearLeft = windows.stateRearLeft.mapTo {
        it?.id
    },
    windowStateRearRight = windows.stateRearRight.mapTo {
        it?.id
    },
    flipWindowStatus = windows.flipWindowStatus.mapTo {
        it?.toInt()
    },
    weeklySetHU = headunit.weeklySetHU.mapTo {
        it?.map { dayTime ->
            DayTime(
                day = Day.values().getOrElse(dayTime.day.id) { Day.UNKNOWN },
                time = dayTime.time
            )
        }
    },
    zevUpdate = ZevStatusUpdate(
        chargingActive = zev.chargingActive.mapTo {
            it?.toInt()
        },
        chargingError = zev.chargingError.mapTo {
            it?.id
        },
        chargingMode = zev.chargingMode.mapTo {
            it?.id
        },
        chargingStatus = zev.chargingStatus.mapTo {
            it?.id
        },
        hybridWarnings = zev.hybridWarnings.mapTo {
            it?.id
        },
        activeState = zev.activeState.mapTo {
            it?.toInt()
        },
        maxSoc = zev.maxSoc.mapTo(),
        maxSocLowerLimit = zev.maxSocLowerLimit.mapTo(),
        selectedChargeProgram = zev.selectedChargeProgram.mapTo {
            it?.chargeProgram?.number
        },
        smartCharging = zev.smartCharging.mapTo {
            it?.id
        },
        smartChargingAtDeparture = zev.smartChargingAtDeparture.mapTo {
            it?.id
        },
        smartChargingAtDeparture2 = zev.smartChargingAtDeparture2.mapTo {
            it?.id
        },
        temperaturePointFrontCenter = zev.temperaturePoints.mapTo {
            it?.let { map ->
                map[TemperatureZone.FRONT_CENTER]
            } ?: 0.0
        },
        temperaturePointFrontLeft = zev.temperaturePoints.mapTo {
            it?.let { map ->
                map[TemperatureZone.FRONT_LEFT]
            } ?: 0.0
        },
        temperaturePointFrontRight = zev.temperaturePoints.mapTo {
            it?.let { map ->
                map[TemperatureZone.FRONT_RIGHT]
            } ?: 0.0
        },
        temperaturePointRearCenter = zev.temperaturePoints.mapTo {
            it?.let { map ->
                map[TemperatureZone.REAR_CENTER]
            } ?: 0.0
        },
        temperaturePointRearCenter2 = zev.temperaturePoints.mapTo {
            it?.let { map ->
                map[TemperatureZone.REAR_2_CENTER]
            } ?: 0.0
        },
        temperaturePointRearLeft = zev.temperaturePoints.mapTo {
            it?.let { map ->
                map[TemperatureZone.REAR_LEFT]
            } ?: 0.0
        },
        temperaturePointRearRight = zev.temperaturePoints.mapTo {
            it?.let { map ->
                map[TemperatureZone.REAR_RIGHT]
            } ?: 0.0
        },
        weekdayTariff = zev.weekdayTariff.mapTo {
            it?.map { tariff ->
                tariff.map()
            }
        },
        weekendTariff = zev.weekendTariff.mapTo {
            it?.map { tariff ->
                tariff.map()
            }
        },
        chargingPower = zev.chargingPower.mapTo(),
        departureTime = zev.departureTime.mapTo(),
        departureTimeMode = zev.departureTimeMode.mapTo {
            it?.id
        },
        departureTimeSoc = zev.departureTimeSoc.mapTo(),
        departureTimeWeekday = zev.departureTimeWeekday.mapTo {
            it?.id
        },
        endOfChargeTime = zev.endOfChargeTime.mapTo(),
        endOfChargeTimeRelative = zev.endOfChargeTimeRelative.mapTo(),
        endOfChargeTimeWeekday = zev.endOfChargeTimeWeekday.mapTo {
            it?.id
        },
        maxRange = zev.maxRange.mapTo(),
        precondActive = zev.precondActive.mapTo {
            it?.toInt()
        },
        precondAtDeparture = zev.precondAtDeparture.mapTo {
            it?.toInt()
        },
        precondAtDepartureDisable = zev.precondAtDepartureDisable.mapTo {
            it?.toInt()
        },
        precondDuration = zev.precondDuration.mapTo(),
        precondError = zev.precondError.mapTo {
            it?.id
        },
        precondNow = zev.precondNow.mapTo {
            it?.toInt()
        },
        precondNowError = zev.precondNowError.mapTo {
            it?.id
        },
        precondSeatFrontRight = zev.precondSeatFrontRight.mapTo {
            it?.toInt()
        },
        precondSeatFrontLeft = zev.precondSeatFrontLeft.mapTo {
            it?.toInt()
        },
        precondSeatRearRight = zev.precondSeatRearRight.mapTo {
            it?.toInt()
        },
        precondSeatRearLeft = zev.precondSeatRearLeft.mapTo {
            it?.toInt()
        },
        socprofile = zev.socprofile.mapTo {
            it?.map { socProfile ->
                SocProfile(
                    time = socProfile.time,
                    soc = socProfile.soc
                )
            }
        },
        chargingPrograms = zev.chargingPrograms.mapTo {
            it?.map { programParam ->
                VehicleChargingProgramParameter(
                    program = programParam.program?.chargeProgram?.number?.let { id ->
                        ChargingProgram.values().getOrElse(id) {
                            ChargingProgram.UNKNOWN
                        }
                    } ?: ChargingProgram.UNKNOWN,
                    maxSoc = programParam.maxSoc,
                    autoUnlock = programParam.autoUnlock,
                    locationBasedCharging = programParam.locationBasedCharging,
                    weeklyProfile = programParam.weeklyProfile,
                    clockTimer = programParam.clockTimer,
                    maxChargingCurrent = programParam.maxChargingCurrent,
                    ecoCharging = programParam.ecoCharging
                )
            }
        },
        bidirectionalChargingActive = zev.bidirectionalChargingActive.mapTo {
            it?.toInt()
        },
        minSoc = zev.minSoc.mapTo(),
        acChargingCurrentLimitation = zev.acChargingCurrentLimitation.mapTo {
            it?.id
        },
        chargingErrorInfrastructure = zev.chargingErrorInfrastructure.mapTo {
            it?.id
        },
        chargingTimeType = zev.chargingTimeType.mapTo {
            it?.toInt()
        },
        minSocLowerLimit = zev.minSocLowerLimit.mapTo(),
        nextDepartureTime = zev.nextDepartureTime.mapTo(),
        nextDepartureTimeWeekday = zev.nextDepartureTimeWeekday.mapTo {
            it?.id
        },
        departureTimeIcon = zev.departureTimeIcon.mapTo {
            it?.id
        },
        chargingErrorWim = zev.chargingErrorWim.mapTo(),
        maxSocUpperLimit = zev.maxSocUpperLimit.mapTo(),
        minSocUpperLimit = zev.minSocUpperLimit.mapTo(),
        chargingPowerEcoLimit = zev.chargingPowerEcoLimit.mapTo(),
        chargeFlapDCStatus = zev.chargeFlapDCStatus.mapTo {
            it?.id
        },
        chargeFlapACStatus = zev.chargeFlapACStatus.mapTo {
            it?.id
        },
        chargeCouplerDCLockStatus = zev.chargeCouplerDCLockStatus.mapTo {
            it?.id
        },
        chargeCouplerACLockStatus = zev.chargeCouplerACLockStatus.mapTo {
            it?.id
        },
        chargeCouplerDCStatus = zev.chargeCouplerDCStatus.mapTo {
            it?.id
        },
        chargeCouplerACStatus = zev.chargeCouplerACStatus.mapTo {
            it?.id
        },
        evRangeAssistDriveOnTime = zev.evRangeAssistDriveOnTime.mapTo(),
        evRangeAssistDriveOnSOC = zev.evRangeAssistDriveOnSOC.mapTo(),
        vehicleChargingBreakClockTimers = zev.chargingBreakClockTimers.mapTo { list ->
            list?.map {
                VehicleChargingBreakClockTimer(
                    action = it.action.map(),
                    endTimeHour = it.endTimeHour,
                    endTimeMin = it.endTimeMin,
                    startTimeHour = it.startTimeHour,
                    startTimeMin = it.startTimeMin,
                    timerId = it.timerId
                )
            }
        },
        vehicleChargingPowerControl = zev.chargingPowerControl.mapTo { value ->
            value?.let {
                VehicleChargingPowerControl(
                    chargingStatus = ChargingStatusForPowerControl.values()
                        .getOrElse(it.chargingStatus.ordinal) { ChargingStatusForPowerControl.NOT_DEFINED },
                    controlDuration = it.controlDuration,
                    controlInfo = it.controlInfo,
                    chargingPower = it.chargingPower,
                    serviceStatus = it.serviceStatus,
                    serviceAvailable = it.serviceAvailable,
                    useCase = it.useCase
                )
            }
        }
    ),
    lastTheftWarningReason = theft.lastTheftWarningReason.mapTo {
        it?.id
    },
    lastTheftWarning = theft.lastTheftWarning.mapTo(),
    weeklyProfile = headunit.weeklyProfile.mapTo {
        it?.let { weeklyProfile ->
            WeeklyProfile(
                singleEntriesActivatable = weeklyProfile.singleEntriesActivatable ?: false,
                maxSlots = weeklyProfile.maxSlots ?: 0,
                maxTimeProfiles = weeklyProfile.maxTimeProfiles ?: 0,
                currentSlots = weeklyProfile.currentSlots ?: 0,
                currentTimeProfiles = weeklyProfile.currentTimeProfiles ?: 0,
                allTimeProfiles = weeklyProfile.allTimeProfiles.map { timeProfile ->
                    TimeProfile(
                        identifier = timeProfile.identifier,
                        hour = timeProfile.hour,
                        minute = timeProfile.minute,
                        active = timeProfile.active,
                        days = timeProfile.days.map { day ->
                            Day.values().getOrElse(day.id) { Day.UNKNOWN }
                        }.toSet(),
                        applicationIdentifier = timeProfile.applicationIdentifier,
                        toBeRemoved = timeProfile.toBeRemoved
                    )
                }.toMutableList(),
                backupProfiles = it.backupProfiles.mapValues { map ->
                    TimeProfile(
                        identifier = map.value.identifier,
                        hour = map.value.hour,
                        minute = map.value.minute,
                        active = map.value.active,
                        days = map.value.days.map { day ->
                            Day.values().getOrElse(day.id) { Day.UNKNOWN }
                        }.toSet(),
                        applicationIdentifier = map.value.applicationIdentifier,
                        toBeRemoved = map.value.toBeRemoved
                    )
                }
            )
        }
    },
    teenageDrivingMode = drivingModes.teenageDrivingMode.mapTo {
        it?.id
    },
    valetDrivingMode = drivingModes.valetDrivingMode.mapTo {
        it?.id
    }
)

private fun Boolean.toInt() = if (this) 1 else 0

private fun com.daimler.mbprotokit.dto.car.zev.ZevTariff.map() = ZevTariff(
    rate = rate.map(),
    time = time
)

private fun com.daimler.mbprotokit.dto.car.zev.Rate.map() = when (this) {
    com.daimler.mbprotokit.dto.car.zev.Rate.INVALID_PRICE -> Rate.INVALID_PRICE
    com.daimler.mbprotokit.dto.car.zev.Rate.LOW_PRICE -> Rate.LOW_PRICE
    com.daimler.mbprotokit.dto.car.zev.Rate.NORMAL_PRICE -> Rate.NORMAL_PRICE
    com.daimler.mbprotokit.dto.car.zev.Rate.HIGH_PRICE -> Rate.HIGH_PRICE
    com.daimler.mbprotokit.dto.car.zev.Rate.UNRECOGNIZED -> Rate.UNRECOGNIZED
}

private fun com.daimler.mbprotokit.dto.car.zev.ChargingBreakClockTimerAction?.map() = when (this) {
    com.daimler.mbprotokit.dto.car.zev.ChargingBreakClockTimerAction.DELETE -> ChargingBreakClockTimerAction.DELETE
    com.daimler.mbprotokit.dto.car.zev.ChargingBreakClockTimerAction.ACTIVATE -> ChargingBreakClockTimerAction.ACTIVATE
    com.daimler.mbprotokit.dto.car.zev.ChargingBreakClockTimerAction.DEACTIVATE -> ChargingBreakClockTimerAction.DEACTIVATE
    com.daimler.mbprotokit.dto.car.zev.ChargingBreakClockTimerAction.UNRECOGNIZED -> ChargingBreakClockTimerAction.UNKNOWN
    null -> ChargingBreakClockTimerAction.UNKNOWN
}
