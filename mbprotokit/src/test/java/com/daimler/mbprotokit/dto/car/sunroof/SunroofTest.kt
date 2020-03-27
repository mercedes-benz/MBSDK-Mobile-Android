package com.daimler.mbprotokit.dto.car.sunroof

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
class SunroofTest {

    @Test
    fun `position full random mapping`(softly: SoftAssertions) {
        val eventState = random<SunroofEventState>()
        val eventActive = Random.nextBoolean()
        val state = random<SunroofState>()
        val blindState = random<SunroofBlindState>()

        val attributes = mapOf(
            ApiVehicleKey.SUNROOF_EVENT.id to VehicleEventUtil.createAttribute(eventState.id),
            ApiVehicleKey.SUNROOF_EVENT_ACTIVE.id to VehicleEventUtil.createAttribute(eventActive),
            ApiVehicleKey.SUNROOF_STATUS.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.SUNROOF_BLIND_STATUS_FRONT.id to VehicleEventUtil.createAttribute(blindState.id),
            ApiVehicleKey.SUNROOF_BLIND_STATUS_REAR.id to VehicleEventUtil.createAttribute(blindState.id)
        )

        val sunroof = Sunroof(attributes)
        softly.assertThat(sunroof.eventState.first).isEqualTo(eventState)
        softly.assertThat(sunroof.eventActive.first).isEqualTo(eventActive)
        softly.assertThat(sunroof.state.first).isEqualTo(state)
        softly.assertThat(sunroof.blindFrontState.first).isEqualTo(blindState)
        softly.assertThat(sunroof.blindRearState.first).isEqualTo(blindState)
    }

    @ParameterizedTest
    @EnumSource(SunroofEventState::class)
    fun `sunroof eventState mapping`(state: SunroofEventState, softly: SoftAssertions) {
        val sunroof =
            Sunroof(mapOf(ApiVehicleKey.SUNROOF_EVENT.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(sunroof.eventState.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(SunroofState::class)
    fun `sunroof state mapping`(state: SunroofState, softly: SoftAssertions) {
        val sunroof =
            Sunroof(mapOf(ApiVehicleKey.SUNROOF_STATUS.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(sunroof.state.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(SunroofBlindState::class)
    fun `sunroof blind state mapping`(state: SunroofBlindState, softly: SoftAssertions) {
        val sunroof = Sunroof(
            mapOf(
                ApiVehicleKey.SUNROOF_BLIND_STATUS_FRONT.id to VehicleEventUtil.createAttribute(
                    state.id
                ),
                ApiVehicleKey.SUNROOF_BLIND_STATUS_REAR.id to VehicleEventUtil.createAttribute(state.id)
            )
        )
        softly.assertThat(sunroof.blindFrontState.first).isEqualTo(state)
        softly.assertThat(sunroof.blindRearState.first).isEqualTo(state)
    }
}
