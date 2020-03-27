package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.speedfence.SpeedFence
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiSpeedFences(
    @SerializedName("speedfences") val speedfences: List<ApiSpeedFence>?
) : Mappable<List<SpeedFence>> {

    override fun map(): List<SpeedFence> =
        speedfences?.map { it.toSpeedFence() } ?: emptyList()
}
