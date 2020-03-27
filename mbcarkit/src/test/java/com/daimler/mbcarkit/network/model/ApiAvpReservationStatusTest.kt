package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(SoftAssertionsExtension::class)
class ApiAvpReservationStatusTest {

    @Test
    fun `map ApiAvpReservationStatus to AvpReservationStatus`(softly: SoftAssertions) {
        val apiReservationStatus = ApiAvpReservationStatus(
            "reservationId",
            ApiDriveType.DROP_OFF,
            ApiDriveStatus.COMPLETED,
            listOf("error1", "error2"),
            Date(),
            "parkedLocation"
        )
        val reservationStatus = apiReservationStatus.toAvpReservationStatus()

        softly.assertThat(reservationStatus.reservationId).isEqualTo(apiReservationStatus.reservationId)
        softly.assertThat(reservationStatus.driveType).isEqualTo(apiReservationStatus.driveType)
        softly.assertThat(reservationStatus.driveStatus).isEqualTo(apiReservationStatus.driveStatus)
        softly.assertThat(reservationStatus.errorIds).isEqualTo(apiReservationStatus.errorIds)
        softly.assertThat(reservationStatus.estimatedTimeOfArrival).isEqualTo(apiReservationStatus.estimatedTimeOfArrival)
        softly.assertThat(reservationStatus.parkedLocation).isEqualTo(apiReservationStatus.parkedLocation)
    }
}
