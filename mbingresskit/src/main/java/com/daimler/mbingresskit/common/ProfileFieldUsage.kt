package com.daimler.mbingresskit.common

enum class ProfileFieldUsage {
    OPTIONAL,
    MANDATORY,
    INVISIBLE,
    READ_ONLY,
    UNKNOWN;

    companion object {
        private val map: Map<String, ProfileFieldUsage> = values().associateBy(ProfileFieldUsage::name)

        fun forName(name: String) = map[name] ?: UNKNOWN
    }
}
