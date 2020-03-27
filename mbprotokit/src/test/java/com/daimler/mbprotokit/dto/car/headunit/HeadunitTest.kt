package com.daimler.mbprotokit.dto.car.headunit

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
class HeadunitTest {

    @Test
    fun `headunit full random mapping`(softly: SoftAssertions) {
        val languageHu = random<LanguageState>()
        val temperatureUnitHu = Random.nextBoolean()
        val timeFormat = Random.nextBoolean()
        val trackingStateHu = Random.nextBoolean()
        val dayTimes = listOf(DayTime(random(), Random.nextInt()))

        val attributes = mapOf(
            ApiVehicleKey.LANGUAGE_HU.id to VehicleEventUtil.createAttribute(languageHu.id),
            ApiVehicleKey.TEMPERATURE_UNIT_HU.id to VehicleEventUtil.createAttribute(temperatureUnitHu),
            ApiVehicleKey.TIME_FORMAT_HU.id to VehicleEventUtil.createAttribute(timeFormat),
            ApiVehicleKey.TRACKING_STATE_HU.id to VehicleEventUtil.createAttribute(trackingStateHu),
            ApiVehicleKey.WEEKLY_SET_HU.id to VehicleEventUtil.createAttribute(dayTimes)
        )

        val headunit = Headunit(attributes)

        softly.assertThat(headunit.languageHu.first).isEqualTo(languageHu)
        softly.assertThat(headunit.temperatureUnitHu.first).isEqualTo(temperatureUnitHu)
        softly.assertThat(headunit.timeFormatHu.first).isEqualTo(timeFormat)
        softly.assertThat(headunit.trackingStateHu.first).isEqualTo(trackingStateHu)
        softly.assertThat(headunit.weeklySetHU.first?.first()?.day).isEqualTo(dayTimes.first().day)
        softly.assertThat(headunit.weeklySetHU.first?.first()?.time).isEqualTo(dayTimes.first().time)
    }

    @ParameterizedTest
    @EnumSource(LanguageState::class)
    fun `headunit languageState mapping`(state: LanguageState, softly: SoftAssertions) {
        val headunit = Headunit(mapOf(ApiVehicleKey.LANGUAGE_HU.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(headunit.languageHu.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(Day::class)
    fun `headunit weeklySetHU mapping`(day: Day, softly: SoftAssertions) {
        val dayTimes = listOf(DayTime(day, Random.nextInt()))
        val headunit = Headunit(mapOf(ApiVehicleKey.WEEKLY_SET_HU.id to VehicleEventUtil.createAttribute(dayTimes)))
        softly.assertThat(headunit.weeklySetHU.first?.first()?.day).isEqualTo(dayTimes.first().day)
        softly.assertThat(headunit.weeklySetHU.first?.first()?.time).isEqualTo(dayTimes.first().time)
    }

    @Test
    fun `headunit weeklyProfile mapping`(softly: SoftAssertions) {
        val timeProfile = TimeProfile(
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextBoolean(),
            Day.values().toSet(),
            Random.nextInt(),
            Random.nextBoolean()
        )
        val weeklyProfile = WeeklyProfile(
            Random.nextBoolean(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            mutableListOf(timeProfile)
        )
        val headunit = Headunit(mapOf(ApiVehicleKey.WEEKLY_PROFILE.id to VehicleEventUtil.createWeeklyProfile(weeklyProfile)))

        softly.assertThat(headunit.weeklyProfile.first).isNotNull
        headunit.weeklyProfile.first?.apply {
            softly.assertThat(singleEntriesActivatable).isEqualTo(weeklyProfile.singleEntriesActivatable)
            softly.assertThat(maxSlots).isEqualTo(weeklyProfile.maxSlots)
            softly.assertThat(maxTimeProfiles).isEqualTo(weeklyProfile.maxTimeProfiles)
            softly.assertThat(currentSlots).isEqualTo(weeklyProfile.currentSlots)
            softly.assertThat(currentTimeProfiles).isEqualTo(weeklyProfile.currentTimeProfiles)
            softly.assertThat(allTimeProfiles).isNotNull
            softly.assertThat(allTimeProfiles.first().identifier).isEqualTo(timeProfile.identifier)
            softly.assertThat(allTimeProfiles.first().hour).isEqualTo(timeProfile.hour)
            softly.assertThat(allTimeProfiles.first().minute).isEqualTo(timeProfile.minute)
            softly.assertThat(allTimeProfiles.first().active).isEqualTo(timeProfile.active)
            softly.assertThat(allTimeProfiles.first().days).isEqualTo(timeProfile.days)
        }
    }
}
