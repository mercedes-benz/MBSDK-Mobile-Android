package com.daimler.mbprotokit.dto.car.vehicledata

import com.daimler.mbprotokit.generated.VehicleEvents

data class SpeedAlertConfiguration(
    val endTimestampInSeconds: Long,
    val thresholdInKilometersPerHour: Int,
    val thresholdDisplayValue: String
) {

    companion object {
        fun mapToSpeedAlertConfigurations(): (VehicleEvents.SpeedAlertConfigurationValue?) -> List<SpeedAlertConfiguration>? = {
            it?.speedAlertConfigurationsList?.map { config ->
                SpeedAlertConfiguration(
                    config.endTimestampInS,
                    config.thresholdInKph,
                    config.thresholdDisplayValue
                )
            }
        }
    }
}
