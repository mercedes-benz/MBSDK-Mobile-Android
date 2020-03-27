package com.daimler.mbingresskit.common

enum class ProfileFieldOwnerType {
    ACCOUNT,
    VEHICLE,
    UNKNOWN;

    companion object {
        private val map: Map<String, ProfileFieldOwnerType> = values().associateBy(ProfileFieldOwnerType::name)

        fun forName(name: String) = map[name] ?: UNKNOWN
    }
}
