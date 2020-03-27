package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiLicensePlate(
    @SerializedName("licenseplate") val licensePlate: String
)
