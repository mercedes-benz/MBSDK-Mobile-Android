package com.daimler.mbmobilesdk.networking.model

import com.google.gson.annotations.SerializedName

internal enum class ApiDistributionProfile {
    @SerializedName("development")
    DEVELOPMENT,
    @SerializedName("ad_hoc")
    AD_HOC,
    @SerializedName("store")
    STORE
}