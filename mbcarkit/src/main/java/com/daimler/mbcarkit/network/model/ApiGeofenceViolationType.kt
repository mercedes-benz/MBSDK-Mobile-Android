package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.GeofenceViolationType
import com.daimler.mbcarkit.network.model.ApiGeofenceViolationType.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiGeofenceViolationType {
    @SerializedName("LEAVE") LEAVE,
    @SerializedName("ENTER") ENTER,
    @SerializedName("LEAVE_AND_ENTER") LEAVE_AND_ENTER;

    companion object {
        val map: Map<String, GeofenceViolationType> = GeofenceViolationType.values().associateBy(GeofenceViolationType::name)
        private val reverseMap: Map<String, ApiGeofenceViolationType> = values().associateBy(ApiGeofenceViolationType::name)

        fun fromGeofenceViolationType(geofenceViolationType: GeofenceViolationType) =
            reverseMap[geofenceViolationType.name] ?: LEAVE_AND_ENTER
    }
}

internal fun ApiGeofenceViolationType?.toGeofenceViolationType(): GeofenceViolationType =
    this?.let { map[name] } ?: GeofenceViolationType.LEAVE_AND_ENTER
