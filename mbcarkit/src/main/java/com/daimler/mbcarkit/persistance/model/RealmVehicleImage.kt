package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmVehicleImage : RealmObject() {

    @PrimaryKey
    var primaryKey = ""

    var finOrVin: String = ""
    var imageKey: String = ""
    var imageData: ByteArray? = null
}
