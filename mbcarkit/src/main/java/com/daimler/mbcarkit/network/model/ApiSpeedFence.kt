package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.speedfence.SpeedFence
import com.google.gson.annotations.SerializedName

internal data class ApiSpeedFence(
    @SerializedName("geofenceid") val geoFenceId: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("isActive") val isActive: Boolean?,
    @SerializedName("endtime") val endTime: Long?,
    @SerializedName("threshold") val threshold: Int?,
    @SerializedName("violationdelay") val violationDelay: Int?,
    @SerializedName("violationtypes") val violationTypes: List<ApiSpeedfenceViolationType?>?,
    @SerializedName("ts") val timestamp: Long?,
    @SerializedName("speedfenceid") val speedFenceId: Int?,
    @SerializedName("syncstatus") val syncStatus: ApiSyncStatus?
) {
    companion object {
        fun fromSpeedFence(speedFence: SpeedFence) = ApiSpeedFence(
            speedFence.geoFenceId,
            speedFence.name,
            speedFence.isActive,
            speedFence.endTime,
            speedFence.threshold,
            speedFence.violationDelay,
            speedFence.violationTypes.mapNotNull {
                ApiSpeedfenceViolationType.fromSpeedFenceViolationType(it)
            },
            speedFence.timestamp,
            speedFence.speedFenceId,
            speedFence.syncStatus?.let { ApiSyncStatus.fromSyncStatus(it) }
        )
    }
}

internal fun ApiSpeedFence.toSpeedFence() = SpeedFence(
    geoFenceId,
    name,
    isActive,
    endTime,
    threshold,
    violationDelay,
    violationTypes?.mapNotNull {
        it.toSpeedFenceViolationType()
    } ?: emptyList(),
    timestamp,
    speedFenceId,
    syncStatus.toSyncStatus(),
)
