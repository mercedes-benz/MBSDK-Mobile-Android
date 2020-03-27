package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapability
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPrecondition
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmSendToCarCapability : RealmObject() {

    @PrimaryKey
    var finOrVin: String = ""

    var capabilities: RealmList<Int> = RealmList()
    var preconditions: RealmList<Int> = RealmList()

    internal companion object {

        const val FIELD_VIN = "finOrVin"
    }
}

internal fun RealmSendToCarCapability.toSendToCarCapabilities() = SendToCarCapabilities(
    capabilities.mapNotNull { SendToCarCapability.values().getOrNull(it) }.toList(),
    preconditions.mapNotNull { SendToCarPrecondition.values().getOrNull(it) }.toList()
)
