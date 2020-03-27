package com.daimler.mbnetworkkit.networking.model

import com.google.gson.annotations.SerializedName

internal data class ApiDefaultErrorModel(
    @SerializedName("description") val description: String?
)
