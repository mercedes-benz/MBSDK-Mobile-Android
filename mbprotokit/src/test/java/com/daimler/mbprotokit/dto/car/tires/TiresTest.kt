package com.daimler.mbprotokit.dto.car.tires

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
class TiresTest {

    @Test
    fun `tires full random mapping`(softly: SoftAssertions) {
        val tirePressure = Random.nextDouble()
        val tireMarkerState = random<TireMarkerState>()
        val tireSensorAvailable = random<TireSensorState>()
        val warningTireSrdk = random<TireSrdkState>()
        val tirePressureMeasurementTimestamp = Random.nextLong()
        val attributes = mapOf(
            ApiVehicleKey.TIRE_PRESSURE_FRONT_LEFT.id to VehicleEventUtil.createAttribute(tirePressure),
            ApiVehicleKey.TIRE_PRESSURE_FRONT_RIGHT.id to VehicleEventUtil.createAttribute(tirePressure),
            ApiVehicleKey.TIRE_PRESSURE_REAR_LEFT.id to VehicleEventUtil.createAttribute(tirePressure),
            ApiVehicleKey.TIRE_PRESSURE_REAR_RIGHT.id to VehicleEventUtil.createAttribute(tirePressure),
            ApiVehicleKey.TIRE_MARKER_FRONT_LEFT.id to VehicleEventUtil.createAttribute(tireMarkerState.id),
            ApiVehicleKey.TIRE_MARKER_FRONT_RIGHT.id to VehicleEventUtil.createAttribute(tireMarkerState.id),
            ApiVehicleKey.TIRE_MARKER_REAR_LEFT.id to VehicleEventUtil.createAttribute(tireMarkerState.id),
            ApiVehicleKey.TIRE_MARKER_REAR_RIGHT.id to VehicleEventUtil.createAttribute(tireMarkerState.id),
            ApiVehicleKey.TIRE_SENSOR_AVAILABLE.id to VehicleEventUtil.createAttribute(tireSensorAvailable.id),
            ApiVehicleKey.TIRE_WARNING_SRDK.id to VehicleEventUtil.createAttribute(warningTireSrdk.id),
            ApiVehicleKey.TIRE_PRESSURE_MEASUREMENT_TIMESTAMP.id to VehicleEventUtil.createAttribute(tirePressureMeasurementTimestamp)
        )

        val tires = Tires(attributes)

        softly.assertThat(tires.tirePressureFrontLeft.first).isEqualTo(tirePressure)
        softly.assertThat(tires.tirePressureFrontRight.first).isEqualTo(tirePressure)
        softly.assertThat(tires.tirePressureRearLeft.first).isEqualTo(tirePressure)
        softly.assertThat(tires.tirePressureRearRight.first).isEqualTo(tirePressure)
        softly.assertThat(tires.tireMarkerFrontLeft.first).isEqualTo(tireMarkerState)
        softly.assertThat(tires.tireMarkerFrontRight.first).isEqualTo(tireMarkerState)
        softly.assertThat(tires.tireMarkerRearLeft.first).isEqualTo(tireMarkerState)
        softly.assertThat(tires.tireMarkerRearRight.first).isEqualTo(tireMarkerState)
        softly.assertThat(tires.tireSensorAvailable.first).isEqualTo(tireSensorAvailable)
        softly.assertThat(tires.warningTireSrdk.first).isEqualTo(warningTireSrdk)
    }

    @ParameterizedTest
    @EnumSource(TireMarkerState::class)
    fun `tires tireMarkerState mapping`(
        state: TireMarkerState,
        softly: SoftAssertions
    ) {
        val attributes = mapOf(
            ApiVehicleKey.TIRE_MARKER_FRONT_LEFT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.TIRE_MARKER_FRONT_RIGHT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.TIRE_MARKER_REAR_LEFT.id to VehicleEventUtil.createAttribute(state.id),
            ApiVehicleKey.TIRE_MARKER_REAR_RIGHT.id to VehicleEventUtil.createAttribute(state.id)
        )
        val tires = Tires(attributes)
        softly.assertThat(tires.tireMarkerFrontLeft.first).isEqualTo(state)
        softly.assertThat(tires.tireMarkerFrontRight.first).isEqualTo(state)
        softly.assertThat(tires.tireMarkerRearLeft.first).isEqualTo(state)
        softly.assertThat(tires.tireMarkerRearRight.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(TireSensorState::class)
    fun `tires tireMarkerRearRight mapping`(state: TireSensorState, softly: SoftAssertions) {
        val tires = Tires(mapOf(ApiVehicleKey.TIRE_SENSOR_AVAILABLE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(tires.tireSensorAvailable.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(TireSrdkState::class)
    fun `tires tireMarkerRearRight mapping`(state: TireSrdkState, softly: SoftAssertions) {
        val tires = Tires(mapOf(ApiVehicleKey.TIRE_WARNING_SRDK.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(tires.warningTireSrdk.first).isEqualTo(state)
    }
}
