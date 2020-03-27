package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.daimler.mbingresskit.common.ProfileSelectableValue
import com.google.gson.annotations.SerializedName

internal data class ProfileSelectableValueResponse(
    @SerializedName("key") val key: String,
    @SerializedName("description") val description: String
)

internal fun ProfileSelectableValueResponse.toProfileSelectableValue() = ProfileSelectableValue(
    key = key,
    description = description
)
