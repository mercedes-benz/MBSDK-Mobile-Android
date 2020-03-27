package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.daimler.mbingresskit.common.CustomerDataField
import com.daimler.mbingresskit.common.ProfileFieldType
import com.daimler.mbingresskit.common.ProfileFieldUsage
import com.google.gson.annotations.SerializedName

internal data class CustomerDataFieldResponse(
    @SerializedName("fieldId") val fieldType: ProfileFieldTypeResponse?,
    @SerializedName("sequenceOrder") val sequenceOrder: Int,
    @SerializedName("fieldUsage") val fieldUsageResponse: ProfileFieldUsageResponse?,
    @SerializedName("fieldValidation") val fieldValidation: ProfileFieldValidationResponse?,
    @SerializedName("selectableValues") val selectableValues: ProfileSelectableValuesResponse?
)

internal fun CustomerDataFieldResponse.toCustomerDataField() = CustomerDataField(
    fieldType = fieldType?.profileFieldType ?: ProfileFieldType.UNKNOWN,
    sequenceOrder = sequenceOrder,
    fieldUsage = fieldUsageResponse?.let { response ->
        ProfileFieldUsage.forName(response.name)
    } ?: ProfileFieldUsage.UNKNOWN,
    fieldValidation = fieldValidation?.toProfileFieldValidation(),
    selectableValues = selectableValues?.toProfileSelectableValues()
)
