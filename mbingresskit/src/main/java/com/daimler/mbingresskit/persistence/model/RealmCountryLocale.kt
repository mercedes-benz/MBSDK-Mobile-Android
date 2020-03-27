package com.daimler.mbingresskit.persistence.model

import io.realm.RealmObject

internal open class RealmCountryLocale : RealmObject() {

    var code: String? = null
    var name: String? = null
}
