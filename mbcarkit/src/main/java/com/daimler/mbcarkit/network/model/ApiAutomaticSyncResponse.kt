package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.ProfileSyncStatus
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiAutomaticSyncResponse(
    @SerializedName("profileSyncStatus") val profileSyncStatus: ApiProfileSyncStatus?
) : Mappable<ProfileSyncStatus> {
    override fun map(): ProfileSyncStatus = profileSyncStatus?.toProfileSyncStatus() ?: ProfileSyncStatus.UNKNOWN
}
