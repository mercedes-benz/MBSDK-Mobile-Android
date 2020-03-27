package com.daimler.mbcarkit.utils

import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.testutils.random.randomEnumValue
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
internal class VehicleAttributeTestUtilsTest {

    @Test
    fun `randomVehicleAttribute() should contain value and unit`(softly: SoftAssertions) {
        val value = Random.nextInt()
        val case = randomEnumValue<DisplayUnitCase>()
        val unit = randomEnumValue<DistanceUnit>()
        val attribute = randomVehicleAttribute(value, case, unit)
        softly.assertThat(attribute.value).isEqualTo(value)
        softly.assertThat(attribute.unit).isNotNull
        softly.assertThat(attribute.unit?.displayUnitCase).isEqualTo(case)
        softly.assertThat(attribute.unit?.displayUnit).isEqualTo(unit)
        softly.assertThat(attribute.unit?.displayValue).isEqualTo(value.toString())
    }

    @Test
    fun `randomVehicleAttribute() should contain date`(softly: SoftAssertions) {
        softly.assertThat(randomVehicleAttribute<Int, DistanceUnit>().lastChanged).isNotNull
    }

    @Test
    fun `randomVehicleAttribute() should generate value for int and double`(softly: SoftAssertions) {
        softly.assertThat(randomVehicleAttribute<Int, DistanceUnit>().value).isNotNull
        softly.assertThat(randomVehicleAttribute<Double, DistanceUnit>().value).isNotNull
        softly.assertThat(randomVehicleAttribute<String, DistanceUnit>().value).isNull()
    }
}
