package com.daimler.mbcarkit.business.model.onboardfence

enum class SyncStatus {
    PENDING,
    TIMEOUT,
    HOLD,
    FAILED,
    FINISHED,
    AWAITING_VEP;

    companion object {
        val map: Map<String, SyncStatus> = values().associateBy(SyncStatus::name)
    }
}
