package com.daimler.mbmobilesdk.business.model

enum class PushDistributionProfile {
    DEV,
    @Deprecated("Unused, use either DEV or STORE.")
    AD_HOC,
    STORE
}