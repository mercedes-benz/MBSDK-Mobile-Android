package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal enum class ApiServiceDesire {
    @SerializedName("ACTIVE") ACTIVE,
    @SerializedName("INACTIVE") INACTIVE
}
