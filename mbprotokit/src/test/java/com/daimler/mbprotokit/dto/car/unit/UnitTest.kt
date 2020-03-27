package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@ExtendWith(SoftAssertionsExtension::class)
internal class UnitTest {

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.ClockHourUnit::class)
    fun `clock hour unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.ClockHourUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(ClockHourUnit.map(unit).clockHourUnit).isEqualTo(unit)
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.CombustionConsumptionUnit::class)
    fun `combustion consumption unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.CombustionConsumptionUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(CombustionConsumptionUnit.map(unit).consumptionUnit).isEqualTo(unit)
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.DistanceUnit::class)
    fun `distance unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.DistanceUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(DistanceUnit.map(unit).distanceUnit).isEqualTo(unit)
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.ElectricityConsumptionUnit::class)
    fun `electricity consumption unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.ElectricityConsumptionUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(ElectricityConsumptionUnit.map(unit).electricityConsumptionUnit)
            .isEqualTo(unit)
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.GasConsumptionUnit::class)
    fun `gas consumption unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.GasConsumptionUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(GasConsumptionUnit.map(unit).gasConsumptionUnit)
            .isEqualTo(unit)
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.PressureUnit::class)
    fun `pressure unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.PressureUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(PressureUnit.map(unit).pressureUnit)
            .isEqualTo(unit)
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.RatioUnit::class)
    fun `ratio unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.RatioUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(RatioUnit.map(unit).ratioUnit)
            .isEqualTo(unit)
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.SpeedUnit::class)
    fun `speed distance unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.SpeedUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(SpeedUnit.map(unit).speedUnit).isEqualTo(unit)
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.VehicleAttributeStatus.TemperatureUnit::class)
    fun `temperatur unit mapping`(
        unit: VehicleEvents.VehicleAttributeStatus.TemperatureUnit,
        softly: SoftAssertions
    ) {
        softly.assertThat(TemperatureUnit.map(unit).temperatureUnit).isEqualTo(unit)
    }

    @ParameterizedTest
    @MethodSource("displayUnitCaseProvider")
    fun `display unit case mapping`(
        case: VehicleEvents.VehicleAttributeStatus.DisplayUnitCase,
        softly: SoftAssertions
    ) {
        softly.assertThat(UnitCase.map(case).displayUnitCase).isEqualTo(case)
    }

    companion object {
        // custom provider to filter out deprecated SPEED_DISTANCE_UNIT which was removed in productive code
        @JvmStatic
        private fun displayUnitCaseProvider(): Stream<out Arguments> =
            VehicleEvents.VehicleAttributeStatus.DisplayUnitCase.values()
                .filterNot { it == VehicleEvents.VehicleAttributeStatus.DisplayUnitCase.SPEED_DISTANCE_UNIT }
                .map {
                    Arguments { arrayOf(it) }
                }.stream()
    }
}
