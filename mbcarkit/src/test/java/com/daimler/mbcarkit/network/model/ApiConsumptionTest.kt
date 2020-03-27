package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiConsumptionTest {

    @Test
    fun `map ApiConsumption to Consumption`(softly: SoftAssertions) {
        val apiConsumption = ApiConsumption(
            ApiConsumptionEntry(true, 0.0, ApiVehicleConsumptionUnit.KM_PER_LITER),
            ApiConsumptionData(true, emptyList()),
            ApiConsumptionEntry(true, 1.0, ApiVehicleConsumptionUnit.KM_PER_LITER),
            ApiConsumptionEntry(true, 2.0, ApiVehicleConsumptionUnit.KM_PER_LITER),
            ApiConsumptionEntry(true, 3.0, ApiVehicleConsumptionUnit.KM_PER_LITER),
            ApiConsumptionEntry(true, 4.0, ApiVehicleConsumptionUnit.KM_PER_LITER),
            ApiIndividual30DaysConsumptionData(5.0, 5L, ApiVehicleConsumptionUnit.KM_PER_LITER)
        )
        val consumption = apiConsumption.toConsumption()

        softly.assertThat(consumption.averageConsumption?.changed).isEqualTo(apiConsumption.averageConsumption?.changed)
        softly.assertThat(consumption.averageConsumption?.value).isEqualTo(apiConsumption.averageConsumption?.value)
        softly.assertThat(consumption.averageConsumption?.unit?.name).isEqualTo(apiConsumption.averageConsumption?.unit?.name)
        softly.assertThat(consumption.consumptionData?.changed).isEqualTo(apiConsumption.consumptionData?.changed)
        softly.assertThat(consumption.consumptionData?.value).isEqualTo(apiConsumption.consumptionData?.value)
        softly.assertThat(consumption.individualLifetimeConsumption?.changed).isEqualTo(apiConsumption.individualLifetimeConsumption?.changed)
        softly.assertThat(consumption.individualLifetimeConsumption?.value).isEqualTo(apiConsumption.individualLifetimeConsumption?.value)
        softly.assertThat(consumption.individualLifetimeConsumption?.unit?.name).isEqualTo(apiConsumption.individualLifetimeConsumption?.unit?.name)
        softly.assertThat(consumption.individualResetConsumption?.changed).isEqualTo(apiConsumption.individualResetConsumption?.changed)
        softly.assertThat(consumption.individualResetConsumption?.value).isEqualTo(apiConsumption.individualResetConsumption?.value)
        softly.assertThat(consumption.individualResetConsumption?.unit?.name).isEqualTo(apiConsumption.individualResetConsumption?.unit?.name)
        softly.assertThat(consumption.individualStartConsumption?.changed).isEqualTo(apiConsumption.individualStartConsumption?.changed)
        softly.assertThat(consumption.individualStartConsumption?.value).isEqualTo(apiConsumption.individualStartConsumption?.value)
        softly.assertThat(consumption.individualStartConsumption?.unit?.name).isEqualTo(apiConsumption.individualStartConsumption?.unit?.name)
        softly.assertThat(consumption.wltpCombined?.changed).isEqualTo(apiConsumption.wltpCombined?.changed)
        softly.assertThat(consumption.wltpCombined?.value).isEqualTo(apiConsumption.wltpCombined?.value)
        softly.assertThat(consumption.wltpCombined?.unit?.name).isEqualTo(apiConsumption.wltpCombined?.unit?.name)
        softly.assertThat(consumption.individual30DaysConsumptionData?.value).isEqualTo(apiConsumption.individual30DaysConsumptionData?.value)
        softly.assertThat(consumption.individual30DaysConsumptionData?.lastUpdated).isEqualTo(apiConsumption.individual30DaysConsumptionData?.lastUpdated)
        softly.assertThat(consumption.individual30DaysConsumptionData?.unit?.name).isEqualTo(apiConsumption.individual30DaysConsumptionData?.unit?.name)
    }
}
