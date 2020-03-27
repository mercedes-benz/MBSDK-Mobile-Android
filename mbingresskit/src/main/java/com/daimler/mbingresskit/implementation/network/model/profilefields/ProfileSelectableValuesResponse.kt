package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.daimler.mbingresskit.common.ProfileSelectableValues
import com.google.gson.annotations.SerializedName

internal data class ProfileSelectableValuesResponse(
    @SerializedName("matchSelectableValueByKey") val matchSelectableValueByKey: Boolean,
    @SerializedName("defaultSelectableValueKey") val defaultSelectableValueKey: String?,
    @SerializedName("selectableValues") val selectableValues: List<ProfileSelectableValueResponse>?
)

internal fun ProfileSelectableValuesResponse.toProfileSelectableValues() = ProfileSelectableValues(
    matchSelectableValueByKey = matchSelectableValueByKey,
    defaultSelectableValueKey = defaultSelectableValueKey,
    selectableValues = selectableValues?.map { value -> value.toProfileSelectableValue() } ?: emptyList()
)
