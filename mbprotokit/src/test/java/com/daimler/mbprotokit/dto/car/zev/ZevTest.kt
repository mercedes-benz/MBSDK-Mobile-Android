package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.dto.car.Day
import com.daimler.mbprotokit.dto.car.VehicleEventUtil
import com.daimler.mbprotokit.dto.car.random
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class ZevTest {

    @Test
    fun `zev full random mapping`(softly: SoftAssertions) {
        val chargingActive = Random.nextBoolean()
        val chargingError = random<ChargingError>()
        val chargingMode = random<ChargingMode>()
        val chargingStatus = random<ChargingStatus>()
        val hybridWarnings = random<HybridWarningState>()
        val activeState = Random.nextBoolean()
        val maxSoc = Random.nextInt()
        val maxSocLowerLimit = Random.nextInt()
        val selectedChargeProgram = random<ChargingProgram>()
        val smartCharging = random<SmartCharging>()
        val smartChargingAtDeparture = random<SmartChargingDeparture>()
        val chargingPower = Random.nextDouble()
        val departureTime = Random.nextInt()
        val departureTimeSoc = Random.nextInt()
        val endOfChargeTime = Random.nextInt()
        val endOfChargeTimeRelative = Random.nextInt()
        val maxRange = Random.nextInt()
        val precondActive = Random.nextBoolean()
        val precondAtDeparture = Random.nextBoolean()
        val precondAtDepartureDisable = Random.nextBoolean()
        val precondDuration = Random.nextInt()
        val precondNow = Random.nextBoolean()
        val precondSeatFrontRight = Random.nextBoolean()
        val precondSeatFrontLeft = Random.nextBoolean()
        val precondSeatRearRight = Random.nextBoolean()
        val precondSeatRearLeft = Random.nextBoolean()
        val bidirectionalChargingActive = Random.nextBoolean()
        val minSoc = Random.nextInt()
        val minSocLowerLimit = Random.nextInt()
        val nextDepartureTime = Random.nextInt()
        val chargingTimeType = Random.nextBoolean()
        val maxSocUpperLimit = Random.nextInt()
        val minSocUpperLimit = Random.nextInt()
        val chargingPowerEcoLimit = Random.nextInt()
        val evRangeAssistDriveOnSOC = Random.nextInt()
        val evRangeAssistDriveOnTime = Random.nextLong()

        val attributes = mapOf(
            ApiVehicleKey.CHARGING_ACTIVE.id to VehicleEventUtil.createAttribute(chargingActive),
            ApiVehicleKey.CHARGING_ERROR_DETAILS.id to VehicleEventUtil.createAttribute(chargingError.id),
            ApiVehicleKey.CHARGING_MODE.id to VehicleEventUtil.createAttribute(chargingMode.id),
            ApiVehicleKey.CHARGING_STATUS.id to VehicleEventUtil.createAttribute(chargingStatus.id),
            ApiVehicleKey.HYBRID_WARNINGS.id to VehicleEventUtil.createAttribute(hybridWarnings.id),
            ApiVehicleKey.ACTIVE.id to VehicleEventUtil.createAttribute(activeState),
            ApiVehicleKey.MAX_SOC.id to VehicleEventUtil.createAttribute(maxSoc),
            ApiVehicleKey.MAX_SOC_LOWER_LIMIT.id to VehicleEventUtil.createAttribute(maxSocLowerLimit),
            ApiVehicleKey.SELECTED_CHARGE_PROGRAMM.id to VehicleEventUtil.createAttribute(selectedChargeProgram.chargeProgram.number),
            ApiVehicleKey.SMART_CHARGING.id to VehicleEventUtil.createAttribute(smartCharging.id),
            ApiVehicleKey.SMART_CHARGING_AT_DEPARTURE.id to VehicleEventUtil.createAttribute(smartChargingAtDeparture.id),
            ApiVehicleKey.SMART_CHARGING_AT_DEPARTURE2.id to VehicleEventUtil.createAttribute(smartChargingAtDeparture.id),
            ApiVehicleKey.CHARGING_POWER.id to VehicleEventUtil.createAttribute(chargingPower),
            ApiVehicleKey.DEPARTURE_TIME.id to VehicleEventUtil.createAttribute(departureTime),
            ApiVehicleKey.DEPARTURE_TIME_SOC.id to VehicleEventUtil.createAttribute(departureTimeSoc),
            ApiVehicleKey.END_OF_CHARGE_TIME.id to VehicleEventUtil.createAttribute(endOfChargeTime),
            ApiVehicleKey.END_OF_CHARGE_TIME_RELATIVE.id to VehicleEventUtil.createAttribute(endOfChargeTimeRelative),
            ApiVehicleKey.MAX_RANGE.id to VehicleEventUtil.createAttribute(maxRange),
            ApiVehicleKey.PRECOND_ACTIVE.id to VehicleEventUtil.createAttribute(precondActive),
            ApiVehicleKey.PRECOND_AT_DEPARTURE_DISABLE.id to VehicleEventUtil.createAttribute(precondAtDepartureDisable),
            ApiVehicleKey.PRECOND_AT_DEPARTURE.id to VehicleEventUtil.createAttribute(precondAtDeparture),
            ApiVehicleKey.PRECOND_DURATION.id to VehicleEventUtil.createAttribute(precondDuration),
            ApiVehicleKey.PRECOND_NOW.id to VehicleEventUtil.createAttribute(precondNow),
            ApiVehicleKey.PRECOND_SEAT_FRONT_RIGHT.id to VehicleEventUtil.createAttribute(precondSeatFrontRight),
            ApiVehicleKey.PRECOND_SEAT_FRONT_LEFT.id to VehicleEventUtil.createAttribute(precondSeatFrontLeft),
            ApiVehicleKey.PRECOND_SEAT_REAR_RIGHT.id to VehicleEventUtil.createAttribute(precondSeatRearRight),
            ApiVehicleKey.PRECOND_SEAT_REAR_LEFT.id to VehicleEventUtil.createAttribute(precondSeatRearLeft),
            ApiVehicleKey.BIDIRECTIONAL_CHARGING_ACTIVE.id to VehicleEventUtil.createAttribute(bidirectionalChargingActive),
            ApiVehicleKey.MIN_SOC.id to VehicleEventUtil.createAttribute(minSoc),
            ApiVehicleKey.MIN_SOC_LOWER_LIMIT.id to VehicleEventUtil.createAttribute(minSocLowerLimit),
            ApiVehicleKey.NEXT_DEPARTURE_TIME.id to VehicleEventUtil.createAttribute(nextDepartureTime),
            ApiVehicleKey.CHARGING_TIME_TYPE.id to VehicleEventUtil.createAttribute(chargingTimeType),
            ApiVehicleKey.MAX_SOC_UPPER_LIMIT.id to VehicleEventUtil.createAttribute(maxSocUpperLimit),
            ApiVehicleKey.MIN_SOC_UPPER_LIMIT.id to VehicleEventUtil.createAttribute(minSocUpperLimit),
            ApiVehicleKey.CHARGING_POWER_ECO_LIMIT.id to VehicleEventUtil.createAttribute(chargingPowerEcoLimit),
            ApiVehicleKey.EV_RANGE_ASSIST_DRIVE_ON_SOC.id to VehicleEventUtil.createAttribute(evRangeAssistDriveOnSOC),
            ApiVehicleKey.EV_RANGE_ASSIST_DRIVE_ON_TIME.id to VehicleEventUtil.createAttribute(evRangeAssistDriveOnTime)
        )

        val zev = Zev(attributes)

        softly.assertThat(zev.chargingActive.first).isEqualTo(chargingActive)
        softly.assertThat(zev.chargingError.first).isEqualTo(chargingError)
        softly.assertThat(zev.chargingMode.first).isEqualTo(chargingMode)
        softly.assertThat(zev.chargingStatus.first).isEqualTo(chargingStatus)
        softly.assertThat(zev.hybridWarnings.first).isEqualTo(hybridWarnings)
        softly.assertThat(zev.activeState.first).isEqualTo(activeState)
        softly.assertThat(zev.maxSoc.first).isEqualTo(maxSoc)
        softly.assertThat(zev.maxSocLowerLimit.first).isEqualTo(maxSocLowerLimit)
        softly.assertThat(zev.selectedChargeProgram.first).isEqualTo(selectedChargeProgram)
        softly.assertThat(zev.smartCharging.first).isEqualTo(smartCharging)
        softly.assertThat(zev.smartChargingAtDeparture.first).isEqualTo(smartChargingAtDeparture)
        softly.assertThat(zev.smartChargingAtDeparture2.first).isEqualTo(smartChargingAtDeparture)
        softly.assertThat(zev.chargingPower.first).isEqualTo(chargingPower)
        softly.assertThat(zev.departureTime.first).isEqualTo(departureTime)
        softly.assertThat(zev.departureTimeSoc.first).isEqualTo(departureTimeSoc)
        softly.assertThat(zev.endOfChargeTime.first).isEqualTo(endOfChargeTime)
        softly.assertThat(zev.endOfChargeTimeRelative.first).isEqualTo(endOfChargeTimeRelative)
        softly.assertThat(zev.maxRange.first).isEqualTo(maxRange)
        softly.assertThat(zev.precondActive.first).isEqualTo(precondActive)
        softly.assertThat(zev.precondAtDeparture.first).isEqualTo(precondAtDeparture)
        softly.assertThat(zev.precondDuration.first).isEqualTo(precondDuration)
        softly.assertThat(zev.precondNow.first).isEqualTo(precondNow)
        softly.assertThat(zev.precondSeatFrontRight.first).isEqualTo(precondSeatFrontRight)
        softly.assertThat(zev.precondSeatFrontLeft.first).isEqualTo(precondSeatFrontLeft)
        softly.assertThat(zev.precondSeatRearRight.first).isEqualTo(precondSeatRearRight)
        softly.assertThat(zev.precondSeatRearLeft.first).isEqualTo(precondSeatRearLeft)
        softly.assertThat(zev.bidirectionalChargingActive.first).isEqualTo(bidirectionalChargingActive)
        softly.assertThat(zev.minSoc.first).isEqualTo(minSoc)
        softly.assertThat(zev.minSocLowerLimit.first).isEqualTo(minSocLowerLimit)
        softly.assertThat(zev.nextDepartureTime.first).isEqualTo(nextDepartureTime)
        softly.assertThat(zev.chargingTimeType.first).isEqualTo(chargingTimeType)
        softly.assertThat(zev.maxSocUpperLimit.first).isEqualTo(maxSocUpperLimit)
        softly.assertThat(zev.minSocUpperLimit.first).isEqualTo(minSocUpperLimit)
        softly.assertThat(zev.chargingPowerEcoLimit.first).isEqualTo(chargingPowerEcoLimit)
        softly.assertThat(zev.evRangeAssistDriveOnSOC.first).isEqualTo(evRangeAssistDriveOnSOC)
        softly.assertThat(zev.evRangeAssistDriveOnTime.first).isEqualTo(evRangeAssistDriveOnTime)
    }

    @ParameterizedTest
    @EnumSource(ChargingError::class)
    fun `zev chargingError mapping`(
        state: ChargingError,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.CHARGING_ERROR_DETAILS.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.chargingError.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(ChargingMode::class)
    fun `zev chargingMode mapping`(
        state: ChargingMode,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.CHARGING_MODE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.chargingMode.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(ChargingStatus::class)
    fun `zev chargingStatus mapping`(
        state: ChargingStatus,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.CHARGING_STATUS.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.chargingStatus.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(HybridWarningState::class)
    fun `zev hybridWarnings mapping`(
        state: HybridWarningState,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.HYBRID_WARNINGS.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.hybridWarnings.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(ChargingProgram::class)
    fun `zev selectedChargeProgram mapping`(
        state: ChargingProgram,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.SELECTED_CHARGE_PROGRAMM.id to VehicleEventUtil.createAttribute(state.chargeProgram.number)))
        softly.assertThat(zev.selectedChargeProgram.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(SmartCharging::class)
    fun `zev smartCharging mapping`(
        state: SmartCharging,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.SMART_CHARGING.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.smartCharging.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(SmartChargingDeparture::class)
    fun `zev smartChargingAtDeparture mapping`(
        state: SmartChargingDeparture,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.SMART_CHARGING_AT_DEPARTURE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.smartChargingAtDeparture.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(SmartChargingDeparture::class)
    fun `zev smartChargingAtDeparture2 mapping`(
        state: SmartChargingDeparture,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.SMART_CHARGING_AT_DEPARTURE2.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.smartChargingAtDeparture2.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(TemperatureZone::class)
    fun `zev temperaturePoints mapping`(
        zone: TemperatureZone,
        softly: SoftAssertions
    ) {
        val temperature = Random.nextDouble()
        val temperaturePoints = mapOf(
            zone to temperature
        )
        val zev = Zev(mapOf(ApiVehicleKey.TEMPERATURE_POINTS.id to VehicleEventUtil.createTemperaturePoints(temperaturePoints)))
        softly.assertThat(zev.temperaturePoints.first?.get(zone)).isEqualTo(temperature)
    }

    @ParameterizedTest
    @EnumSource(Rate::class)
    fun `zev weekdayTariff mapping`(
        rate: Rate,
        softly: SoftAssertions
    ) {
        val time = Random.nextInt()
        val tariff = ZevTariff(rate, time)
        val tariffs = listOf(tariff)

        val zev = Zev(mapOf(ApiVehicleKey.WEEKDAY_TARIFF.id to VehicleEventUtil.createWeekdayTariff(tariffs)))
        softly.assertThat(zev.weekdayTariff.first?.first()).isNotNull
        softly.assertThat(zev.weekdayTariff.first?.first()?.rate).isEqualTo(rate)
        softly.assertThat(zev.weekdayTariff.first?.first()?.time).isEqualTo(time)
    }

    @ParameterizedTest
    @EnumSource(Rate::class)
    fun `zev weekendTariff mapping`(
        rate: Rate,
        softly: SoftAssertions
    ) {
        val time = Random.nextInt()
        val tariff = ZevTariff(rate, time)
        val tariffs = listOf(tariff)

        val zev = Zev(mapOf(ApiVehicleKey.WEEKEND_TARIFF.id to VehicleEventUtil.createWeekendTariff(tariffs)))
        softly.assertThat(zev.weekendTariff.first?.first()).isNotNull
        softly.assertThat(zev.weekendTariff.first?.first()?.rate).isEqualTo(rate)
        softly.assertThat(zev.weekendTariff.first?.first()?.time).isEqualTo(time)
    }

    @ParameterizedTest
    @EnumSource(DepartureTimeMode::class)
    fun `zev departureTimeMode mapping`(
        state: DepartureTimeMode,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.DEPARTURE_TIME_MODE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.departureTimeMode.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(Day::class)
    fun `zev departureTimeWeekday mapping`(
        state: Day,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.DEPARTURE_TIME_WEEKDAY.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.departureTimeWeekday.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(Day::class)
    fun `zev endOfChargeTimeWeekday mapping`(
        state: Day,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.END_OF_CHARGE_TIME_WEEKDAY.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.endOfChargeTimeWeekday.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(PrecondErrorState::class)
    fun `zev endOfChargeTimeWeekday mapping`(
        state: PrecondErrorState,
        softly: SoftAssertions
    ) {
        val attributes = mapOf(
            ApiVehicleKey.PRECOND_ERROR.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.PRECOND_NOW_ERROR.id to VehicleEventUtil.createAttribute(state.id)
        )
        val zev = Zev(attributes)
        softly.assertThat(zev.precondError.first).isEqualTo(state)
        softly.assertThat(zev.precondNowError.first).isEqualTo(state)
    }

    @Test
    fun `zev socprofile mapping`(softly: SoftAssertions) {
        val socProfile = SocProfile(
            Random.nextLong(),
            Random.nextInt()
        )
        val profiles = listOf(socProfile)
        val zev = Zev(mapOf(ApiVehicleKey.SOC_PROFILE.id to VehicleEventUtil.createSocProfile(profiles)))
        softly.assertThat(zev.socprofile.first).isNotNull
        softly.assertThat(zev.socprofile.first?.first()?.time).isEqualTo(socProfile.time)
        softly.assertThat(zev.socprofile.first?.first()?.soc).isEqualTo(socProfile.soc)
    }

    @ParameterizedTest
    @EnumSource(ChargingProgram::class)
    fun `zev chargingPrograms mapping`(chargingProgram: ChargingProgram, softly: SoftAssertions) {
        val chargingParam = VehicleChargingProgramParameter(
            chargingProgram,
            Random.nextInt(),
            Random.nextBoolean(),
            Random.nextBoolean(),
            Random.nextBoolean(),
            Random.nextBoolean(),
            Random.nextInt(),
            Random.nextBoolean()
        )
        val params = listOf(chargingParam)

        val zev = Zev(mapOf(ApiVehicleKey.CHARGING_PROGRAMS.id to VehicleEventUtil.createChargingProgams(params)))
        softly.assertThat(zev.chargingPrograms.first).isNotNull
        softly.assertThat(zev.chargingPrograms.first?.first()).isNotNull
        val vehicleChargingProgram = zev.chargingPrograms.first?.first()
        softly.assertThat(vehicleChargingProgram?.program).isEqualTo(chargingParam.program)
        softly.assertThat(vehicleChargingProgram?.maxSoc).isEqualTo(chargingParam.maxSoc)
        softly.assertThat(vehicleChargingProgram?.autoUnlock).isEqualTo(chargingParam.autoUnlock)
        softly.assertThat(vehicleChargingProgram?.locationBasedCharging).isEqualTo(chargingParam.locationBasedCharging)
        softly.assertThat(vehicleChargingProgram?.weeklyProfile).isEqualTo(chargingParam.weeklyProfile)
        softly.assertThat(vehicleChargingProgram?.clockTimer).isEqualTo(chargingParam.clockTimer)
        softly.assertThat(vehicleChargingProgram?.maxChargingCurrent).isEqualTo(chargingParam.maxChargingCurrent)
        softly.assertThat(vehicleChargingProgram?.ecoCharging).isEqualTo(chargingParam.ecoCharging)
    }

    @ParameterizedTest
    @EnumSource(ChargingBreakClockTimerAction::class)
    fun `zev chargingBreakClockTimers mapping`(
        action: ChargingBreakClockTimerAction,
        softly: SoftAssertions
    ) {
        val timer = VehicleChargingBreakClockTimer(
            action,
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextLong()
        )
        val timers = listOf(timer)

        val zev = Zev(mapOf(ApiVehicleKey.CHARGING_BREAK_CLOCK_TIMER.id to VehicleEventUtil.createChargingBreakClockTimer(timers)))

        softly.assertThat(zev.chargingBreakClockTimers.first?.first()).isNotNull
        val clockTimer = zev.chargingBreakClockTimers.first?.first()
        softly.assertThat(clockTimer?.action).isEqualTo(timer.action)
        softly.assertThat(clockTimer?.endTimeHour).isEqualTo(timer.endTimeHour)
        softly.assertThat(clockTimer?.endTimeMin).isEqualTo(timer.endTimeMin)
        softly.assertThat(clockTimer?.startTimeHour).isEqualTo(timer.startTimeHour)
        softly.assertThat(clockTimer?.startTimeMin).isEqualTo(timer.startTimeMin)
        softly.assertThat(clockTimer?.timerId).isEqualTo(timer.timerId)
    }

    @ParameterizedTest
    @EnumSource(ChargingStatusForPowerControl::class)
    fun `zev chargingPowerControl mapping`(
        state: ChargingStatusForPowerControl,
        softly: SoftAssertions
    ) {
        val control = ChargingPowerControl(
            state,
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt()
        )
        val zev = Zev(mapOf(ApiVehicleKey.CHARGING_POWER_CONTROL.id to VehicleEventUtil.createChargingPowerControl(control)))
        softly.assertThat(zev.chargingPowerControl.first?.chargingStatus).isEqualTo(control.chargingStatus)
        softly.assertThat(zev.chargingPowerControl.first?.chargingPower).isEqualTo(control.chargingPower)
        softly.assertThat(zev.chargingPowerControl.first?.controlDuration).isEqualTo(control.controlDuration)
        softly.assertThat(zev.chargingPowerControl.first?.controlInfo).isEqualTo(control.controlInfo)
        softly.assertThat(zev.chargingPowerControl.first?.serviceAvailable).isEqualTo(control.serviceAvailable)
        softly.assertThat(zev.chargingPowerControl.first?.serviceStatus).isEqualTo(control.serviceStatus)
    }

    @ParameterizedTest
    @EnumSource(ChargingLimitation::class)
    fun `zev acChargingCurrentLimitation mapping`(
        state: ChargingLimitation,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.AC_CHARGING_CURRENT_LIMITATION.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.acChargingCurrentLimitation.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(ChargingErrorInfrastructure::class)
    fun `zev chargingErrorInfrastructure mapping`(
        state: ChargingErrorInfrastructure,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.CHARGING_ERROR_INFRASTRUCTURE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(zev.chargingErrorInfrastructure.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(Day::class)
    fun `zev nextDepartureTimeWeekday mapping`(
        day: Day,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.NEXT_DEPARTURE_TIME_WEEKDAY.id to VehicleEventUtil.createAttribute(day.id)))
        softly.assertThat(zev.nextDepartureTimeWeekday.first).isEqualTo(day)
    }

    @ParameterizedTest
    @EnumSource(DepartureTimeIcon::class)
    fun `zev departureTimeIcon mapping`(
        icon: DepartureTimeIcon,
        softly: SoftAssertions
    ) {
        val zev = Zev(mapOf(ApiVehicleKey.DEPARTURE_TIME_ICON.id to VehicleEventUtil.createAttribute(icon.id)))
        softly.assertThat(zev.departureTimeIcon.first).isEqualTo(icon)
    }

    @ParameterizedTest
    @EnumSource(ChargeFlapStatus::class)
    fun `zev chargeFlapDCStatus mapping`(
        state: ChargeFlapStatus,
        softly: SoftAssertions
    ) {
        val zev = Zev(
            mapOf(
                ApiVehicleKey.CHARGE_FLAP_DC_STATUS.id to VehicleEventUtil.createAttribute(state.id),
                ApiVehicleKey.CHARGE_FLAP_AC_STATUS.id to VehicleEventUtil.createAttribute(state.id)
            )
        )
        softly.assertThat(zev.chargeFlapDCStatus.first).isEqualTo(state)
        softly.assertThat(zev.chargeFlapACStatus.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(CouplerLockStatus::class)
    fun `zev chargeCouplerDCLockStatus mapping`(
        state: CouplerLockStatus,
        softly: SoftAssertions
    ) {
        val zev = Zev(
            mapOf(
                ApiVehicleKey.CHARGE_COUPLER_DC_LOCK_STATUS.id to VehicleEventUtil.createAttribute(state.id),
                ApiVehicleKey.CHARGE_COUPLER_AC_LOCK_STATUS.id to VehicleEventUtil.createAttribute(state.id)
            )
        )
        softly.assertThat(zev.chargeCouplerDCLockStatus.first).isEqualTo(state)
        softly.assertThat(zev.chargeCouplerACLockStatus.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(CouplerStatus::class)
    fun `zev chargeCouplerDCStatus mapping`(
        state: CouplerStatus,
        softly: SoftAssertions
    ) {
        val zev = Zev(
            mapOf(
                ApiVehicleKey.CHARGE_COUPLER_DC_STATUS.id to VehicleEventUtil.createAttribute(state.id),
                ApiVehicleKey.CHARGE_COUPLER_AC_STATUS.id to VehicleEventUtil.createAttribute(state.id)
            )
        )
        softly.assertThat(zev.chargeCouplerDCStatus.first).isEqualTo(state)
        softly.assertThat(zev.chargeCouplerACStatus.first).isEqualTo(state)
    }
}
