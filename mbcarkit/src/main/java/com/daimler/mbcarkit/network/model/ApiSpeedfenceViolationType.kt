package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.speedfence.SpeedFenceViolationType
import com.daimler.mbcarkit.network.model.ApiSpeedfenceViolationType.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiSpeedfenceViolationType {
    @SerializedName("ENTER") ENTER,
    @SerializedName("LEAVE") LEAVE,
    @SerializedName("LEAVE_AND_ENTER") LEAVE_AND_ENTER;

    companion object {
        val map: Map<String, SpeedFenceViolationType> = SpeedFenceViolationType.values().associateBy(SpeedFenceViolationType::name)
        private val reverseMap: Map<String, ApiSpeedfenceViolationType> = values().associateBy(ApiSpeedfenceViolationType::name)

        fun fromSpeedFenceViolationType(speedFenceViolationType: SpeedFenceViolationType?) = speedFenceViolationType?.let { reverseMap[it.name] }
    }
}

internal fun ApiSpeedfenceViolationType?.toSpeedFenceViolationType(): SpeedFenceViolationType? =
    this?.let { map[name] }
