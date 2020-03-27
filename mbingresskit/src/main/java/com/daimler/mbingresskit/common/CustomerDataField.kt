package com.daimler.mbingresskit.common

data class CustomerDataField(
    val fieldType: ProfileFieldType,
    val sequenceOrder: Int,
    val fieldUsage: ProfileFieldUsage,
    val fieldValidation: ProfileFieldValidation?,
    val selectableValues: ProfileSelectableValues?
)
