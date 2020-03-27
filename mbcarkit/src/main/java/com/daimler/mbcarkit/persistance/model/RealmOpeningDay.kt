package com.daimler.mbcarkit.persistance.model

import io.realm.RealmList
import io.realm.RealmObject

internal open class RealmOpeningDay : RealmObject() {

    var status: String? = null
    var periods: RealmList<RealmDayPeriod>? = null
}

internal fun RealmOpeningDay.cascadeDeleteFromRealm() {
    periods?.deleteAllFromRealm()
    deleteFromRealm()
}
