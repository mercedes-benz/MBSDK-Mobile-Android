package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class ConsumptionTest {

    @Test
    fun `consumption full random mapping`(softly: SoftAssertions) {
        val electricConsumptionReset = Random.nextDouble()
        val electricConsumptionStart = Random.nextDouble()
        val gasConsumptionReset = Random.nextDouble()
        val gasConsumptionStart = Random.nextDouble()
        val liquidConsumptionReset = Random.nextDouble()
        val liquidConsumptionStart = Random.nextDouble()

        val attributes = mapOf(
            ApiVehicleKey.ELECTRIC_CONSUMPTION_RESET.id to VehicleEventUtil.createAttribute(electricConsumptionReset),
            ApiVehicleKey.ELECTRIC_CONSUMPTION_START.id to VehicleEventUtil.createAttribute(electricConsumptionStart),
            ApiVehicleKey.GAS_CONSUMPTION_RESET.id to VehicleEventUtil.createAttribute(gasConsumptionReset),
            ApiVehicleKey.GAS_CONSUMPTION_START.id to VehicleEventUtil.createAttribute(gasConsumptionStart),
            ApiVehicleKey.LIQUID_CONSUMPTION_RESET.id to VehicleEventUtil.createAttribute(liquidConsumptionReset),
            ApiVehicleKey.LIQUID_CONSUMPTION_START.id to VehicleEventUtil.createAttribute(liquidConsumptionStart)
        )

        val consumption = Consumption(attributes)
        softly.assertThat(consumption.electricConsumptionReset.first).isEqualTo(electricConsumptionReset)
        softly.assertThat(consumption.electricConsumptionStart.first).isEqualTo(electricConsumptionStart)
        softly.assertThat(consumption.gasConsumptionReset.first).isEqualTo(gasConsumptionReset)
        softly.assertThat(consumption.gasConsumptionStart.first).isEqualTo(gasConsumptionStart)
        softly.assertThat(consumption.liquidConsumptionReset.first).isEqualTo(liquidConsumptionReset)
        softly.assertThat(consumption.liquidConsumptionStart.first).isEqualTo(liquidConsumptionStart)
    }
}
