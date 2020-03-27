package com.daimler.mbingresskit.persistence

import com.daimler.mbingresskit.common.Country

interface CountryCache {

    fun overwriteCache(countries: List<Country>, locale: String)

    fun loadCountries(locale: String): List<Country>?

    fun deleteCountries(countries: List<Country>, locale: String)

    fun deleteCountriesForLocale(locale: String)

    fun deleteAll()
}
