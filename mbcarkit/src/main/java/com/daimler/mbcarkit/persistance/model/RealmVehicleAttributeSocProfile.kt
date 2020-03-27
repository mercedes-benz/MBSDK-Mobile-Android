package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import io.realm.RealmList
import io.realm.RealmObject

internal open class RealmVehicleAttributeSocProfile(
    var status: Int = StatusEnum.INVALID.value,
    override var timestamp: Long? = null,
    var times: RealmList<Long> = RealmList(),
    var soc: RealmList<Int> = RealmList(),
    var displayValue: String? = null,
    var displayUnitCaseOrdinal: Int = DisplayUnitCase.DISPLAYUNIT_NOT_SET.ordinal,
    var displayUnitOrdinal: Int = 0
) : RealmObject(), RealmTimeObject
