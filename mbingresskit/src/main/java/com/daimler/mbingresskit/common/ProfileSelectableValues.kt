package com.daimler.mbingresskit.common

data class ProfileSelectableValues(
    val matchSelectableValueByKey: Boolean,
    val defaultSelectableValueKey: String?,
    val selectableValues: List<ProfileSelectableValue>
)
