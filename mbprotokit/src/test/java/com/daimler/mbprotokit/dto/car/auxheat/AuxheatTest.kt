package com.daimler.mbprotokit.dto.car.auxheat

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
class AuxheatTest {

    @Test
    fun `auxheat full random mapping`(softly: SoftAssertions) {
        val activeState = Random.nextBoolean()
        val runtime = Random.nextInt()
        val state = random<AuxheatState>()
        val time1 = Random.nextInt()
        val time2 = Random.nextInt()
        val time3 = Random.nextInt()
        val timeSelection = random<AuxheatTimeSelectionState>()
        val warning = Random.nextInt()

        val attributes = mapOf(
            ApiVehicleKey.AUXHEAT_ACTIVE.id to VehicleEventUtil.createAttribute(activeState),
            ApiVehicleKey.AUXHEAT_RUNTIME.id to VehicleEventUtil.createAttribute(runtime),
            ApiVehicleKey.AUXHEAT_STATUS.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.AUXHEAT_TIME_1.id to VehicleEventUtil.createAttribute(time1),
            ApiVehicleKey.AUXHEAT_TIME_2.id to VehicleEventUtil.createAttribute(time2),
            ApiVehicleKey.AUXHEAT_TIME_3.id to VehicleEventUtil.createAttribute(time3),
            ApiVehicleKey.AUXHEAT_TIME_SELECTION.id to VehicleEventUtil.createAttribute(timeSelection.id),
            ApiVehicleKey.AUXHEAT_WARNINGS.id to VehicleEventUtil.createAttribute(warning)
        )

        val auxheat = Auxheat(attributes)

        softly.assertThat(auxheat.activeState.first).isEqualTo(activeState)
        softly.assertThat(auxheat.runtime.first).isEqualTo(runtime)
        softly.assertThat(auxheat.state.first).isEqualTo(state)
        softly.assertThat(auxheat.time1.first).isEqualTo(time1)
        softly.assertThat(auxheat.time2.first).isEqualTo(time2)
        softly.assertThat(auxheat.time3.first).isEqualTo(time3)
        softly.assertThat(auxheat.timeSelection.first).isEqualTo(timeSelection)
        softly.assertThat(auxheat.warnings.first).isEqualTo(warning)
    }

    @ParameterizedTest
    @EnumSource(AuxheatState::class)
    fun `auxheat state mapping`(state: AuxheatState, softly: SoftAssertions) {
        val auxheat = Auxheat(mapOf(ApiVehicleKey.AUXHEAT_STATUS.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(auxheat.state.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(AuxheatTimeSelectionState::class)
    fun `auxheat timeSelection mapping`(state: AuxheatTimeSelectionState, softly: SoftAssertions) {
        val auxheat = Auxheat(mapOf(ApiVehicleKey.AUXHEAT_TIME_SELECTION.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(auxheat.timeSelection.first).isEqualTo(state)
    }
}
