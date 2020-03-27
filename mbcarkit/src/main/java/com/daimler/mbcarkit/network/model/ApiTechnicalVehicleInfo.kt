package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiTechnicalVehicleInfo(
    @SerializedName("consumerCountry") val consumerCountry: String?,
    @SerializedName("salesDesignation") val salesDesignation: String?,
    @SerializedName("baumuster") val baumuster: String?
)
