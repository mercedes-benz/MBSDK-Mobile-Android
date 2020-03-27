package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiSpeedAlertViolationTest {

    @Test
    fun `map ApiSpeedAletViolation to SpeedAlertViolation`(softly: SoftAssertions) {
        val apiSpeedAlertViolation = ApiSpeedAlertViolation(
            "id",
            1234,
            5,
            1234,
            ApiGeoCoordinates(123.0, 456.0)
        )
        val speedAlertViolation = apiSpeedAlertViolation.toSpeedAlertViolation()

        softly.assertThat(speedAlertViolation.id).isEqualTo(apiSpeedAlertViolation.id)
        softly.assertThat(speedAlertViolation.time).isEqualTo(apiSpeedAlertViolation.time)
        softly.assertThat(speedAlertViolation.speedAlertTreshold).isEqualTo(apiSpeedAlertViolation.speedAlertTreshold)
        softly.assertThat(speedAlertViolation.speedAlertEndTime).isEqualTo(apiSpeedAlertViolation.speedAlertEndTime)
        softly.assertThat(speedAlertViolation.coordinate.latitude).isEqualTo(apiSpeedAlertViolation.coordinate.latitude)
        softly.assertThat(speedAlertViolation.coordinate.longitude).isEqualTo(apiSpeedAlertViolation.coordinate.longitude)
    }
}
