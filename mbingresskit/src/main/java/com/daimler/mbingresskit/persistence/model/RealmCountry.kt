package com.daimler.mbingresskit.persistence.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmCountry : RealmObject() {

    @PrimaryKey
    var primaryKey: String = ""

    var countryCode: String? = null
    var countryName: String? = null
    var instance: Int? = null
    var legalRegion: String? = null
    var defaultLocale: String? = null
    var natconCountry: Boolean? = null
    var connectCountry: Boolean? = null
    var translationLocale: String? = null
    var locales: RealmList<RealmCountryLocale> = RealmList()
    var availability: Boolean = true

    companion object {

        const val FIELD_KEY = "primaryKey"
        const val FIELD_LOCALE = "translationLocale"
    }
}

internal fun RealmCountry.cascadeDeleteFromRealm() {
    locales.deleteAllFromRealm()
    deleteFromRealm()
}
