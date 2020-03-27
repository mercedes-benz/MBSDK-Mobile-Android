package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.Period
import com.google.gson.annotations.SerializedName

internal data class ApiPeriod(
    @SerializedName("from") val from: String?,
    @SerializedName("until") val until: String?
)

internal fun ApiPeriod.toPeriod() = Period(from, until)
