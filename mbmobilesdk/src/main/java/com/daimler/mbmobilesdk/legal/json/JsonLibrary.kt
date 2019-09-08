package com.daimler.mbmobilesdk.legal.json

import com.google.gson.annotations.SerializedName

internal data class JsonLibrary(
    @SerializedName("name") val name: String?,
    @SerializedName("version") val version: String?,
    @SerializedName("dependency") val dependency: String?,
    @SerializedName("fileUrl") val fileUrl: String?,
    @SerializedName("file") val file: String?,
    @SerializedName("licenses") val licenses: List<JsonLicense>?
)