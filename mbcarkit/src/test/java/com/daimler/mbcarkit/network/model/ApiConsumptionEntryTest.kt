package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiConsumptionEntryTest {

    @Test
    fun `map ApiConsumptionEntry to ConsumptionEntry`(softly: SoftAssertions) {
        val apiConsumptionEntry = ApiConsumptionEntry(true, 0.0, ApiVehicleConsumptionUnit.KM_PER_LITER)
        val consumptionEntry = apiConsumptionEntry.toConsumptionEntry()

        softly.assertThat(consumptionEntry.changed).isEqualTo(apiConsumptionEntry.changed)
        softly.assertThat(consumptionEntry.value).isEqualTo(apiConsumptionEntry.value)
        softly.assertThat(consumptionEntry.unit.name).isEqualTo(apiConsumptionEntry.unit?.name)
    }
}
