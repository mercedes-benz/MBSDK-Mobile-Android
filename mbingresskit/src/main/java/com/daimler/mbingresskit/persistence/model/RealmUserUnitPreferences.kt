package com.daimler.mbingresskit.persistence.model

import io.realm.RealmObject

internal open class RealmUserUnitPreferences : RealmObject() {

    var clockHours: Int? = null
    var speedDistance: Int? = null
    var consumptionCo: Int? = null
    var consumptionEv: Int? = null
    var consumptionGas: Int? = null
    var tirePressure: Int? = null
    var temperature: Int? = null
}
