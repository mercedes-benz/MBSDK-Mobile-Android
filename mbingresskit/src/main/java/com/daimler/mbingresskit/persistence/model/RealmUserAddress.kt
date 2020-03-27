package com.daimler.mbingresskit.persistence.model

import io.realm.RealmObject

internal open class RealmUserAddress : RealmObject() {

    var street: String? = null
    var houseNumber: String? = null
    var zipCode: String? = null
    var city: String? = null
    var countryCode: String? = null
    var state: String? = null
    var province: String? = null
    var streetType: String? = null
    var houseName: String? = null
    var floorNumber: String? = null
    var doorNumber: String? = null
    var addressLine1: String? = null
    var addressLine2: String? = null
    var addressLine3: String? = null
    var postOfficeBox: String? = null
}
