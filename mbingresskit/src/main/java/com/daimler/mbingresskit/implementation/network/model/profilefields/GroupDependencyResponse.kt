package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.daimler.mbingresskit.common.ProfileFieldRelationshipType
import com.daimler.mbingresskit.common.ProfileFieldType
import com.daimler.mbingresskit.common.ProfileGroupDependency
import com.google.gson.annotations.SerializedName

internal data class GroupDependencyResponse(
    @SerializedName("itemId") val itemId: ProfileFieldTypeResponse?,
    @SerializedName("profileDataFieldRelationshipType") val fieldType: ProfileDataFieldRelationshipTypeResponse?,
    @SerializedName("childrenIds") val childrenIds: List<ProfileFieldTypeResponse?>?
)

internal fun GroupDependencyResponse.toProfileGroupDependency() = ProfileGroupDependency(
    itemType = itemId?.profileFieldType ?: ProfileFieldType.UNKNOWN,
    fieldRelationshipType = fieldType?.let {
        ProfileFieldRelationshipType.forName(it.name)
    } ?: ProfileFieldRelationshipType.UNKNOWN,
    childrenTypes = childrenIds?.map { it?.profileFieldType ?: ProfileFieldType.UNKNOWN } ?: emptyList()
)
