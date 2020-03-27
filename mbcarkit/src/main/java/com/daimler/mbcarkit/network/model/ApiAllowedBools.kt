package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.AllowedBools
import com.daimler.mbcarkit.network.model.ApiAllowedBools.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiAllowedBools {

    @SerializedName("ONLY_TRUE") ONLY_TRUE,
    @SerializedName("ONLY_FALSE") ONLY_FALSE,
    @SerializedName("TRUE_AND_FALSE") TRUE_AND_FALSE;

    companion object {
        val map: Map<String, AllowedBools> = AllowedBools.values().associateBy(AllowedBools::name)
    }
}

internal fun ApiAllowedBools?.toAllowedBools(): AllowedBools =
    this?.let { map[name] } ?: AllowedBools.UNKNOWN
