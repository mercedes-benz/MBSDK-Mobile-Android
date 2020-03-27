package com.daimler.mbprotokit.dto.car.theft

import com.daimler.mbprotokit.dto.car.VehicleEventUtil
import com.daimler.mbprotokit.dto.car.random
import com.daimler.mbprotokit.dto.car.zev.TheftWarningReasonState
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class TheftTest {

    @Test
    fun `theft full random mapping`(softly: SoftAssertions) {
        val interiorProtectionStatus = random<ActiveSelectionState>()
        val theftAlarmActive = Random.nextBoolean()
        val theftSystemArmed = Random.nextBoolean()
        val towProtectionSensorStatus = random<ActiveSelectionState>()
        val lastTheftWarningReason = random<TheftWarningReasonState>()
        val lastTheftWarning = Random.nextLong()
        val keyActivationState = Random.nextBoolean()

        val attributes = mapOf(
            ApiVehicleKey.INTERIOR_PROTECTION_SENSOR_STATE.id to VehicleEventUtil.createAttribute(interiorProtectionStatus.id),
            ApiVehicleKey.THEFT_ALARM_ACTIVE.id to VehicleEventUtil.createAttribute(theftAlarmActive),
            ApiVehicleKey.THEFT_SYSTEM_ARMED.id to VehicleEventUtil.createAttribute(theftSystemArmed),
            ApiVehicleKey.TOW_PROTECTION_SENSOR_STATE.id to VehicleEventUtil.createAttribute(towProtectionSensorStatus.id),
            ApiVehicleKey.LAST_THEFT_WARNING_REASON.id to VehicleEventUtil.createAttribute(lastTheftWarningReason.id),
            ApiVehicleKey.LAST_THEFT_WARNING.id to VehicleEventUtil.createAttribute(lastTheftWarning),
            ApiVehicleKey.KEY_ACTIVATION_STATE.id to VehicleEventUtil.createAttribute(keyActivationState)
        )

        val theft = Theft(attributes)
        softly.assertThat(theft.interiorProtectionStatus.first).isEqualTo(interiorProtectionStatus)
        softly.assertThat(theft.theftAlarmActive.first).isEqualTo(theftAlarmActive)
        softly.assertThat(theft.theftSystemArmed.first).isEqualTo(theftSystemArmed)
        softly.assertThat(theft.towProtectionSensorStatus.first).isEqualTo(towProtectionSensorStatus)
        softly.assertThat(theft.lastTheftWarningReason.first).isEqualTo(lastTheftWarningReason)
        softly.assertThat(theft.lastTheftWarning.first).isEqualTo(lastTheftWarning)
        softly.assertThat(theft.keyActivationState.first).isEqualTo(keyActivationState)
    }

    @ParameterizedTest
    @EnumSource(ActiveSelectionState::class)
    fun `theft activeSelectionState mapping`(
        state: ActiveSelectionState,
        softly: SoftAssertions
    ) {
        val attributes = mapOf(
            ApiVehicleKey.INTERIOR_PROTECTION_SENSOR_STATE.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.TOW_PROTECTION_SENSOR_STATE.id to VehicleEventUtil.createAttribute(state.id)
        )
        val theft = Theft(attributes)
        softly.assertThat(theft.interiorProtectionStatus.first).isEqualTo(state)
        softly.assertThat(theft.towProtectionSensorStatus.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(TheftWarningReasonState::class)
    fun `theft lastTheftWarningReason mapping`(
        state: TheftWarningReasonState,
        softly: SoftAssertions
    ) {
        val theft = Theft(mapOf(ApiVehicleKey.LAST_THEFT_WARNING_REASON.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(theft.lastTheftWarningReason.first).isEqualTo(state)
    }
}
