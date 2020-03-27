package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject

internal open class RealmChargingProgram : RealmObject() {

    var program: Int? = null
    var maxSoc: Int? = null
    var autoUnlock: Boolean? = null
    var locationBasedCharging: Boolean? = null
    var weeklyProfile: Boolean? = null
    var clockTimer: Boolean? = null
    var maxChargingCurrent: Int? = null
    var ecoCharging: Boolean? = null
}
