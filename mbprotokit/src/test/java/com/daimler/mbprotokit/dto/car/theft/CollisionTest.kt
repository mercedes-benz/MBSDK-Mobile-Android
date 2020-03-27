package com.daimler.mbprotokit.dto.car.theft

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
class CollisionTest {

    @Test
    fun `collision full random mapping`(softly: SoftAssertions) {
        val lastParkEvent = Random.nextLong()
        val lastParkEventNotConfirmed = Random.nextBoolean()
        val parkEventSensorStatus = random<ActiveSelectionState>()
        val parkEventLevel = random<ParkEventLevel>()
        val parkEventType = random<ParkEventType>()

        val attributes = mapOf(
            ApiVehicleKey.LAST_PARK_EVENT.id to VehicleEventUtil.createAttribute(lastParkEvent),
            ApiVehicleKey.LAST_PARK_EVENT_NOT_CONFIRMED.id to VehicleEventUtil.createAttribute(lastParkEventNotConfirmed),
            ApiVehicleKey.PARK_EVENT_SENSOR_STATUS.id to VehicleEventUtil.createAttribute(parkEventSensorStatus.id),
            ApiVehicleKey.PARK_EVENT_LEVEL.id to VehicleEventUtil.createAttribute(parkEventLevel.id),
            ApiVehicleKey.PARK_EVENT_TYPE.id to VehicleEventUtil.createAttribute(parkEventType.id)
        )

        val collision = Collision(attributes)

        softly.assertThat(collision.lastParkEvent.first).isEqualTo(lastParkEvent)
        softly.assertThat(collision.lastParkEventNotConfirmed.first).isEqualTo(lastParkEventNotConfirmed)
        softly.assertThat(collision.parkEventSensorStatus.first).isEqualTo(parkEventSensorStatus)
        softly.assertThat(collision.parkEventLevel.first).isEqualTo(parkEventLevel)
        softly.assertThat(collision.parkEventType.first).isEqualTo(parkEventType)
    }

    @ParameterizedTest
    @EnumSource(ActiveSelectionState::class)
    fun `collision parkEventSensorStatus mapping`(
        state: ActiveSelectionState,
        softly: SoftAssertions
    ) {
        val collision = Collision(mapOf(ApiVehicleKey.PARK_EVENT_SENSOR_STATUS.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(collision.parkEventSensorStatus.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(ParkEventLevel::class)
    fun `collision parkEventLevel mapping`(
        state: ParkEventLevel,
        softly: SoftAssertions
    ) {
        val collision = Collision(mapOf(ApiVehicleKey.PARK_EVENT_LEVEL.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(collision.parkEventLevel.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(ParkEventType::class)
    fun `collision parkEventType mapping`(
        state: ParkEventType,
        softly: SoftAssertions
    ) {
        val collision = Collision(mapOf(ApiVehicleKey.PARK_EVENT_TYPE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(collision.parkEventType.first).isEqualTo(state)
    }
}
