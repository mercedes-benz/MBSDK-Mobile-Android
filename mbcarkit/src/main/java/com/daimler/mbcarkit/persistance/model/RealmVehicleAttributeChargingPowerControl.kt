package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.ChargingStatusForPowerControl
import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import io.realm.RealmObject

internal open class RealmVehicleAttributeChargingPowerControl : RealmObject(), RealmTimeObject {

    override var timestamp: Long? = null
    var status: Int = StatusEnum.INVALID.value
    var displayValue: String? = null
    var displayUnitCaseOrdinal: Int = DisplayUnitCase.DISPLAYUNIT_NOT_SET.ordinal
    var displayUnitOrdinal: Int = 0

    var chargingStatus: Int = ChargingStatusForPowerControl.NOT_DEFINED.ordinal
    var controlDuration: Int? = null
    var controlInfo: Int? = null
    var chargingPower: Int? = null
    var serviceStatus: Int? = null
    var serviceAvailable: Int? = null
    var useCase: Int? = null
}
