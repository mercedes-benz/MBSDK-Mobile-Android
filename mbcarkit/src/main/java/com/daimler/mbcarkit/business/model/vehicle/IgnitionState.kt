package com.daimler.mbcarkit.business.model.vehicle

enum class IgnitionState(internal val value: Int) {
    LOCK(0),
    OFF(1),
    ACCESSORY(2),
    ON(4),
    START(5),
    UNKNOWN(-1);

    companion object {
        fun byValueOrElse(value: Int, default: (Int) -> IgnitionState): IgnitionState {
            return values().firstOrNull { it.value == value } ?: default(value)
        }
    }
}
