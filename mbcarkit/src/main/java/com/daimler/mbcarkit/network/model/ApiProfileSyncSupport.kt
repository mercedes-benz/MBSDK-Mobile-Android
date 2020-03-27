package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.ProfileSyncSupport
import com.daimler.mbcarkit.network.model.ApiProfileSyncSupport.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiProfileSyncSupport {
    @SerializedName("SUPPORTED") SUPPORTED,
    @SerializedName("MANAGE_IN_HEAD_UNIT") MANAGE_IN_HEAD_UNIT,
    @SerializedName("SERVICE_NOT_ACTIVE") SERVICE_NOT_ACTIVE,
    @SerializedName("UNSUPPORTED") UNSUPPORTED,
    @SerializedName("UNKNOWN") UNKNOWN;

    companion object {
        val map: Map<String, ProfileSyncSupport> = ProfileSyncSupport.values().associateBy(ProfileSyncSupport::name)
    }
}

internal fun ApiProfileSyncSupport?.toProfileSyncSupport(): ProfileSyncSupport? =
    this?.let { map[name] }
