package com.daimler.mbingresskit.persistence

import com.daimler.mbingresskit.common.Country
import com.daimler.mbingresskit.common.CountryInstance
import com.daimler.mbingresskit.common.CountryLocale
import com.daimler.mbingresskit.persistence.model.RealmCountry
import com.daimler.mbingresskit.persistence.model.RealmCountryLocale
import com.daimler.mbingresskit.persistence.model.cascadeDeleteFromRealm
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import io.realm.kotlin.where

internal class RealmCountryCache(
    private val realm: Realm
) : CountryCache {

    override fun overwriteCache(countries: List<Country>, locale: String) {
        deleteCountriesForLocale(locale)
        realm.executeTransaction { r ->
            countries.forEach {
                persistCountry(it, locale, r)
            }
        }
    }

    override fun loadCountries(locale: String): List<Country>? =
        realmCountriesForLocale(realm, locale)
            ?.takeIf { it.isNotEmpty() }
            ?.map { mapRealmCountryToCountry(it) }

    override fun deleteCountries(countries: List<Country>, locale: String) {
        val keys = countries.map { generatePrimaryKey(it, locale) }
        realm.executeTransaction { r ->
            val realmCountries = r.where<RealmCountry>()
                .`in`(RealmCountry.FIELD_KEY, keys.toTypedArray())
                .findAll()
            realmCountries.forEach {
                it.cascadeDeleteFromRealm()
            }
        }
    }

    override fun deleteCountriesForLocale(locale: String) {
        realm.executeTransaction { r ->
            val realmCountries = realmCountriesForLocale(r, locale)
            realmCountries.forEach {
                it.cascadeDeleteFromRealm()
            }
            realmCountries.deleteAllFromRealm()
        }
    }

    override fun deleteAll() {
        realm.executeTransaction {
            it.delete<RealmCountryLocale>()
            it.delete<RealmCountry>()
        }
    }

    private fun persistCountry(country: Country, locale: String, realm: Realm) {
        realm.createObject<RealmCountry>(generatePrimaryKey(country, locale)).apply {
            countryCode = country.countryCode
            countryName = country.countryName
            instance = country.instance.ordinal
            legalRegion = country.legalRegion
            defaultLocale = country.defaultLocale
            natconCountry = country.natconCountry
            connectCountry = country.connectCountry
            translationLocale = locale
            country.locales?.let { locales = persistCountryLocales(locales, it) }
            availability = country.availability

            realm.copyToRealmOrUpdate(this)
        }
    }

    private fun persistCountryLocales(realmList: RealmList<RealmCountryLocale>, locales: List<CountryLocale>): RealmList<RealmCountryLocale> {
        val realm = realmList.realm
        realmList.deleteAllFromRealm()
        realmList.clear()
        locales.forEach {
            realm.createObject<RealmCountryLocale>().apply {
                code = it.localeCode
                name = it.localeName
                realmList.add(this)
            }
        }
        return realmList
    }

    private fun realmCountriesForLocale(realm: Realm, locale: String) =
        realm.where<RealmCountry>()
            .equalTo(RealmCountry.FIELD_LOCALE, locale)
            .findAll()

    private fun generatePrimaryKey(country: Country, locale: String) =
        "${country.instance.name}_${country.countryCode}_$locale"

    private fun mapRealmCountryToCountry(realmCountry: RealmCountry) =
        Country(
            realmCountry.countryCode.orEmpty(),
            realmCountry.countryName.orEmpty(),
            CountryInstance.values().getOrElse(realmCountry.instance ?: -1) { CountryInstance.UNKNOWN },
            realmCountry.legalRegion.orEmpty(),
            realmCountry.defaultLocale,
            realmCountry.natconCountry == true,
            realmCountry.connectCountry == true,
            realmCountry.locales.map { mapRealmCountryLocaleToCountryLocale(it) },
            realmCountry.availability
        )

    private fun mapRealmCountryLocaleToCountryLocale(realmCountryLocale: RealmCountryLocale) =
        CountryLocale(realmCountryLocale.code, realmCountryLocale.name)
}
