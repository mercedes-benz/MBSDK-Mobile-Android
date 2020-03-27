package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.dto.car.headunit.DayTime
import com.daimler.mbprotokit.dto.car.headunit.WeeklyProfile
import com.daimler.mbprotokit.dto.car.vehicledata.SpeedAlertConfiguration
import com.daimler.mbprotokit.dto.car.zev.ChargingPowerControl
import com.daimler.mbprotokit.dto.car.zev.SocProfile
import com.daimler.mbprotokit.dto.car.zev.TemperatureZone
import com.daimler.mbprotokit.dto.car.zev.VehicleChargingBreakClockTimer
import com.daimler.mbprotokit.dto.car.zev.VehicleChargingProgramParameter
import com.daimler.mbprotokit.dto.car.zev.ZevTariff
import com.daimler.mbprotokit.generated.VehicleCommands
import com.daimler.mbprotokit.generated.VehicleEvents
import kotlin.random.Random

object VehicleEventUtil {

    fun createAttribute(boolean: Boolean): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus
        .newBuilder().apply {
            boolValue = boolean
        }.build()

    fun createAttribute(int: Int): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus
        .newBuilder().apply {
            // Int32
            intValue = int.toLong()
        }.build()

    fun createAttribute(double: Double): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus
        .newBuilder()
        .apply {
            doubleValue = double
        }.build()

    fun createAttribute(long: Long): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus
        .newBuilder()
        .apply {
            // Int64
            intValue = long
        }.build()

    fun createAttribute(dayTimes: List<DayTime>): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus.newBuilder().apply {
        weeklySettingsHeadUnitValue =
            VehicleEvents.WeeklySettingsHeadUnitValue.newBuilder().apply {
                dayTimes.forEach { dayTime ->
                    addWeeklySettings(
                        VehicleEvents.WeeklySetting.getDefaultInstance().toBuilder().apply {
                            day = dayTime.day.id
                            minutesSinceMidnight = dayTime.time
                        }.build()
                    )
                }
            }.build()
    }.build()

    fun createWeeklyProfile(weeklyProfile: WeeklyProfile): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus.newBuilder().apply {
        weeklyProfileValue =
            VehicleEvents.WeeklyProfileValue.newBuilder().apply {
                singleTimeProfileEntriesActivatable = weeklyProfile.singleEntriesActivatable ?: false
                maxNumberOfWeeklyTimeProfileSlots = weeklyProfile.maxSlots ?: 0
                maxNumberOfTimeProfiles = weeklyProfile.maxTimeProfiles ?: 0
                currentNumberOfTimeProfileSlots = weeklyProfile.currentSlots ?: 0
                currentNumberOfTimeProfiles = weeklyProfile.currentTimeProfiles ?: 0
                weeklyProfile.allTimeProfiles.forEach { timeProfile ->
                    addTimeProfiles(
                        VehicleEvents.VVRTimeProfile.newBuilder().apply {
                            identifier = timeProfile.identifier ?: 0
                            hour = timeProfile.hour
                            minute = timeProfile.minute
                            active = timeProfile.active
                            timeProfile.days.map { VehicleCommands.TimeProfileDay.forNumber(it.id) }.forEach { timeProfileDay ->
                                addDays(timeProfileDay)
                            }
                        }.build()
                    )
                }
            }.build()
    }.build()

    fun createSpeedAlert(speedAlertConfigurations: List<SpeedAlertConfiguration>): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus
        .newBuilder().apply {
            speedAlertConfigurationValue = VehicleEvents.SpeedAlertConfigurationValue.newBuilder().apply {
                speedAlertConfigurations.forEach {
                    addSpeedAlertConfigurations(
                        VehicleEvents.SpeedAlertConfiguration.newBuilder().apply {
                            endTimestampInS = it.endTimestampInSeconds
                            thresholdInKph = it.thresholdInKilometersPerHour
                            thresholdDisplayValue = it.thresholdDisplayValue
                        }
                    )
                }
            }.build()
        }.build()

    fun createTemperaturePoints(points: Map<TemperatureZone, Double?>): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus
        .newBuilder().apply {
            temperaturePointsValue = VehicleEvents.TemperaturePointsValue.newBuilder().apply {
                points.forEach { (temperatureZone, temperatureValue) ->
                    addTemperaturePoints(
                        VehicleEvents.TemperaturePoint.newBuilder().apply {
                            zone = temperatureZone.value
                            temperature = temperatureValue ?: 0.0
                        }.build()
                    )
                }
            }.build()
        }.build()

