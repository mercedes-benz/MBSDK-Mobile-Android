package com.daimler.mbprotokit.dto.car.zev

enum class ChargingStatusForPowerControl(val id: Int) {
    NOT_DEFINED(0),
    DEACTIVATED(1),
    ACTIVATED(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }
    }
}
