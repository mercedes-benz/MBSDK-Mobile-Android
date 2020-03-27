package com.daimler.mbprotokit.dto.car.windows

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
class WindowsTest {

    @Test
    fun `windows full random mapping`(softly: SoftAssertions) {
        val stateOverall = random<WindowsOverallStatus>()
        val state = random<WindowState>()
        val flipWindowStatus = Random.nextBoolean()
        val blindState = random<WindowBlindState>()

        val attributes = mapOf(
            ApiVehicleKey.WINDOW_STATUS_OVERALL.id to VehicleEventUtil.createAttribute(stateOverall.id),
            ApiVehicleKey.WINDOW_STATUS_FRONT_LEFT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_STATUS_FRONT_RIGHT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_STATUS_REAR_LEFT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_STATUS_REAR_RIGHT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_STATUS_FLIP.id to VehicleEventUtil.createAttribute(flipWindowStatus),
            ApiVehicleKey.WINDOW_BLIND_STATUS_REAR.id to VehicleEventUtil.createAttribute(blindState.id),
            ApiVehicleKey.WINDOW_BLIND_STATUS_REAR_LEFT.id to VehicleEventUtil.createAttribute(blindState.id),
            ApiVehicleKey.WINDOW_BLIND_STATUS_REAR_RIGHT.id to VehicleEventUtil.createAttribute(blindState.id)
        )

        val windows = Windows(attributes)
        softly.assertThat(windows.stateOverall.first).isEqualTo(stateOverall)
        softly.assertThat(windows.stateFrontLeft.first).isEqualTo(state)
        softly.assertThat(windows.stateFrontRight.first).isEqualTo(state)
        softly.assertThat(windows.stateRearLeft.first).isEqualTo(state)
        softly.assertThat(windows.stateRearRight.first).isEqualTo(state)
        softly.assertThat(windows.flipWindowStatus.first).isEqualTo(flipWindowStatus)
        softly.assertThat(windows.blindRearState.first).isEqualTo(blindState)
        softly.assertThat(windows.blindRearLeftState.first).isEqualTo(blindState)
        softly.assertThat(windows.blindRearRightState.first).isEqualTo(blindState)
    }

    @ParameterizedTest
    @EnumSource(WindowsOverallStatus::class)
    fun `windows stateOverall mapping`(
        state: WindowsOverallStatus,
        softly: SoftAssertions
    ) {
        val windows = Windows(mapOf(ApiVehicleKey.WINDOW_STATUS_OVERALL.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(windows.stateOverall.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(WindowState::class)
    fun `windows state mapping`(
        state: WindowState,
        softly: SoftAssertions
    ) {
        val attributes = mapOf(
            ApiVehicleKey.WINDOW_STATUS_FRONT_LEFT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_STATUS_FRONT_RIGHT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_STATUS_REAR_LEFT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_STATUS_REAR_RIGHT.id to VehicleEventUtil.createAttribute(state.id),
        )
        val windows = Windows(attributes)
        softly.assertThat(windows.stateFrontLeft.first).isEqualTo(state)
        softly.assertThat(windows.stateFrontRight.first).isEqualTo(state)
        softly.assertThat(windows.stateRearLeft.first).isEqualTo(state)
        softly.assertThat(windows.stateRearRight.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(WindowBlindState::class)
    fun `windows blind state mapping`(
        state: WindowBlindState,
        softly: SoftAssertions
    ) {
        val attributes = mapOf(
            ApiVehicleKey.WINDOW_BLIND_STATUS_REAR.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_BLIND_STATUS_REAR_LEFT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.WINDOW_BLIND_STATUS_REAR_RIGHT.id to VehicleEventUtil.createAttribute(state.id)
        )
        val windows = Windows(attributes)
        softly.assertThat(windows.blindRearState.first).isEqualTo(state)
        softly.assertThat(windows.blindRearLeftState.first).isEqualTo(state)
        softly.assertThat(windows.blindRearRightState.first).isEqualTo(state)
    }
}
