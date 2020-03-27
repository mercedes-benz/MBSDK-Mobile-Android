package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// TODO 18.09.2019 check if it is necessary to persist all merchant attributes
internal open class RealmMerchant : RealmObject() {

    @PrimaryKey
    var id: String = ""

    var legalName: String? = null
    var address: RealmMerchantAddress? = null
    var openingHours: RealmMerchantOpeningHours? = null

    companion object {
        const val FIELD_ID = "id"
    }
}

internal fun RealmMerchant.cascadeDeleteFromRealm() {
    address?.deleteFromRealm()
    openingHours?.cascadeDeleteFromRealm()
    deleteFromRealm()
}
