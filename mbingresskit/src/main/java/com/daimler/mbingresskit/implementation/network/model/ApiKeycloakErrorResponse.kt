package com.daimler.mbingresskit.implementation.network.model

import com.daimler.mbnetworkkit.networking.RequestError
import com.google.gson.annotations.SerializedName

internal data class ApiKeycloakErrorResponse(
    @SerializedName("error") val error: ApiKeycloakError?,
    @SerializedName("error_description") val description: String?
) : RequestError
