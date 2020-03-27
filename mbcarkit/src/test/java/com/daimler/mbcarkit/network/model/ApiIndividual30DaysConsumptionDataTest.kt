package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiIndividual30DaysConsumptionDataTest {

    @Test
    fun `map ApiIndividual30DaysConsumptionData to Individual30DaysConsumptionData`(softly: SoftAssertions) {
        val apiIndividual30DaysConsumptionData = ApiIndividual30DaysConsumptionData(0.0, 1L, ApiVehicleConsumptionUnit.LITERS_PER_100KM)
        val individual30DaysConsumptionData = apiIndividual30DaysConsumptionData.toIndividual30DaysConsumptionData()

        softly.assertThat(individual30DaysConsumptionData.value).isEqualTo(apiIndividual30DaysConsumptionData.value)
        softly.assertThat(individual30DaysConsumptionData.lastUpdated).isEqualTo(apiIndividual30DaysConsumptionData.lastUpdated)
        softly.assertThat(individual30DaysConsumptionData.unit.name).isEqualTo(apiIndividual30DaysConsumptionData.unit?.name)
    }
}
