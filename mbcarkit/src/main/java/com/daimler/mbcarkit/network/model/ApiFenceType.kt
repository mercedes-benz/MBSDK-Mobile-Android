package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.onboardfence.FenceType
import com.google.gson.annotations.SerializedName

internal enum class ApiFenceType {
    @SerializedName("CIRCLE") CIRCLE,
    @SerializedName("POLYGON") POLYGON;

    companion object {
        private val map: Map<String, ApiFenceType> = values().associateBy(ApiFenceType::name)

        fun fromFenceType(fenceType: FenceType) = map[fenceType.name]
    }
}

internal fun ApiFenceType?.toFenceType(): FenceType? =
    this?.let { FenceType.map[name] }
