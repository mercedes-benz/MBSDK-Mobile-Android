package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiQrInvitationRequest(
    @SerializedName("finOrVin") val finOrVin: String?,
    @SerializedName("correlationId") val correlationId: String?
)
