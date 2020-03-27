package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiCommunication(
    @SerializedName("fax") val fax: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("website") val website: String?,
    @SerializedName("facebook") val facebook: String?,
    @SerializedName("mobile") val mobile: String?,
    @SerializedName("googlePlus") val googlePlus: String?,
    @SerializedName("twitter") val twitter: String?,
    @SerializedName("phone") val phone: String?
)
