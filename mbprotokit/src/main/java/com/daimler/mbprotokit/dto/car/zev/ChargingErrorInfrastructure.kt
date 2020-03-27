package com.daimler.mbprotokit.dto.car.zev

enum class ChargingErrorInfrastructure(val id: Int) {
    NO_ERROR(0),
    AC_INFRA_ERROR_AC(1),
    DC_INFRA_ERROR_DC(2),
    IND_INFRA_ERROR(3),
    AC_DC_INFRA_ERROR(4),
    AC_IND_INFRA_ERROR_AC(5),
    DC_IND_INFRA_ERROR_DC(6),
    CHARGE_ERROR(7);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ChargingErrorInfrastructure? = { map(it) }
    }
}
