package com.daimler.mbprotokit.dto.car.engine

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
class EngineTest {

    @Test
    fun `engine full random mapping`(softly: SoftAssertions) {
        val ignitionState = random<IgnitionState>()
        val engineState = Random.nextBoolean()
        val remoteStartActive = Random.nextBoolean()
        val remoteStartTemperature = Random.nextDouble()
        val remoteStartEndtime = Random.nextLong()

        val attributes = mapOf(
            ApiVehicleKey.IGNITION_STATE.id to VehicleEventUtil.createAttribute(ignitionState.id),
            ApiVehicleKey.ENGINE_STATE.id to VehicleEventUtil.createAttribute(engineState),
            ApiVehicleKey.REMOTE_START_ACTIVE.id to VehicleEventUtil.createAttribute(remoteStartActive),
            ApiVehicleKey.REMOTE_START_TEMPERATURE.id to VehicleEventUtil.createAttribute(remoteStartTemperature),
            ApiVehicleKey.REMOTE_START_ENDTIME.id to VehicleEventUtil.createAttribute(remoteStartEndtime)
        )

        val engine = Engine(attributes)

        softly.assertThat(engine.ignitionState.first).isEqualTo(ignitionState)
        softly.assertThat(engine.engineState.first).isEqualTo(engineState)
        softly.assertThat(engine.remoteStartActive.first).isEqualTo(remoteStartActive)
        softly.assertThat(engine.remoteStartTemperature.first).isEqualTo(remoteStartTemperature)
        softly.assertThat(engine.remoteStartEndtime.first).isEqualTo(remoteStartEndtime)
    }

    @ParameterizedTest
    @EnumSource(IgnitionState::class)
    fun `engine ignitionState mapping`(state: IgnitionState, softly: SoftAssertions) {
        val engine = Engine(mapOf(ApiVehicleKey.IGNITION_STATE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(engine.ignitionState.first).isEqualTo(state)
    }
}
