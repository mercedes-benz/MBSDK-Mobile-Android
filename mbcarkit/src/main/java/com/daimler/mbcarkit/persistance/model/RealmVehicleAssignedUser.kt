package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUserStatus
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmVehicleAssignedUser : RealmObject() {

    @PrimaryKey
    var authorizationId: String = ""

    var displayName: String = ""
    var email: String? = null
    var mobileNumber: String? = null
    var userImageUrl: String? = null
    var status: Int = VehicleAssignedUserStatus.UNKNOWN.ordinal
    var validUntil: Long? = null

    companion object {
        const val FIELD_ID = "authorizationId"
    }
}

internal fun RealmVehicleAssignedUser.cascadeDeleteFromRealm() {
    deleteFromRealm()
}
