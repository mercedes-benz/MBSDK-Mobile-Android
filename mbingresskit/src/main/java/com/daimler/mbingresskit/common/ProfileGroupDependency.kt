package com.daimler.mbingresskit.common

data class ProfileGroupDependency(
    val itemType: ProfileFieldType,
    val fieldRelationshipType: ProfileFieldRelationshipType,
    val childrenTypes: List<ProfileFieldType>
)
