package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class DrivenTimeTest {

    @Test
    fun `drivenTime full random mapping`(softly: SoftAssertions) {
        val timeReset = Random.nextInt()
        val timeStart = Random.nextInt()
        val zeTimeReset = Random.nextDouble()
        val zeTimeStart = Random.nextDouble()

        val attribute = mapOf(
            ApiVehicleKey.DRIVEN_TIME_RESET.id to VehicleEventUtil.createAttribute(timeReset),
            ApiVehicleKey.DRIVEN_TIME_START.id to VehicleEventUtil.createAttribute(timeStart),
            ApiVehicleKey.DRIVEN_TIME_ZERESET.id to VehicleEventUtil.createAttribute(zeTimeReset),
            ApiVehicleKey.DRIVEN_TIME_ZESTART.id to VehicleEventUtil.createAttribute(zeTimeStart)
        )

        val drivenTime = DrivenTime(attribute)

        softly.assertThat(drivenTime.timeReset.first).isEqualTo(timeReset)
        softly.assertThat(drivenTime.timeStart.first).isEqualTo(timeStart)
        softly.assertThat(drivenTime.zeTimeReset.first).isEqualTo(zeTimeReset)
        softly.assertThat(drivenTime.zeTimeStart.first).isEqualTo(zeTimeStart)
    }
}
