package com.daimler.mbingresskit.common

enum class CountryInstance {
    ECE,
    AMAP,
    CN,
    AP, // ASIA-PACIFIC
    UNKNOWN;

    companion object {
        private val map: Map<String, CountryInstance> = values().associateBy(CountryInstance::name)

        fun forName(name: String) = map[name] ?: UNKNOWN
    }
}
