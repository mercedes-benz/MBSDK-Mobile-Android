package com.daimler.mbprotokit.dto.car.zev

enum class Rate(val startInclusive: Int, val endInclusive: Int) {
    INVALID_PRICE(0, 0),
    LOW_PRICE(1, 33),
    NORMAL_PRICE(34, 65),
    HIGH_PRICE(66, 100),
    UNRECOGNIZED(-1, -1);

    companion object {
        fun map(rate: Int) = values().find {
            it.startInclusive <= rate && rate <= it.endInclusive
        } ?: UNRECOGNIZED
    }
}
