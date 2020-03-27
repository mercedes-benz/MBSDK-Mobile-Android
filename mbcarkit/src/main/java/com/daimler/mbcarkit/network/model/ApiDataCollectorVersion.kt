package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.DataCollectorVersion
import com.daimler.mbcarkit.network.model.ApiDataCollectorVersion.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiDataCollectorVersion {
    @SerializedName("DC1") DC1,
    @SerializedName("DC2") DC2,
    @SerializedName("DC2_PLUS") DC2_PLUS;

    companion object {
        val map: Map<String, DataCollectorVersion> = DataCollectorVersion.values().associateBy(DataCollectorVersion::name)
    }
}

internal fun ApiDataCollectorVersion?.toDataCollectorVersion(): DataCollectorVersion? =
    this?.let { map[name] }
