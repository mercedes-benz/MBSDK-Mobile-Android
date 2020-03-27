package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.ActiveTimes
import com.daimler.mbcarkit.business.model.geofencing.Fence
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiFence(
    @SerializedName("id") val id: Int?,
    @SerializedName("isActive") val isActive: Boolean?,
    @SerializedName("name") val name: String?,
    @SerializedName("activeTimes") val activeTimes: ApiActiveTimes,
    @SerializedName("violationType") val violationType: ApiGeofenceViolationType?,
    @SerializedName("shape") val shape: ApiShape
) : Mappable<Fence> {

    override fun map(): Fence = toFence()

    companion object {
        fun fromFence(fence: Fence) = ApiFence(
            null,
            null,
            fence.name,
            ApiActiveTimes.fromActiveTimes(fence.activeTimes),
            fence.violationType?.let { ApiGeofenceViolationType.fromGeofenceViolationType(it) },
            ApiShape.fromShape(fence.shape)
        )
    }
}

internal fun ApiFence.toFence() = Fence(
    id,
    isActive,
    name.orEmpty(),
    ActiveTimes(activeTimes.days, activeTimes.begin, activeTimes.end),
    violationType?.let { it.toGeofenceViolationType() },
    shape.toShape()
)

internal fun List<ApiFence>.toFences() = map { it.toFence() }
