package com.daimler.mbingresskit.persistence.model

import io.realm.RealmObject

internal open class RealmCommunicationPreferences : RealmObject() {

    var contactByPhone: Boolean? = null
    var contactByLetter: Boolean? = null
    var contactByMail: Boolean? = null
    var contactBySms: Boolean? = null
}
