package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import io.realm.RealmList
import io.realm.RealmObject

internal open class RealmVehicleAttributeChargingBreakClockTimer : RealmObject(), RealmTimeObject {

    override var timestamp: Long? = null
    var status: Int = StatusEnum.INVALID.value
    var displayValue: String? = null
    var displayUnitCaseOrdinal: Int = DisplayUnitCase.DISPLAYUNIT_NOT_SET.ordinal
    var displayUnitOrdinal: Int = 0

    var clockTimers: RealmList<RealmVehicleChargingBreakClockTimer> = RealmList()
}