    fun createWeekdayTariff(tariffs: List<ZevTariff>): VehicleEvents.VehicleAttributeStatus = VehicleEvents.VehicleAttributeStatus
        .newBuilder().apply {
            weekdayTariffValue = VehicleEvents.WeekdayTariffValue.newBuilder().apply {
                tariffs.forEach { zevTariff ->
                    addTariffs(
                        VehicleEvents.Tariff.newBuilder().apply {
                            rate = zevTariff.rate.endInclusive
                            time = zevTariff.time
                        }.build()
                    )
                }
            }.build()
        }.build()

    fun createWeekendTariff(tariffs: List<ZevTariff>): VehicleEvents.VehicleAttributeStatus = VehicleEvents
        .VehicleAttributeStatus.newBuilder().apply {
            weekendTariffValue = VehicleEvents.WeekendTariffValue.newBuilder().apply {
                tariffs.forEach { zevTariff ->
                    addTariffs(
                        VehicleEvents.Tariff.newBuilder().apply {
                            rate = zevTariff.rate.endInclusive
                            time = zevTariff.time
                        }.build()
                    )
                }
            }.build()
        }.build()

    fun createSocProfile(socProfile: List<SocProfile>): VehicleEvents.VehicleAttributeStatus = VehicleEvents
        .VehicleAttributeStatus.newBuilder().apply {
            stateOfChargeProfileValue = VehicleEvents.StateOfChargeProfileValue.newBuilder().apply {
                socProfile.forEach {
                    addStatesOfCharge(
                        VehicleEvents.StateOfCharge.newBuilder().apply {
                            timestampInS = it.time
                            stateOfCharge = it.soc
                        }.build()
                    )
                }
            }.build()
        }.build()

    fun createChargingProgams(params: List<VehicleChargingProgramParameter>): VehicleEvents.VehicleAttributeStatus =
        VehicleEvents.VehicleAttributeStatus.newBuilder().apply {
            chargeProgramsValue = VehicleEvents.ChargeProgramsValue.newBuilder().apply {
                params.forEach {
                    addChargeProgramParameters(
                        VehicleEvents.ChargeProgramParameters.newBuilder().apply {
                            chargeProgram =
                                VehicleEvents.ChargeProgram.forNumber(it.program?.chargeProgram?.number ?: -1)
                            maxSoc = it.maxSoc
                            autoUnlock = it.autoUnlock
                            locationBasedCharging = it.locationBasedCharging
                            weeklyProfile = it.weeklyProfile
                            clockTimer = it.clockTimer
                            maxChargingCurrent = it.maxChargingCurrent
                            ecoCharging = it.ecoCharging
                        }.build()
                    )
                }
            }.build()
        }.build()

    fun createChargingBreakClockTimer(timers: List<VehicleChargingBreakClockTimer>): VehicleEvents.VehicleAttributeStatus =
        VehicleEvents.VehicleAttributeStatus.newBuilder().apply {
            chargingBreakClockTimer = VehicleEvents.ChargingBreakClockTimer.newBuilder().apply {
                timers.forEach { timer ->
                    addClockTimer(
                        VehicleEvents.ChargingBreakClockTimerEntry.newBuilder().apply {
                            action = timer.action.action
                            endTimeHour = timer.endTimeHour
                            endTimeMin = timer.endTimeMin
                            startTimeHour = timer.startTimeHour
                            startTimeMin = timer.startTimeMin
                            timerId = timer.timerId
                        }.build()
                    )
                }
            }.build()
        }.build()

    fun createChargingPowerControl(control: ChargingPowerControl): VehicleEvents.VehicleAttributeStatus =
        VehicleEvents.VehicleAttributeStatus.newBuilder().apply {
            chargingPowerControl = VehicleEvents.ChargingPowerControl.newBuilder().apply {
                chargeStatus = control.chargingStatus.id
                ctrlDuration = control.controlDuration
                ctrlInfo = control.controlInfo
                chargePower = control.chargingPower
                servAvail = control.serviceAvailable
                servStat = control.serviceStatus
                useCase = control.useCase
            }.build()
        }.build()
}

inline fun <reified T : Enum<T>> random() = enumValues<T>()[Random.nextInt(0, enumValues<T>().size)]
