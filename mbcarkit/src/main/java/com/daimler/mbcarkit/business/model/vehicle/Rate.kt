package com.daimler.mbcarkit.business.model.vehicle

enum class Rate(val value: Int) {
    INVALID_PRICE(0),
    LOW_PRICE(33),
    NORMAL_PRICE(44),
    HIGH_PRICE(66),
    UNRECOGNIZED(-1),
}
