package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.dto.car.tires.TireLampState
import com.daimler.mbprotokit.dto.car.tires.TireLevelPrwState
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class WarningMappingTest {

    @Test
    fun `warning full random mapping`(softly: SoftAssertions) {
        val warningBrakeFluid = Random.nextBoolean()
        val warningBrakeLiningWear = Random.nextBoolean()
        val warningCoolantLevelLow = Random.nextBoolean()
        val electricalRangeSkipIndication = Random.nextBoolean()
        val liquidRangeSkipIndication = Random.nextBoolean()
        val warningEngineLight = Random.nextBoolean()
        val warningLowBattery = Random.nextBoolean()
        val warningTireSprw = Random.nextBoolean()
        val warningWashWater = Random.nextBoolean()

        val attribute = mapOf(
            ApiVehicleKey.WARNING_BRAKE_FLUID.id to VehicleEventUtil.createAttribute(warningBrakeFluid),
            ApiVehicleKey.WARNING_BRAKE_LINING_WEAR.id to VehicleEventUtil.createAttribute(warningBrakeLiningWear),
            ApiVehicleKey.WARNING_COOLANT_LEVEL_LOW.id to VehicleEventUtil.createAttribute(warningCoolantLevelLow),
            ApiVehicleKey.ELECTRIC_RANGE_SKIP_INDICATION.id to VehicleEventUtil.createAttribute(electricalRangeSkipIndication),
            ApiVehicleKey.LIQUID_RANGE_SKIP_INDICATION.id to VehicleEventUtil.createAttribute(liquidRangeSkipIndication),
            ApiVehicleKey.WARNING_ENGINE_LIGHT.id to VehicleEventUtil.createAttribute(warningEngineLight),
            ApiVehicleKey.WARNING_LOW_BATTERY.id to VehicleEventUtil.createAttribute(warningLowBattery),
            ApiVehicleKey.TIRE_WARNING_SPRW.id to VehicleEventUtil.createAttribute(warningTireSprw),
            ApiVehicleKey.WARNING_WASH_WATER.id to VehicleEventUtil.createAttribute(warningWashWater)
        )

        val warning = Warning(attribute)

        softly.assertThat(warning.warningBrakeFluid.first).isEqualTo(warningBrakeFluid)
        softly.assertThat(warning.warningBrakeLiningWear.first).isEqualTo(warningBrakeLiningWear)
        softly.assertThat(warning.warningCoolantLevelLow.first).isEqualTo(warningCoolantLevelLow)
        softly.assertThat(warning.electricalRangeSkipIndication.first).isEqualTo(electricalRangeSkipIndication)
        softly.assertThat(warning.liquidRangeSkipIndication.first).isEqualTo(liquidRangeSkipIndication)
        softly.assertThat(warning.warningEngineLight.first).isEqualTo(warningEngineLight)
        softly.assertThat(warning.warningLowBattery.first).isEqualTo(warningLowBattery)
        softly.assertThat(warning.warningTireSprw.first).isEqualTo(warningTireSprw)
        softly.assertThat(warning.warningWashWater.first).isEqualTo(warningWashWater)
    }

    @ParameterizedTest
    @EnumSource(TireLampState::class)
    fun `warning warningTireLamp mapping`(
        state: TireLampState,
        softly: SoftAssertions
    ) {
        val warning = Warning(mapOf(ApiVehicleKey.TIRE_WARNING_LAMP.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(warning.warningTireLamp.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(TireLevelPrwState::class)
    fun `warning tireLevelPrw mapping`(
        state: TireLevelPrwState,
        softly: SoftAssertions
    ) {
        val warning = Warning(mapOf(ApiVehicleKey.TIRE_WARNING_LEVEL_PRW.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(warning.tireLevelPrw.first).isEqualTo(state)
    }
}
