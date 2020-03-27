package com.daimler.mbingresskit.common

enum class UserPinStatus {
    SET,
    NOT_SET,
    UNKNOWN;

    companion object {
        private val map: Map<String, UserPinStatus> = values().associateBy(UserPinStatus::name)

        fun forName(name: String) = map[name] ?: UNKNOWN
    }
}
