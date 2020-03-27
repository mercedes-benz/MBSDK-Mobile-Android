package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.network.model.ApiDriveStatus
import com.daimler.mbcarkit.network.model.ApiDriveType
import java.util.Date

data class AvpReservationStatus(
    val reservationId: String?,
    val driveType: ApiDriveType?,
    val driveStatus: ApiDriveStatus?,
    val errorIds: List<String>?,
    val estimatedTimeOfArrival: Date?,
    val parkedLocation: String?
)
