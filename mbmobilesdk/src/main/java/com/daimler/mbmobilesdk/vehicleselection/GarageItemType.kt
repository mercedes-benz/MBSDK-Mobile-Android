package com.daimler.mbmobilesdk.vehicleselection

internal enum class GarageItemType(val isVehicleType: Boolean) {
    ADD(false),
    FULL_ASSIGNED(true),
    PENDING(true),
    INSUFFICIENT(true)
}