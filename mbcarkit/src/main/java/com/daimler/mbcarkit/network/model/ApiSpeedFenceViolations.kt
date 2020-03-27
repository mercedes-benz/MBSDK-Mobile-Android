package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.speedfence.SpeedFenceViolation
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiSpeedFenceViolations(
    @SerializedName("violations") val violations: List<ApiSpeedFenceViolation>?
) : Mappable<List<SpeedFenceViolation>> {

    override fun map(): List<SpeedFenceViolation> =
        violations?.map { it.toSpeedFenceViolation() } ?: emptyList()
}
