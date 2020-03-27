package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmVehicleDealer : RealmObject() {

    @PrimaryKey
    var internalId: String = ""

    var dealerId: String? = null
    var role: Int? = null
    var updatedAt: Long? = null
    var merchant: RealmMerchant? = null

    companion object {
        const val FIELD_ID = "internalId"
    }
}

internal fun RealmVehicleDealer.cascadeDeleteFromRealm() {
    merchant?.cascadeDeleteFromRealm()
    deleteFromRealm()
}
