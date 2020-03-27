package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiConsumptionValueTest {

    @Test
    fun `map ApiConsumptionValue to ConsumptionValue`(softly: SoftAssertions) {
        val apiConsumptionValue = ApiConsumptionValue(0.0, 1, 2.0, ApiVehicleConsumptionUnit.KM_PER_LITER)
        val consumptionValue = apiConsumptionValue.toConsumptionValue()

        softly.assertThat(consumptionValue.consumption).isEqualTo(apiConsumptionValue.consumption)
        softly.assertThat(consumptionValue.group).isEqualTo(apiConsumptionValue.group)
        softly.assertThat(consumptionValue.percentage).isEqualTo(apiConsumptionValue.percentage)
        softly.assertThat(consumptionValue.unit.name).isEqualTo(apiConsumptionValue.unit?.name)
    }
}
