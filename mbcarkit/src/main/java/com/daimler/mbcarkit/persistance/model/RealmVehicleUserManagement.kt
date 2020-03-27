package com.daimler.mbcarkit.persistance.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmVehicleUserManagement : RealmObject() {

    @PrimaryKey
    var finOrVin: String = ""
    var maxProfiles: Int? = null
    var occupiedProfiles: Int? = null
    var owner: RealmVehicleAssignedUser? = null
    var users: RealmList<RealmVehicleAssignedUser>? = null
    var profiles: RealmList<RealmVehicleLocalProfiles>? = null

    companion object {
        const val FIELD_ID = "finOrVin"
    }
}

internal fun RealmVehicleUserManagement.cascadeDeleteFromRealm() {
    owner?.cascadeDeleteFromRealm()
    users?.toList()?.forEach { it.cascadeDeleteFromRealm() }
    users?.deleteAllFromRealm()
    profiles?.toList()?.forEach { it.cascadeDeleteFromRealm() }
    profiles?.deleteAllFromRealm()
    deleteFromRealm()
}
