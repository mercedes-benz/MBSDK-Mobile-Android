package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleSoftwareUpdateStatus
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiVehicleSoftwareUpdateItemTest {

    @Test
    fun `map from ApiVehicleSoftwareUpdateItem to VehicleSoftwareUpdateItem enum`(softly: SoftAssertions) {

        val apiUpdateItem = ApiVehicleSoftwareUpdateItem(
            TITLE,
            DESCRIPTION,
            Date(TIMESTAMP),
            ApiVehicleSoftwareUpdateStatus.SUCCESSFUL
        )

        with(apiUpdateItem.toVehicleSoftwareUpdateItem()) {
            softly.assertThat(title).isEqualTo(TITLE)
            softly.assertThat(description).isEqualTo(DESCRIPTION)
            softly.assertThat(timestamp).isEqualTo(Date(TIMESTAMP))
            softly.assertThat(status).isEqualTo(VehicleSoftwareUpdateStatus.SUCCESSFUL)
        }
    }

    companion object {
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val TIMESTAMP = 123456789L
    }
}
