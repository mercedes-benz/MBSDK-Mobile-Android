package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiConsumptionDataTest {

    @Test
    fun `map ApiConsumptionData to ConsumptionData`(softly: SoftAssertions) {
        val apiConsumptionValue = ApiConsumptionValue(0.0, 1, 2.0, ApiVehicleConsumptionUnit.KM_PER_LITER)
        val apiConsumptionData = ApiConsumptionData(true, listOf(apiConsumptionValue))
        val consumptionData = apiConsumptionData.toConsumptionData()

        softly.assertThat(consumptionData.changed).isEqualTo(apiConsumptionData.changed)
        softly.assertThat(consumptionData.value[0].consumption).isEqualTo(apiConsumptionData.value[0].consumption)
        softly.assertThat(consumptionData.value[0].group).isEqualTo(apiConsumptionData.value[0].group)
        softly.assertThat(consumptionData.value[0].percentage).isEqualTo(apiConsumptionData.value[0].percentage)
        softly.assertThat(consumptionData.value[0].unit?.name).isEqualTo(apiConsumptionData.value[0].unit?.name)
    }
}
