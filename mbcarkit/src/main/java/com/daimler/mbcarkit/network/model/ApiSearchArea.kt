package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiSearchArea(
    @SerializedName("center") val center: ApiCenter?,
    @SerializedName("radius") val radius: ApiRadius?
)
