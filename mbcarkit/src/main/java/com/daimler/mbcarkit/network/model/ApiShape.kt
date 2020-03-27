package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.Circle
import com.daimler.mbcarkit.business.model.geofencing.Polygon
import com.daimler.mbcarkit.business.model.geofencing.Shape
import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.google.gson.annotations.SerializedName

internal data class ApiShape(
    @SerializedName("circle") val circle: ApiCircle?,
    @SerializedName("polygon") val polygon: ApiPolygon?
) {
    companion object {
        fun fromShape(shape: Shape) = ApiShape(
            shape.circle?.let { circle ->
                ApiCircle(
                    ApiGeoCoordinates(circle.center.latitude, circle.center.longitude),
                    circle.radius
                )
            },
            shape.polygon?.let { polygon ->
                ApiPolygon(
                    polygon.coordinates.map {
                        ApiGeoCoordinates(it.latitude, it.longitude)
                    }
                )
            }
        )
    }
}

internal fun ApiShape.toShape() = Shape(
    circle?.let { circle -> Circle(GeoCoordinates(circle.center.latitude, circle.center.longitude), circle.radius) },
    polygon?.let { polygon -> Polygon(polygon.coordinates.map { GeoCoordinates(it.latitude, it.longitude) }) }
)
