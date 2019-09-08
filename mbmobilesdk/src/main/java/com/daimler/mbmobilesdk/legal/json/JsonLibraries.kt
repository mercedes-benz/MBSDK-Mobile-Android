package com.daimler.mbmobilesdk.legal.json

import com.google.gson.annotations.SerializedName

internal data class JsonLibraries(
    @SerializedName("licenses") val libraries: List<JsonLibrary>?
)