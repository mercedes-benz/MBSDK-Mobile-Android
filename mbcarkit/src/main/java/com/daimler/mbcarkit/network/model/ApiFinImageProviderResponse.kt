package com.daimler.mbcarkit.network.model

import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiFinImageProviderResponse(
    @SerializedName("finImagesCdnBaseUrl") val url: String
) : Mappable<String> {

    override fun map(): String = url
}
