package com.daimler.mbingresskit.persistence.model

import io.realm.RealmObject

internal open class RealmUserAdaptionValues : RealmObject() {

    var bodyHeight: Int? = null
    var preAdjustment: Boolean? = null
    var alias: String? = null
}
