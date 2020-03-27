package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.GeofenceViolation
import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.google.gson.annotations.SerializedName

internal data class ApiGeofenceViolation(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: ApiGeofenceViolationType?,
    @SerializedName("fenceId") val fenceId: Int,
    @SerializedName("time") val time: Int,
    @SerializedName("coordinate") val coordinate: ApiGeoCoordinates,
    @SerializedName("snapshot") val snapshot: ApiFence
)

internal fun ApiGeofenceViolation.toGeofenceViolation() = GeofenceViolation(
    id,
    type.toGeofenceViolationType(),
    fenceId,
    time,
    GeoCoordinates(coordinate.latitude, coordinate.longitude),
    snapshot.toFence()
)

internal fun List<ApiGeofenceViolation>.toGeofenceViolations() =
    map { it.toGeofenceViolation() }
