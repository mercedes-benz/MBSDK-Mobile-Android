package com.daimler.mbcarkit.business.model.consumption

data class ConsumptionData(
    /**
     *
     */
    val changed: Boolean,
    /**
     *
     */
    val value: List<ConsumptionValue>
)
