package com.daimler.mbcarkit.persistance.model

import io.realm.RealmObject

internal open class RealmVehicleChargingBreakClockTimer : RealmObject() {

    var action: Int? = null
    var endTimeHour: Int? = null
    var endTimeMin: Int? = null
    var startTimeHour: Int? = null
    var startTimeMin: Int? = null
    var timerId: Long? = null
}
