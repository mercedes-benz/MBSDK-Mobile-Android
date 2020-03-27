package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.APPLICATION_IDENTIFIER_FOR_NEW_ENTRY
import io.realm.RealmList
import io.realm.RealmObject

internal open class RealmTimeProfile : RealmObject() {

    var identifier: Int? = null
    var active: Boolean = false
    var days: RealmList<Int>? = null
    var hour: Int = 0
    var minute: Int = 0
    var applicationIdentifier: Int = APPLICATION_IDENTIFIER_FOR_NEW_ENTRY
}
