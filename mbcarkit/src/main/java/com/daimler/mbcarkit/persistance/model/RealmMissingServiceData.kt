package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.services.MissingServiceData
import io.realm.RealmObject

internal open class RealmMissingServiceData : RealmObject() {

    var missingAccountLinkage: RealmMissingAccountLinkage? = null
}

internal fun RealmMissingServiceData.cascadeDeleteFromRealm() {
    missingAccountLinkage?.cascadeDeleteFromRealm()
    deleteFromRealm()
}

internal fun RealmMissingServiceData.toMissingServiceData() =
    MissingServiceData(
        missingAccountLinkage?.toMissingAccountLinkage()
    )

internal fun RealmMissingServiceData.applyFrom(
    realmMissingAccountLinkage: RealmMissingAccountLinkage?
) = apply {
    missingAccountLinkage = realmMissingAccountLinkage
}
