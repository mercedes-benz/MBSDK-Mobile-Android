package com.daimler.mbprotokit.dto.car.drivingmodes

import com.daimler.mbprotokit.dto.car.VehicleEventUtil
import com.daimler.mbprotokit.dto.car.random
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@ExtendWith(SoftAssertionsExtension::class)
class DrivingModesTest {

    @Test
    fun `driving modes random mapping`(softly: SoftAssertions) {
        val state = random<DrivingModeState>()

        val attributes = mapOf(
            ApiVehicleKey.TEENAGE_DRIVING_MODE.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.VALET_DRIVING_MODE.id to VehicleEventUtil.createAttribute(state.id)
        )

        val drivingModes = DrivingModes(attributes)

        softly.assertThat(drivingModes.teenageDrivingMode.first).isEqualTo(state)
        softly.assertThat(drivingModes.valetDrivingMode.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(DrivingModeState::class)
    fun `drivingmode state mapping`(
        state: DrivingModeState,
        softly: SoftAssertions
    ) {
        val attributes = mapOf(
            ApiVehicleKey.TEENAGE_DRIVING_MODE.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.VALET_DRIVING_MODE.id to VehicleEventUtil.createAttribute(state.id)
        )
        val drivingModes = DrivingModes(attributes)
        softly.assertThat(drivingModes.teenageDrivingMode.first).isEqualTo(state)
        softly.assertThat(drivingModes.valetDrivingMode.first).isEqualTo(state)
    }
}
