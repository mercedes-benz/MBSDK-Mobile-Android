package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class DistanceTest {

    @Test
    fun `distance full random mapping`(softly: SoftAssertions) {
        val electricalReset = Random.nextDouble()
        val electricalStart = Random.nextDouble()
        val gasReset = Random.nextDouble()
        val gasStart = Random.nextDouble()
        val liquidReset = Random.nextDouble()
        val liquidStart = Random.nextDouble()
        val zeReset = Random.nextInt()
        val zeStart = Random.nextInt()

        val attributes = mapOf(
            ApiVehicleKey.DISTANCE_ELECTRICAL_RESET.id to VehicleEventUtil.createAttribute(electricalReset),
            ApiVehicleKey.DISTANCE_ELECTRICAL_START.id to VehicleEventUtil.createAttribute(electricalStart),
            ApiVehicleKey.DISTANCE_GAS_RESET.id to VehicleEventUtil.createAttribute(gasReset),
            ApiVehicleKey.DISTANCE_GAS_START.id to VehicleEventUtil.createAttribute(gasStart),
            ApiVehicleKey.DISTANCE_RESET.id to VehicleEventUtil.createAttribute(liquidReset),
            ApiVehicleKey.DISTANCE_START.id to VehicleEventUtil.createAttribute(liquidStart),
            ApiVehicleKey.DISTANCE_ZERESET.id to VehicleEventUtil.createAttribute(zeReset),
            ApiVehicleKey.DISTANCE_ZESTART.id to VehicleEventUtil.createAttribute(zeStart)
        )

        val distance = Distance(attributes)
        softly.assertThat(distance.electricalReset.first).isEqualTo(electricalReset)
        softly.assertThat(distance.electricalStart.first).isEqualTo(electricalStart)
        softly.assertThat(distance.gasReset.first).isEqualTo(gasReset)
        softly.assertThat(distance.gasStart.first).isEqualTo(gasStart)
        softly.assertThat(distance.liquidReset.first).isEqualTo(liquidReset)
        softly.assertThat(distance.liquidStart.first).isEqualTo(liquidStart)
        softly.assertThat(distance.zeReset.first).isEqualTo(zeReset)
        softly.assertThat(distance.zeStart.first).isEqualTo(zeStart)
    }
}
