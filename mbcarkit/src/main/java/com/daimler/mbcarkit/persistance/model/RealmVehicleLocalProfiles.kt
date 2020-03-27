package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmVehicleLocalProfiles : RealmObject() {

    @PrimaryKey
    var id: String = ""

    var name: String = ""

    companion object {
        const val FIELD_ID = "id"
    }
}

internal fun RealmVehicleLocalProfiles.cascadeDeleteFromRealm() {
    deleteFromRealm()
}
