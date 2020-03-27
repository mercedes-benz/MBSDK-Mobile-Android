package com.daimler.mbcarkit.business.model.vehicle

enum class StatusEnum(val value: Int) {
    INVALID(4),
    NO_VALUE(1),
    VALID(0);

    companion object {
        fun from(value: Int?): StatusEnum =
            value?.let {
                findValue ->
                StatusEnum.values().firstOrNull { it.value == findValue }
            } ?: INVALID
    }
}
