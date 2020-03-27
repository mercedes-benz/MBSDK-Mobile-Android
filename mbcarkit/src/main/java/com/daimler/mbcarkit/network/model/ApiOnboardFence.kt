package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.onboardfence.OnboardFence
import com.google.gson.annotations.SerializedName

internal data class ApiOnboardFence(
    @SerializedName("geofenceid") val geoFenceId: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("isActive") val isActive: Boolean?,
    @SerializedName("center") val center: ApiGeoCoordinates,
    @SerializedName("fencetype") val fenceType: ApiFenceType?,
    @SerializedName("radiusInMeter") val radiusInMeters: Int?,
    @SerializedName("verticescount") val verticesCount: Int?,
    @SerializedName("verticespositions") val verticesPositions: List<ApiGeoCoordinates>?,
    @SerializedName("syncstatus") val syncStatus: ApiSyncStatus?
) {
    companion object {
        fun fromOnboardFence(onboardFence: OnboardFence) =
            ApiOnboardFence(
                onboardFence.geoFenceId,
                onboardFence.name,
                onboardFence.isActive,
                ApiGeoCoordinates(onboardFence.center.latitude, onboardFence.center.longitude),
                onboardFence.fenceType?.let { ApiFenceType.fromFenceType(it) },
                onboardFence.radiusInMeters,
                onboardFence.verticesCount,
                onboardFence.verticesPositions?.map { ApiGeoCoordinates(it.latitude, it.longitude) },
                onboardFence.syncStatus?.let { ApiSyncStatus.fromSyncStatus(it) }
            )
    }
}

internal fun ApiOnboardFence.toOnboardFence(): OnboardFence =
    OnboardFence(
        geoFenceId,
        name,
        isActive,
        GeoCoordinates(center.latitude, center.longitude),
        fenceType.toFenceType(),
        radiusInMeters,
        verticesCount,
        verticesPositions?.map { GeoCoordinates(it.latitude, it.longitude) },
        syncStatus?.toSyncStatus()
    )
