package com.daimler.mbingresskit.common

enum class ProfileFieldRelationshipType {
    GROUP,
    DATA_FIELD,
    UNKNOWN;

    companion object {
        private val map: Map<String, ProfileFieldRelationshipType> = values().associateBy(ProfileFieldRelationshipType::name)

        fun forName(name: String) = map[name] ?: UNKNOWN
    }
}
