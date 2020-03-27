package com.daimler.mbingresskit.implementation.network.model.adaptionvalues

import com.daimler.mbingresskit.common.UserAdaptionValues
import com.daimler.mbingresskit.common.UserBodyHeight
import com.google.gson.annotations.SerializedName

internal data class ApiUserAdaptionValues(
    @SerializedName("bodyHeight") val bodyHeight: Int?,
    @SerializedName("preAdjustment") val preAdjustment: Boolean,
    @SerializedName("alias") val alias: String?
)

internal fun ApiUserAdaptionValues.toUserBodyHeight() = UserBodyHeight(
    bodyHeight = bodyHeight,
    preAdjustment = preAdjustment
)

internal fun ApiUserAdaptionValues.toUserAdaptionValues() = UserAdaptionValues(
    UserBodyHeight(bodyHeight, preAdjustment),
    alias
)

internal fun UserAdaptionValues.toApiUserAdaptionValues() = ApiUserAdaptionValues(
    bodyHeight = bodyHeight?.bodyHeight,
    preAdjustment = bodyHeight?.preAdjustment ?: false,
    alias = alias
)
