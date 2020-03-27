package com.daimler.mbingresskit.common

enum class AccountIdentifier {
    EMAIL,
    MOBILE,
    EMAIL_AND_MOBILE,
    UNKNOWN;

    companion object {
        private val map: Map<String, AccountIdentifier> = values().associateBy(AccountIdentifier::name)

        fun forName(name: String) = map[name] ?: UNKNOWN
    }
}
