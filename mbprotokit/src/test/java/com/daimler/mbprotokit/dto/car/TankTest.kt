package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class TankTest {

    @Test
    fun `tank full random mapping`(softly: SoftAssertions) {
        val adBlueLevel = Random.nextInt()
        val electricalLevel = Random.nextInt()
        val electricalRange = Random.nextInt()
        val gasLevel = Random.nextDouble()
        val gasRange = Random.nextDouble()
        val liquidLevel = Random.nextInt()
        val liquidRange = Random.nextInt()
        val overallRange = Random.nextDouble()

        val attribute = mapOf(
            ApiVehicleKey.TANK_LEVEL_ADBLUE.id to VehicleEventUtil.createAttribute(adBlueLevel),
            ApiVehicleKey.SOC.id to VehicleEventUtil.createAttribute(electricalLevel),
            ApiVehicleKey.RANGE_ELECTRIC.id to VehicleEventUtil.createAttribute(electricalRange),
            ApiVehicleKey.GAS_TANK_LEVEL.id to VehicleEventUtil.createAttribute(gasLevel),
            ApiVehicleKey.GAS_TANK_RANGE.id to VehicleEventUtil.createAttribute(gasRange),
            ApiVehicleKey.TANK_LEVEL_PERCENT.id to VehicleEventUtil.createAttribute(liquidLevel),
            ApiVehicleKey.RANGE_LIQUID.id to VehicleEventUtil.createAttribute(liquidRange),
            ApiVehicleKey.OVERALL_RANGE.id to VehicleEventUtil.createAttribute(overallRange)
        )

        val tank = Tank(attribute)

        softly.assertThat(tank.adBlueLevel.first).isEqualTo(adBlueLevel)
        softly.assertThat(tank.electricalLevel.first).isEqualTo(electricalLevel)
        softly.assertThat(tank.electricalRange.first).isEqualTo(electricalRange)
        softly.assertThat(tank.gasLevel.first).isEqualTo(gasLevel)
        softly.assertThat(tank.gasRange.first).isEqualTo(gasRange)
        softly.assertThat(tank.liquidLevel.first).isEqualTo(liquidLevel)
        softly.assertThat(tank.liquidRange.first).isEqualTo(liquidRange)
        softly.assertThat(tank.overallRange.first).isEqualTo(overallRange)
    }
}
