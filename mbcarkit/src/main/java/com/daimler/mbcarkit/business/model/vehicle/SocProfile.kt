package com.daimler.mbcarkit.business.model.vehicle

data class SocProfile(
    /**
     * Timestamp in seconds, UTC
     */
    val time: Long,

    /**
     * State of Charge
     * Range: 0..100
     */
    val soc: Int
)
