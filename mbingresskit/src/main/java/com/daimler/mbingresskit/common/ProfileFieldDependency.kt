package com.daimler.mbingresskit.common

data class ProfileFieldDependency(
    val ownerType: ProfileFieldOwnerType,
    val itemType: ProfileFieldType,
    val fieldRelationshipType: ProfileFieldRelationshipType,
    val childrenTypes: List<ProfileFieldType>
)
