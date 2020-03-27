package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiVehicleSoftwareUpdateInformationTest {

    @Test
    fun `map from ApiVehicleSoftwareUpdateInformation to VehicleSoftwareUpdateInformation enum`(softly: SoftAssertions) {
        val apiInfoItem = createTestSubject()
        with(apiInfoItem.toVehicleSoftwareUpdateInformation()) {
            softly.assertThat(totalUpdates).isEqualTo(TOTAL_UPDATES)
            softly.assertThat(availableUpdates).isEqualTo(AVAILABLE_UPDATES)
            softly.assertThat(lastSynchronization).isEqualTo(Date(LAST_SYNC_TIMESTAMP))
            softly.assertThat(updates.size).isEqualTo(2)
        }
    }

    private fun createTestSubject() =
        ApiVehicleSoftwareUpdateInformation(
            TOTAL_UPDATES,
            AVAILABLE_UPDATES,
            Date(LAST_SYNC_TIMESTAMP),
            createTestUpdatesList()
        )

    private fun createTestUpdatesList() =
        listOf(
            ApiVehicleSoftwareUpdateItem(TITLE, DESCRIPTION, Date(TIMESTAMP), ApiVehicleSoftwareUpdateStatus.SUCCESSFUL),
            ApiVehicleSoftwareUpdateItem(TITLE, DESCRIPTION, Date(TIMESTAMP), ApiVehicleSoftwareUpdateStatus.SUCCESSFUL)
        )

    companion object {
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val TIMESTAMP = 123456789L

        private const val TOTAL_UPDATES = 50
        private const val AVAILABLE_UPDATES = 3
        private const val LAST_SYNC_TIMESTAMP = 123456789L
    }
}
