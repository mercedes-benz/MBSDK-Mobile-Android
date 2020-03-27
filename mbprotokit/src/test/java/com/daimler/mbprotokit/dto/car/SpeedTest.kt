package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class SpeedTest {

    @Test
    fun `speed full random mapping`(softly: SoftAssertions) {
        val averageSpeedReset = Random.nextDouble()
        val averageSpeedStart = Random.nextDouble()

        val attributes = mapOf(
            ApiVehicleKey.AVERAGE_SPEED_RESET.id to VehicleEventUtil.createAttribute(averageSpeedReset),
            ApiVehicleKey.AVERAGE_SPEED_START.id to VehicleEventUtil.createAttribute(averageSpeedStart)
        )

        val speed = Speed(attributes)
        softly.assertThat(speed.averageSpeedReset.first).isEqualTo(averageSpeedReset)
        softly.assertThat(speed.averageSpeedStart.first).isEqualTo(averageSpeedStart)
    }
}
