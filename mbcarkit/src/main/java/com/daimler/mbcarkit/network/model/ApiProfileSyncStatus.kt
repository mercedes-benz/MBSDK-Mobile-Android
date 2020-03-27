package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.ProfileSyncStatus
import com.daimler.mbcarkit.network.model.ApiProfileSyncStatus.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiProfileSyncStatus {
    @SerializedName("UNKNOWN")
    UNKNOWN,
    @SerializedName("ON")
    ON,
    @SerializedName("OFF")
    OFF,
    @SerializedName("MANAGE_IN_HEAD_UNIT")
    MANAGE_IN_HEAD_UNIT,
    @SerializedName("UNSUPPORTED")
    UNSUPPORTED,
    @SerializedName("SERVICE_NOT_ACTIVE")
    SERVICE_NOT_ACTIVE;

    companion object {

        val map = ProfileSyncStatus.values().associateBy(ProfileSyncStatus::name)
    }
}

internal fun ApiProfileSyncStatus.toProfileSyncStatus() = map[name]
