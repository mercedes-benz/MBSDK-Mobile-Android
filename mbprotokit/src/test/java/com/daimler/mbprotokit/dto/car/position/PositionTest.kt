package com.daimler.mbprotokit.dto.car.position

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
class PositionTest {

    @Test
    fun `position full random mapping`(softly: SoftAssertions) {
        val heading = Random.nextDouble()
        val latitude = Random.nextDouble()
        val longitude = Random.nextDouble()
        val status = random<VehicleLocationErrorState>()
        val proximityCalculationRequired = Random.nextBoolean()

        val attributes = mapOf(
            ApiVehicleKey.POSITION_HEADING.id to VehicleEventUtil.createAttribute(heading),
            ApiVehicleKey.POSITION_LAT.id to VehicleEventUtil.createAttribute(latitude),
            ApiVehicleKey.POSITION_LONG.id to VehicleEventUtil.createAttribute(longitude),
            ApiVehicleKey.POSITION_ERROR_CODE.id to VehicleEventUtil.createAttribute(status.id),
            ApiVehicleKey.PROXIMITY_CALCULATION_POSITION_REQUIRED.id to VehicleEventUtil.createAttribute(proximityCalculationRequired)
        )

        val position = Position(attributes)

        softly.assertThat(position.heading.first).isEqualTo(heading)
        softly.assertThat(position.latitude.first).isEqualTo(latitude)
        softly.assertThat(position.longitude.first).isEqualTo(longitude)
        softly.assertThat(position.errorCode.first).isEqualTo(status)
        softly.assertThat(position.proximityCalculationRequired.first).isEqualTo(proximityCalculationRequired)
    }

    @ParameterizedTest
    @EnumSource(VehicleLocationErrorState::class)
    fun `position errorCode mapping`(state: VehicleLocationErrorState, softly: SoftAssertions) {
        val position = Position(mapOf(ApiVehicleKey.POSITION_ERROR_CODE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(position.errorCode.first).isEqualTo(state)
    }
}
