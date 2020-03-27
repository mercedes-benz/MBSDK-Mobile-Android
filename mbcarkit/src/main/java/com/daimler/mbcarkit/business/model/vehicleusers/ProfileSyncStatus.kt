package com.daimler.mbcarkit.business.model.vehicleusers

enum class ProfileSyncStatus {
    ON,
    OFF,
    @Deprecated("Use MANAGE_IN_HEAD_UNIT")
    ACTIVATE_IN_HEAD_UNIT,
    UNSUPPORTED,
    MANAGE_IN_HEAD_UNIT,
    SERVICE_NOT_ACTIVE,
    UNKNOWN
}
