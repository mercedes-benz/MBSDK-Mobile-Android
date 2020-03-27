package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.customerfence.CustomerFence
import com.daimler.mbcarkit.business.model.customerfence.CustomerFenceViolationType
import com.daimler.mbcarkit.business.model.vehicle.Day
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiCustomerFenceTest {

    @Test
    fun `map ApiCustomerFence from CustomerFence`(softly: SoftAssertions) {
        val customerFence = CustomerFence(
            0,
            1,
            "name",
            listOf(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY),
            4,
            5,
            6L,
            CustomerFenceViolationType.ENTER
        )
        val apiCustomerFence = ApiCustomerFence.fromCustomerFence(customerFence)

        softly.assertThat(apiCustomerFence.customerFenceId).isEqualTo(customerFence.customerFenceId)
        softly.assertThat(apiCustomerFence.geoFenceId).isEqualTo(customerFence.geoFenceId)
        softly.assertThat(apiCustomerFence.name).isEqualTo(customerFence.name)
        softly.assertThat(apiCustomerFence.days.size).isEqualTo(customerFence.days.size)
        softly.assertThat(apiCustomerFence.days[0].name).isEqualTo(customerFence.days[0].name)
        softly.assertThat(apiCustomerFence.days[1].name).isEqualTo(customerFence.days[1].name)
        softly.assertThat(apiCustomerFence.days[2].name).isEqualTo(customerFence.days[2].name)
        softly.assertThat(apiCustomerFence.beginMinutes).isEqualTo(customerFence.beginMinutes)
        softly.assertThat(apiCustomerFence.endMinutes).isEqualTo(customerFence.endMinutes)
        softly.assertThat(apiCustomerFence.timestamp).isEqualTo(customerFence.timestamp)
        softly.assertThat(apiCustomerFence.violationType?.name).isEqualTo(customerFence.violationType?.name)
    }

    @Test
    fun `map ApiCustomerFence to CustomerFence`(softly: SoftAssertions) {
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
        val customerFence = apiCustomerFence.toCustomerFence()

        softly.assertThat(customerFence.customerFenceId).isEqualTo(apiCustomerFence.customerFenceId)
        softly.assertThat(customerFence.geoFenceId).isEqualTo(apiCustomerFence.geoFenceId)
        softly.assertThat(customerFence.name).isEqualTo(apiCustomerFence.name)
        softly.assertThat(customerFence.days.size).isEqualTo(apiCustomerFence.days.size)
        softly.assertThat(customerFence.days[0].name).isEqualTo(apiCustomerFence.days[0].name)
        softly.assertThat(customerFence.days[1].name).isEqualTo(apiCustomerFence.days[1].name)
        softly.assertThat(customerFence.days[2].name).isEqualTo(apiCustomerFence.days[2].name)
        softly.assertThat(customerFence.beginMinutes).isEqualTo(apiCustomerFence.beginMinutes)
        softly.assertThat(customerFence.endMinutes).isEqualTo(apiCustomerFence.endMinutes)
        softly.assertThat(customerFence.timestamp).isEqualTo(apiCustomerFence.timestamp)
        softly.assertThat(customerFence.violationType?.name).isEqualTo(apiCustomerFence.violationType?.name)
    }
}
