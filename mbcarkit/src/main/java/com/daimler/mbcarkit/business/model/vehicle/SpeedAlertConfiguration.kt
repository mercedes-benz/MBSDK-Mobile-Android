package com.daimler.mbcarkit.business.model.vehicle

data class SpeedAlertConfiguration(
    val endTimestampInSeconds: Long,
    val thresholdInKilometersPerHour: Int,
    val thresholdDisplayValue: String
)
