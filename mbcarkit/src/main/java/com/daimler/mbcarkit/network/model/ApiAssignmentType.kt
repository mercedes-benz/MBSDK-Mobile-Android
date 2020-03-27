package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.assignment.AssignmentType
import com.daimler.mbcarkit.network.model.ApiAssignmentType.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiAssignmentType {
    @SerializedName("OWNER") OWNER,
    @SerializedName("USER") USER;

    companion object {
        val map: Map<String, AssignmentType> = AssignmentType.values().associateBy(AssignmentType::name)
    }
}

internal fun ApiAssignmentType?.toAssignmentType(): AssignmentType =
    this?.let { map[name] } ?: AssignmentType.UNKNOWN
