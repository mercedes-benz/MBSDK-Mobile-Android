package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiValetprotectViolationTest {

    @Test
    fun `map ApiValetprotectViolation to ValetprotectViolation`(softly: SoftAssertions) {
        val apiValetProtectViolation = ApiValetprotectViolation(
            0,
            ApiValetprotectViolationType.IGNITION_OFF,
            1L,
            ApiGeoCoordinates(
                0.0,
                1.0
            ),
            ApiValetprotectItem(
                "",
                emptyList(),
                ApiGeoCoordinates(
                    0.0,
                    1.0
                ),
                ApiValetprotectRadius(
                    0.0,
                    ApiDistanceUnit.KM
                )
            )
        )
        val valetProtectViolation = apiValetProtectViolation.toValetprotectViolation()

        softly.assertThat(valetProtectViolation.id).isEqualTo(apiValetProtectViolation.id)
        softly.assertThat(valetProtectViolation.violationtype.name).isEqualTo(apiValetProtectViolation.violationtype?.name)
        softly.assertThat(valetProtectViolation.time).isEqualTo(apiValetProtectViolation.time)
        softly.assertThat(valetProtectViolation.coordinate.latitude).isEqualTo(apiValetProtectViolation.coordinate?.latitude)
        softly.assertThat(valetProtectViolation.coordinate.longitude).isEqualTo(apiValetProtectViolation.coordinate?.longitude)
        softly.assertThat(valetProtectViolation.snapshot.name).isEqualTo(apiValetProtectViolation.snapshot.name)
        softly.assertThat(valetProtectViolation.snapshot.violationtypes).isEqualTo(apiValetProtectViolation.snapshot.violationtypes)
        softly.assertThat(valetProtectViolation.snapshot.center.latitude).isEqualTo(apiValetProtectViolation.snapshot.center?.latitude)
        softly.assertThat(valetProtectViolation.snapshot.center.longitude).isEqualTo(apiValetProtectViolation.snapshot.center?.longitude)
        softly.assertThat(valetProtectViolation.snapshot.radius.value).isEqualTo(apiValetProtectViolation.snapshot.radius?.value)
        softly.assertThat(valetProtectViolation.snapshot.radius.unit.name).isEqualTo(apiValetProtectViolation.snapshot.radius?.unit?.name)
    }
}
