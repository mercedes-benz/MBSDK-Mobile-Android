package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiSpeedFenceViolationTest {

    @Test
    fun `map ApiSpeedFenceViolation to SpeedFenceViolation`(softly: SoftAssertions) {
        val apiSpeedFenceViolation = ApiSpeedFenceViolation(
            1,
            2L,
            ApiGeoCoordinates(0.0, 1.0),
            ApiSpeedFence(
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
        val speedFenceViolation = apiSpeedFenceViolation.toSpeedFenceViolation()

        softly.assertThat(speedFenceViolation.violationId).isEqualTo(apiSpeedFenceViolation.violationId)
        softly.assertThat(speedFenceViolation.time).isEqualTo(apiSpeedFenceViolation.time)
        softly.assertThat(speedFenceViolation.coordinates?.latitude).isEqualTo(apiSpeedFenceViolation.coordinates?.latitude)
        softly.assertThat(speedFenceViolation.coordinates?.longitude).isEqualTo(apiSpeedFenceViolation.coordinates?.longitude)
        softly.assertThat(speedFenceViolation.speedFence?.isActive).isEqualTo(apiSpeedFenceViolation.speedFence?.isActive)
    }
}
