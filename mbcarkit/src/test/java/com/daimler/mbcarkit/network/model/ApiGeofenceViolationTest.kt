package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiGeofenceViolationTest {

    @Test
    fun `map ApiGeofenceViolation to GeofenceViolation`(softly: SoftAssertions) {
        val apiGeofenceViolation = ApiGeofenceViolation(
            0,
            ApiGeofenceViolationType.LEAVE,
            1,
            2,
            ApiGeoCoordinates(0.0, 1.0),
            ApiFence(
                0,
                true,
                "",
                ApiActiveTimes(emptyList(), 1, 2),
                null,
                ApiShape(null, null)
            )
        )
        val geofenceViolation = apiGeofenceViolation.toGeofenceViolation()

        softly.assertThat(geofenceViolation.id).isEqualTo(apiGeofenceViolation.id)
        softly.assertThat(geofenceViolation.type?.name).isEqualTo(apiGeofenceViolation.type?.name)
        softly.assertThat(geofenceViolation.fenceId).isEqualTo(apiGeofenceViolation.fenceId)
        softly.assertThat(geofenceViolation.time).isEqualTo(apiGeofenceViolation.time)
        softly.assertThat(geofenceViolation.coordinate.latitude).isEqualTo(apiGeofenceViolation.coordinate.latitude)
        softly.assertThat(geofenceViolation.coordinate.longitude).isEqualTo(apiGeofenceViolation.coordinate.longitude)
        softly.assertThat(geofenceViolation.snapshot.id).isEqualTo(apiGeofenceViolation.snapshot.id)
        softly.assertThat(geofenceViolation.snapshot.isActive).isEqualTo(apiGeofenceViolation.snapshot.isActive)
        softly.assertThat(geofenceViolation.snapshot.name).isEqualTo(apiGeofenceViolation.snapshot.name)
        softly.assertThat(geofenceViolation.snapshot.activeTimes.days).isEqualTo(apiGeofenceViolation.snapshot.activeTimes.days)
        softly.assertThat(geofenceViolation.snapshot.activeTimes.begin).isEqualTo(apiGeofenceViolation.snapshot.activeTimes.begin)
        softly.assertThat(geofenceViolation.snapshot.activeTimes.end).isEqualTo(apiGeofenceViolation.snapshot.activeTimes.end)
        softly.assertThat(geofenceViolation.snapshot.violationType).isEqualTo(apiGeofenceViolation.snapshot.violationType)
        softly.assertThat(geofenceViolation.snapshot.shape.circle).isEqualTo(apiGeofenceViolation.snapshot.shape.circle)
        softly.assertThat(geofenceViolation.snapshot.shape.polygon).isEqualTo(apiGeofenceViolation.snapshot.shape.polygon)
    }
}
