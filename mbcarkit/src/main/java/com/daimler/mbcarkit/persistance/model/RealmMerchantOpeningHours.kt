package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject

internal open class RealmMerchantOpeningHours : RealmObject() {

    var monday: RealmOpeningDay? = null
    var tuesday: RealmOpeningDay? = null
    var wednesday: RealmOpeningDay? = null
    var thursday: RealmOpeningDay? = null
    var friday: RealmOpeningDay? = null
    var saturday: RealmOpeningDay? = null
    var sunday: RealmOpeningDay? = null
}

internal fun RealmMerchantOpeningHours.cascadeDeleteFromRealm() {
    monday?.cascadeDeleteFromRealm()
    tuesday?.cascadeDeleteFromRealm()
    wednesday?.cascadeDeleteFromRealm()
    thursday?.cascadeDeleteFromRealm()
    friday?.cascadeDeleteFromRealm()
    saturday?.cascadeDeleteFromRealm()
    sunday?.cascadeDeleteFromRealm()
    deleteFromRealm()
}
