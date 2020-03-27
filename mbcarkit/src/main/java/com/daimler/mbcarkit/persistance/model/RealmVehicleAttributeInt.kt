package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import io.realm.RealmObject

internal open class RealmVehicleAttributeInt(
    var status: Int = StatusEnum.INVALID.value,
    override var timestamp: Long? = null,
    var value: Int = 0,
    var displayValue: String? = null,
    var displayUnitCaseOrdinal: Int = DisplayUnitCase.DISPLAYUNIT_NOT_SET.ordinal,
    var displayUnitOrdinal: Int = 0
) : RealmObject(), RealmTimeObject
