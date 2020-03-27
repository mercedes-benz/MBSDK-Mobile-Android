package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.Radius
import com.google.gson.annotations.SerializedName

internal data class ApiRadius(
    @SerializedName("value") val value: String?,
    @SerializedName("unit") val unit: String?
) {
    companion object {
        fun fromRadius(radius: Radius) =
            ApiRadius(
                radius.value,
                radius.unit
            )
    }
}
