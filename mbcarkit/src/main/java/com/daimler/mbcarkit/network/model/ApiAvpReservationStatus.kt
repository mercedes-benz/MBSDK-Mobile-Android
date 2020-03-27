package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.AvpReservationStatus
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ApiAvpReservationStatus(
    @SerializedName("reservationId") val reservationId: String?,
    @SerializedName("driveType") val driveType: ApiDriveType?,
    @SerializedName("driveStatus") val driveStatus: ApiDriveStatus?,
    @SerializedName("errorIds") val errorIds: List<String>?,
    @SerializedName("estimatedTimeOfArrival") val estimatedTimeOfArrival: Date?,
    @SerializedName("parkedLocation") val parkedLocation: String?
)

internal fun ApiAvpReservationStatus.toAvpReservationStatus(): AvpReservationStatus =
    AvpReservationStatus(
        reservationId,
        driveType,
        driveStatus,
        errorIds,
        estimatedTimeOfArrival,
        parkedLocation
    )
