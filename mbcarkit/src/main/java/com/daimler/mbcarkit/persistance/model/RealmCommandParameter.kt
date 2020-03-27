package com.daimler.mbcarkit.persistance.model

import io.realm.RealmList
import io.realm.RealmObject

internal open class RealmCommandParameter : RealmObject() {

    var name: Int? = null
    var minValue: Double? = null
    var maxValue: Double? = null
    var steps: Double? = null
    var allowedEnums: RealmList<Int>? = null
    var allowedBools: Int? = null
}

internal fun RealmCommandParameter.cascadeDeleteFromRealm() {
    allowedEnums?.deleteAllFromRealm()
    deleteFromRealm()
}
