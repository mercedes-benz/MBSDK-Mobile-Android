package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiCenterTest {

    @Test
    fun `map ApiCenter from GeoCoordinates`(softly: SoftAssertions) {
        val geoCoordinates = GeoCoordinates(0.0, 1.0)
        val apiCenter = ApiCenter.fromGeoCoordinates(geoCoordinates)

        softly.assertThat(apiCenter.latitude).isEqualTo(geoCoordinates.latitude.toString())
        softly.assertThat(apiCenter.longitude).isEqualTo(geoCoordinates.longitude.toString())
    }
}
