package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiAcceptAvpDriveRequest(
    @SerializedName("bookingId") val bookingId: String,
    @SerializedName("startDrive") val startDrive: Boolean
)
