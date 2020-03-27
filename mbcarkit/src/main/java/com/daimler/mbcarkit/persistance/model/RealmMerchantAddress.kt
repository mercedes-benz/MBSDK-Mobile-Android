package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject

internal open class RealmMerchantAddress : RealmObject() {

    var street: String? = null
    var addressAddition: String? = null
    var zipCode: String? = null
    var city: String? = null
    var district: String? = null
    var countryIsoCode: String? = null
}
