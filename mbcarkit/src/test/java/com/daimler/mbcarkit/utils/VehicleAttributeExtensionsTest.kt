package com.daimler.mbcarkit.utils

import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.VehicleAttribute
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.util.isUpdatedBy
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(SoftAssertionsExtension::class)
class VehicleAttributeExtensionsTest {
    private val attribute1 = VehicleAttribute<Int, NoUnit>(StatusEnum.VALID, Date(), 1, null)
    private val sameAsAttribute1 = VehicleAttribute<Int, NoUnit>(StatusEnum.VALID, Date(), 1, null)
    private val updatedAttribute1 = VehicleAttribute<Int, NoUnit>(StatusEnum.VALID, Date(), 2, null)
    private val updatedAttribute1v2 = VehicleAttribute<Int, NoUnit>(StatusEnum.INVALID, Date(), 2, null)
    private val attribute2 = VehicleAttribute<Int, NoUnit>(StatusEnum.VALID, Date(), 17, null)
    private val sameAsAttribute2 = VehicleAttribute<Int, NoUnit>(StatusEnum.VALID, Date(), 17, null)

    @Test
    fun `should compare simple items`(softly: SoftAssertions) {
        softly.assertThat(listOf(attribute1).isUpdatedBy(listOf(sameAsAttribute1))).isFalse
        softly.assertThat(listOf(attribute1).isUpdatedBy(listOf(updatedAttribute1))).isTrue
        softly.assertThat(listOf(attribute1).isUpdatedBy(listOf(updatedAttribute1v2))).isTrue
    }

    @Test
    fun `should differ different attributes`(softly: SoftAssertions) {
        softly.assertThat(listOf(attribute1).isUpdatedBy(listOf(sameAsAttribute1, attribute2))).isTrue
        softly.assertThat(listOf(attribute1, attribute2).isUpdatedBy(listOf(sameAsAttribute1, sameAsAttribute2))).isFalse
    }

    @Test
    fun `should differ deep`(softly: SoftAssertions) {
        val listAttribute1 = VehicleAttribute<List<String>, NoUnit>(StatusEnum.VALID, Date(), listOf("a", "b"), null)
        val sameAsListAttribute1 = VehicleAttribute<List<String>, NoUnit>(StatusEnum.VALID, Date(), listOf("a", "b"), null)
        val updatedListAttribute1 = VehicleAttribute<List<String>, NoUnit>(StatusEnum.VALID, Date(), listOf("a", "c", "b"), null)
        softly.assertThat(listOf(listAttribute1).isUpdatedBy(listOf(sameAsListAttribute1))).isFalse
        softly.assertThat(listOf(listAttribute1).isUpdatedBy(listOf(updatedListAttribute1))).isTrue
        softly.assertThat(listOf(listAttribute1, attribute1).isUpdatedBy(listOf(updatedListAttribute1, attribute1))).isTrue
        softly.assertThat(listOf(attribute1, listAttribute1).isUpdatedBy(listOf(sameAsAttribute1, sameAsListAttribute1)))
            .isFalse
    }
}
