package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject

internal open class RealmDayPeriod : RealmObject() {

    var from: String? = null
    var until: String? = null
}
