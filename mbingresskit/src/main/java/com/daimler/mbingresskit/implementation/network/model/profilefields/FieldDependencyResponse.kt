package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.daimler.mbingresskit.common.ProfileFieldDependency
import com.daimler.mbingresskit.common.ProfileFieldOwnerType
import com.daimler.mbingresskit.common.ProfileFieldRelationshipType
import com.daimler.mbingresskit.common.ProfileFieldType
import com.google.gson.annotations.SerializedName

internal data class FieldDependencyResponse(
    @SerializedName("fieldOwnerType") val fieldOwnerType: FieldOwnerTypeResponse?,
    @SerializedName("itemId") val itemId: ProfileFieldTypeResponse?,
    @SerializedName("profileDataFieldRelationshipType") val fieldType: ProfileDataFieldRelationshipTypeResponse?,
    @SerializedName("childrenIds") val childrenIds: List<ProfileFieldTypeResponse?>?
)

internal fun FieldDependencyResponse.toProfileFieldDependency() = ProfileFieldDependency(
    ownerType = fieldOwnerType?.let {
        ProfileFieldOwnerType.forName(it.name)
    } ?: ProfileFieldOwnerType.UNKNOWN,
    itemType = itemId?.profileFieldType ?: ProfileFieldType.UNKNOWN,
    fieldRelationshipType = fieldType?.let {
        ProfileFieldRelationshipType.forName(it.name)
    } ?: ProfileFieldRelationshipType.UNKNOWN,
    childrenTypes = childrenIds?.map { it?.profileFieldType ?: ProfileFieldType.UNKNOWN } ?: emptyList()
)
