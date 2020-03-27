package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiAutoSyncConfiguration(
    @SerializedName("desiredState") val desiredState: Boolean
)
