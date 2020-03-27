package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents

enum class AttributeStatus(val attributeStatus: VehicleEvents.AttributeStatus) {
    VALID(VehicleEvents.AttributeStatus.VALUE_VALID),
    NO_VALUE(VehicleEvents.AttributeStatus.VALUE_NOT_RECEIVED),
    INVALID(VehicleEvents.AttributeStatus.VALUE_INVALID),
    VALUE_NOT_AVAILABLE(VehicleEvents.AttributeStatus.VALUE_NOT_AVAILABLE),
    UNRECOGNIZED(VehicleEvents.AttributeStatus.UNRECOGNIZED);

    companion object {
        fun map(attributeStatus: VehicleEvents.AttributeStatus?) = values().find {
            attributeStatus == it.attributeStatus
        } ?: UNRECOGNIZED
    }
}
