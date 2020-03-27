package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import io.realm.RealmList
import io.realm.RealmObject

internal open class RealmVehicleAttributeWeeklyProfile(
    var status: Int = StatusEnum.INVALID.value,
    override var timestamp: Long? = null,
    var singleEntriesActivatable: Boolean = false,
    var maxSlots: Int = 0,
    var maxTimeProfiles: Int = 0,
    var currentSlots: Int = 0,
    var currentTimeProfiles: Int = 0,
    var timeProfiles: RealmList<RealmTimeProfile> = RealmList()
) : RealmObject(), RealmTimeObject
