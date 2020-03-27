package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiVehicleUserGeneralData(
    @SerializedName("maxNumberOfProfiles") val maxNumberOfProfiles: Int?,
    @SerializedName("numberOfOccupiedProfiles") val numberOfOccupiedProfiles: Int?,
    @SerializedName("profileSyncStatus") val profileSyncStatus: String?
)
