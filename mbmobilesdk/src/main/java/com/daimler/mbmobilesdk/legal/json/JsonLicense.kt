package com.daimler.mbmobilesdk.legal.json

import com.google.gson.annotations.SerializedName

internal data class JsonLicense(
    @SerializedName("license") val name: String?,
    @SerializedName("url") val url: String?
)