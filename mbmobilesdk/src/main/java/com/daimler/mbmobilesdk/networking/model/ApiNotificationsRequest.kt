package com.daimler.mbmobilesdk.networking.model

import com.google.gson.annotations.SerializedName

internal data class ApiNotificationsRequest(
    @SerializedName("distributionProfile") val distributionProfile: ApiDistributionProfile,
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("deviceToken") val deviceToken: String
)