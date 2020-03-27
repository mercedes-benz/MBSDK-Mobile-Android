package com.daimler.mbcarkit.persistance.model

import io.realm.RealmList
import io.realm.RealmObject

internal open class RealmPrerequisiteCheck : RealmObject() {

    var name: String? = null
    var missingInformation: RealmList<Int> = RealmList()
    var actions: RealmList<Int> = RealmList()
}

internal fun RealmPrerequisiteCheck.cascadeDeleteFromRealm() {
    missingInformation.deleteAllFromRealm()
    actions.deleteAllFromRealm()
    deleteFromRealm()
}
