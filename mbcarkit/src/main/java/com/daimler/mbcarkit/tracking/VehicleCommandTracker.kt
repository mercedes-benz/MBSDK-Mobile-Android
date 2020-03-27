package com.daimler.mbcarkit.tracking

import com.daimler.mbcarkit.business.model.command.CarVehicleApiCommand
import com.daimler.mbcarkit.business.model.command.VehicleCommandStatus
import com.daimler.mbcommonkit.tracking.MBTrackingService
import java.util.concurrent.TimeUnit
import kotlin.math.min

class VehicleCommandTracker {

    private val vehicleEventTimer = mutableMapOf<String, Long>()

    private companion object {
        private const val CAR_SERIES_MAX_LENGTH = 6
    }

    fun trackCarCommand(carCommand: CarVehicleApiCommand<*>) {
        val tag = carCommand.commandRequest()::class.java.simpleName
        MBTrackingService.trackEvent(
            CarKitEvent.VehicleCommandEvent(
                createTrackingData(carCommand, tag).toTrackingMap(),
                tag = tag
            )
        )
    }

    private fun createTrackingData(carCommand: CarVehicleApiCommand<*>, eventTag: String): CarTrackingModel {
        return CarTrackingModel(
            "",
            getCarSeries(carCommand),
            System.currentTimeMillis(),
            carCommand.state(),
            null,
            "-",
            "-",
            1,
            true,
            getDurationOrNull(eventTag, carCommand.state()),
            carCommand.state()
        )
    }

    private fun getCarSeries(carCommand: CarVehicleApiCommand<*>): String {
        val vin = carCommand.commandRequest().vin
        return vin.substring(0, min(vin.length, CAR_SERIES_MAX_LENGTH))
    }

    private fun getDurationOrNull(eventTag: String, commandStatus: VehicleCommandStatus): Long? {
        val startTime = vehicleEventTimer[eventTag]
        return when {
            startTime == null -> {
                vehicleEventTimer[eventTag] = System.currentTimeMillis()
                null
            }
            commandStatus == VehicleCommandStatus.FINISHED -> {
                vehicleEventTimer.remove(eventTag)
                stopTimeAndGetDurationInSec(startTime)
            }
            else -> stopTimeAndGetDurationInSec(startTime)
        }
    }

    private fun stopTimeAndGetDurationInSec(startTimeMillis: Long) = TimeUnit.MILLISECONDS.toSeconds(
        System.currentTimeMillis() - startTimeMillis
    )
}
