package com.daimler.mbcarkit.persistance.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmCommand : RealmObject() {

    @PrimaryKey
    var key: String = ""

    var name: Int? = null
    var available: Boolean? = null
    var parameters: RealmList<RealmCommandParameter> = RealmList()
    var additionalInformation: RealmList<String>? = null
    var finOrVin: String? = null

    internal companion object {

        const val FIELD_KEY = "key"
        const val FIELD_VIN = "finOrVin"
    }
}

internal fun RealmCommand.cascadeDeleteFromRealm() {
    parameters.toList().forEach { it.cascadeDeleteFromRealm() }
    parameters.deleteAllFromRealm()
    deleteFromRealm()
}
