package com.daimler.mbcarkit.business.model.vehicle

enum class TheftWarningReasonState(val value: Int) {
    NO_ALARM(0),
    BASIS_ALARM(16),
    DOOR_FRONT_LEFT(17),
    DOOR_FRONT_RIGHT(18),
    DOOR_REAR_LEFT(19),
    DOOR_REAR_RIGHT(20),
    HOOD(21),
    DECKLID(22),
    COMMON_ALM_IN(23),
    PANIC(26),
    GLOVEBOX(27),
    CENTERBOX(28),
    REARBOX(29),
    SENSOR_VTA(32),
    ITS(33),
    ITS_SLV(34),
    TPS(35),
    SIREN(36),
    HOLD_COM(37),
    REMOTE(38),
    EXT_ITS_1(42),
    EXT_ITS_2(43),
    EXT_ITS_3(44),
    EXT_ITS_4(45);

    companion object {
        fun byValueOrElse(value: Int, default: (Int) -> TheftWarningReasonState): TheftWarningReasonState {
            return values().firstOrNull { it.value == value } ?: default(value)
        }
    }
}
