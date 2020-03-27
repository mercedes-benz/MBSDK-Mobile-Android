package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectViolationType
import com.daimler.mbcarkit.network.model.ApiValetprotectViolationType.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiValetprotectViolationType {
    @SerializedName("IGNITION_ON") IGNITION_ON,
    @SerializedName("IGNITION_OFF") IGNITION_OFF,
    @SerializedName("FENCE") FENCE;

    companion object {
        val map: Map<String, ValetprotectViolationType> = ValetprotectViolationType.values().associateBy(ValetprotectViolationType::name)
        private val reverseMap: Map<String, ApiValetprotectViolationType> = values().associateBy(ApiValetprotectViolationType::name)

        fun fromValetprotectViolationType(valetprotectViolationType: ValetprotectViolationType) =
            reverseMap[valetprotectViolationType.name] ?: FENCE
    }
}

internal fun ApiValetprotectViolationType?.toValetprotectViolationType(): ValetprotectViolationType =
    this?.let { map[name] } ?: ValetprotectViolationType.UNKNOWN
