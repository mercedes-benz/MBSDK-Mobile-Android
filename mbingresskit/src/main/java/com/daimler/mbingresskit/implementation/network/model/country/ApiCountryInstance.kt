package com.daimler.mbingresskit.implementation.network.model.country

import com.google.gson.annotations.SerializedName

internal enum class ApiCountryInstance {
    @SerializedName("ECE") ECE,
    @SerializedName("AMAP") AMAP,
    @SerializedName("CN") CN,
    @SerializedName("AP") AP
}
