package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.valetprotect.DistanceUnit
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectItem
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectRadius
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectViolationType
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiValetprotectItemTest {

    @Test
    fun `map ApiValetprotectItem to ValetprotectItem`(softly: SoftAssertions) {
        val apiValetProtectItem = ApiValetprotectItem(
            "",
            listOf(ApiValetprotectViolationType.FENCE),
            ApiGeoCoordinates(
                0.0,
                1.0
            ),
            ApiValetprotectRadius(
                0.0,
                ApiDistanceUnit.KM
            )
        )
        val valetProtectItem = apiValetProtectItem.toValetprotectItem()

        softly.assertThat(valetProtectItem.name).isEqualTo(apiValetProtectItem.name)
        softly.assertThat(valetProtectItem.violationtypes[0].name).isEqualTo(apiValetProtectItem.violationtypes[0].name)
        softly.assertThat(valetProtectItem.center.latitude).isEqualTo(apiValetProtectItem.center?.latitude)
        softly.assertThat(valetProtectItem.center.longitude).isEqualTo(apiValetProtectItem.center?.longitude)
        softly.assertThat(valetProtectItem.radius.value).isEqualTo(apiValetProtectItem.radius?.value)
        softly.assertThat(valetProtectItem.radius.unit.name).isEqualTo(apiValetProtectItem.radius?.unit?.name)
    }

    @Test
    fun `map ApiValetprotectItem from ValetprotectItem`(softly: SoftAssertions) {
        val valetProtectItem = ValetprotectItem(
            "",
            listOf(ValetprotectViolationType.FENCE),
            GeoCoordinates(
                0.0,
                1.0
            ),
            ValetprotectRadius(
                0.0,
                DistanceUnit.KM
            )
        )
        val apiValetProtectItem = ApiValetprotectItem.fromValetprotectItem(valetProtectItem)

        softly.assertThat(apiValetProtectItem.name).isEqualTo(valetProtectItem.name)
        softly.assertThat(apiValetProtectItem.violationtypes[0].name).isEqualTo(valetProtectItem.violationtypes[0].name)
        softly.assertThat(apiValetProtectItem.center?.latitude).isEqualTo(valetProtectItem.center.latitude)
        softly.assertThat(apiValetProtectItem.center?.longitude).isEqualTo(valetProtectItem.center.longitude)
        softly.assertThat(apiValetProtectItem.radius?.value).isEqualTo(valetProtectItem.radius.value)
        softly.assertThat(apiValetProtectItem.radius?.unit?.name).isEqualTo(valetProtectItem.radius.unit.name)
    }
}
