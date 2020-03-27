package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiBaumuster(
    @SerializedName("baumuster") val value: String?,
    @SerializedName("baumusterDescription") val description: String?
)
