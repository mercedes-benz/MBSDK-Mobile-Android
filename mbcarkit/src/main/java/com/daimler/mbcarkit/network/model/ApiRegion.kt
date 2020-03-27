package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiRegion(
    @SerializedName("region") val region: String?,
    @SerializedName("subRegion") val subRegion: String?
)
