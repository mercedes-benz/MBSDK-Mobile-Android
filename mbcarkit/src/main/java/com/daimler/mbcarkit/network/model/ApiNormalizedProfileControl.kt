package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.NormalizedProfileControl
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiNormalizedProfileControl(
    @SerializedName("enabled") val enabled: Boolean
) : Mappable<NormalizedProfileControl> {
    override fun map(): NormalizedProfileControl = toNormalizedProfileControl()
}

internal fun ApiNormalizedProfileControl.toNormalizedProfileControl() =
    NormalizedProfileControl(enabled)
