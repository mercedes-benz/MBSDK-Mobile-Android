package com.daimler.mbcarkit.business.model.customerfence

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.onboardfence.OnboardFence

data class CustomerFenceViolation(
    val violationId: Int?,
    /**
     * UTC timestamp in seconds (ISO 9945)
     */
    val timestamp: Long?,
    val coordinates: GeoCoordinates?,
    val customerFence: CustomerFence?,
    val onboardFence: OnboardFence?
)
