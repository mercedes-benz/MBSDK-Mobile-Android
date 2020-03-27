package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.onboardfence.SyncStatus
import com.google.gson.annotations.SerializedName

internal enum class ApiSyncStatus {
    @SerializedName("PENDING") PENDING,
    @SerializedName("TIMEOUT") TIMEOUT,
    @SerializedName("HOLD") HOLD,
    @SerializedName("FAILED") FAILED,
    @SerializedName("FINISHED") FINISHED,
    @SerializedName("AWAITING_VEP") AWAITING_VEP;

    companion object {
        private val map: Map<String, ApiSyncStatus> = values().associateBy(ApiSyncStatus::name)

        fun fromSyncStatus(syncStatus: SyncStatus) = map[syncStatus.name]
    }
}

internal fun ApiSyncStatus?.toSyncStatus(): SyncStatus? =
    this?.let { SyncStatus.map[name] }
