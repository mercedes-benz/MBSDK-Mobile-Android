package com.daimler.mbcarkit.persistance.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmService : RealmObject() {

    @PrimaryKey
    var internalId: String = ""

    var vehicleFinOrVin: String? = null
    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var shortName: String? = null
    var categoryName: String? = null
    var allowedActions: RealmList<Int> = RealmList()
    var activationStatus: Int? = null
    var desiredActivationStatus: Int? = null
    var actualActivationStatus: Int? = null
    var virtualActivationStatus: Int? = null
    var prerequisiteChecks: RealmList<RealmPrerequisiteCheck> = RealmList()
    var rights: RealmList<Int> = RealmList()
    var missingData: RealmMissingServiceData? = null

    companion object {
        const val FIELD_ID = "internalId"
        const val FIELD_VIN = "vehicleFinOrVin"
        const val FIELD_MISSING_DATA = "missingData"
    }
}

internal fun RealmService.cascadeDeleteFromRealm() {
    allowedActions.deleteAllFromRealm()
    prerequisiteChecks.toList().forEach { it.cascadeDeleteFromRealm() }
    prerequisiteChecks.deleteAllFromRealm()
    rights.deleteAllFromRealm()
    missingData?.cascadeDeleteFromRealm()
    deleteFromRealm()
}
