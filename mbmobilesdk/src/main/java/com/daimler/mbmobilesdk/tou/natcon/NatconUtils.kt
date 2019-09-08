package com.daimler.mbmobilesdk.tou.natcon

internal val NATCON_COUNTRIES = listOf("KR", "MY", "HK", "MO", "SG", "TH")

internal fun isNatconCountry(countryCode: String) = NATCON_COUNTRIES.contains(countryCode)