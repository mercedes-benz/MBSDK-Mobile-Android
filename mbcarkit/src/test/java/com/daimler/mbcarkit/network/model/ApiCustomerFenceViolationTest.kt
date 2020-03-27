package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiCustomerFenceViolationTest {

    @Test
    fun `map ApiCustomerFenceViolation to CustomerFenceViolation`(softly: SoftAssertions) {
        val apiCustomerFence = ApiCustomerFence(
            0,
            1,
            "name",
            listOf(ApiDayOfWeek.MONDAY, ApiDayOfWeek.TUESDAY, ApiDayOfWeek.WEDNESDAY),
            4,
            5,
            6L,
            ApiCustomerFenceViolationType.ENTER
        )
        val apiOnboardFence = ApiOnboardFence(
            0,
            "name",
            true,
            ApiGeoCoordinates(1.0, 2.0),
            ApiFenceType.CIRCLE,
            2,
            3,
            listOf(ApiGeoCoordinates(3.0, 4.0)),
            ApiSyncStatus.FAILED
        )
        val apiCustomerFenceViolation = ApiCustomerFenceViolation(
            0,
            1,
            ApiGeoCoordinates(1.0, 2.0),
            apiCustomerFence,
            apiOnboardFence
        )
        val customerFenceViolation = apiCustomerFenceViolation.toCustomerFenceViolation()

        softly.assertThat(customerFenceViolation.violationId).isEqualTo(apiCustomerFenceViolation.violationId)
        softly.assertThat(customerFenceViolation.timestamp).isEqualTo(apiCustomerFenceViolation.timestamp)
        softly.assertThat(customerFenceViolation.coordinates?.latitude).isEqualTo(apiCustomerFenceViolation.coordinates?.latitude)
        softly.assertThat(customerFenceViolation.coordinates?.longitude).isEqualTo(apiCustomerFenceViolation.coordinates?.longitude)
        softly.assertThat(customerFenceViolation.customerFence?.customerFenceId).isEqualTo(apiCustomerFenceViolation.customerFence?.customerFenceId)
        softly.assertThat(customerFenceViolation.onboardFence?.geoFenceId).isEqualTo(apiCustomerFenceViolation.onboardFence?.geoFenceId)
    }
}
