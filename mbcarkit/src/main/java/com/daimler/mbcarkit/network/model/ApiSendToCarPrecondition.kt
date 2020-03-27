package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPrecondition
import com.daimler.mbcarkit.network.model.ApiSendToCarPrecondition.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiSendToCarPrecondition {
    @SerializedName("MBAPPS_REGISTER_USER") MBAPPS_REGISTER_USER,
    @SerializedName("MBAPPS_ENABLE_MBAPPS") MBAPPS_ENABLE_MBAPPS;

    companion object {
        val map: Map<String, SendToCarPrecondition> = SendToCarPrecondition.values().associateBy(SendToCarPrecondition::name)
    }
}

internal fun ApiSendToCarPrecondition?.toSendToCarPrecondition(): SendToCarPrecondition =
    this?.let { map[name] } ?: SendToCarPrecondition.MBAPPS_ENABLE_MBAPPS
