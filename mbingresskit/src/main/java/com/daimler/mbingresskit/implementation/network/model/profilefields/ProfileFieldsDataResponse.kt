package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ProfileFieldsDataResponse(
    @SerializedName("customerDataFields") val customerDataFields: List<CustomerDataFieldResponse>?,
    @SerializedName("fieldDependencies") val fieldDependencies: List<FieldDependencyResponse>?,
    @SerializedName("groupDependencies") val groupDependencies: List<GroupDependencyResponse>?
) : Mappable<ProfileFieldsData> {

    override fun map(): ProfileFieldsData = toProfileFieldsData()
}

internal fun ProfileFieldsDataResponse.toProfileFieldsData() = ProfileFieldsData(
    customerDataFields = customerDataFields?.map { it.toCustomerDataField() } ?: emptyList(),
    fieldDependencies = fieldDependencies?.map { it.toProfileFieldDependency() } ?: emptyList(),
    groupDependencies = groupDependencies?.map { it.toProfileGroupDependency() } ?: emptyList()
)
