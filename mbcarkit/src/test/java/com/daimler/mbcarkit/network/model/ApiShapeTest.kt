package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.Circle
import com.daimler.mbcarkit.business.model.geofencing.Polygon
import com.daimler.mbcarkit.business.model.geofencing.Shape
import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiShapeTest {

    @Test
    fun `map ApiShape to Shape`(softly: SoftAssertions) {
        val apiGeoCoordinates = ApiGeoCoordinates(123.0, 456.0)
        val apiShape = ApiShape(
            ApiCircle(apiGeoCoordinates, 7.0),
            ApiPolygon(listOf(apiGeoCoordinates))
        )
        val shape = apiShape.toShape()

        softly.assertThat(shape.circle?.center?.latitude).isEqualTo(apiShape.circle?.center?.latitude)
        softly.assertThat(shape.circle?.center?.longitude).isEqualTo(apiShape.circle?.center?.longitude)
        softly.assertThat(shape.circle?.radius).isEqualTo(apiShape.circle?.radius)
        softly.assertThat(shape.polygon?.coordinates?.get(0)?.latitude).isEqualTo(apiShape.circle?.center?.latitude)
        softly.assertThat(shape.polygon?.coordinates?.get(0)?.longitude).isEqualTo(apiShape.circle?.center?.longitude)
    }

    @Test
    fun `map Shape to ApiShape`(softly: SoftAssertions) {
        val geoCoordinates = GeoCoordinates(123.0, 456.0)
        val shape = Shape(
            Circle(geoCoordinates, 7.0),
            Polygon(listOf(geoCoordinates))
        )
        val apiShape = ApiShape.fromShape(shape)

        softly.assertThat(apiShape.circle?.center?.latitude).isEqualTo(shape.circle?.center?.latitude)
        softly.assertThat(apiShape.circle?.center?.longitude).isEqualTo(shape.circle?.center?.longitude)
        softly.assertThat(apiShape.circle?.radius).isEqualTo(shape.circle?.radius)
        softly.assertThat(apiShape.polygon?.coordinates?.get(0)?.latitude).isEqualTo(shape.circle?.center?.latitude)
        softly.assertThat(apiShape.polygon?.coordinates?.get(0)?.longitude).isEqualTo(shape.circle?.center?.longitude)
    }
}
