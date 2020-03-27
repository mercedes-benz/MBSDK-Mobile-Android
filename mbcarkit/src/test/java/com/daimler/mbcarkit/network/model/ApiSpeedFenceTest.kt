package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.onboardfence.SyncStatus
import com.daimler.mbcarkit.business.model.speedfence.SpeedFence
import com.daimler.mbcarkit.business.model.speedfence.SpeedFenceViolationType
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiSpeedFenceTest {

    @Test
    fun `map ApiSpeedFence from SpeedFence`(softly: SoftAssertions) {
        val speedFence = SpeedFence(
            0,
            "",
            true,
            2,
            4,
            5,
            listOf(SpeedFenceViolationType.LEAVE),
            6L,
            7,
            SyncStatus.PENDING
        )
        val apiSpeedFence = ApiSpeedFence.fromSpeedFence(speedFence)

        softly.assertThat(apiSpeedFence.geoFenceId).isEqualTo(speedFence.geoFenceId)
        softly.assertThat(apiSpeedFence.name).isEqualTo(speedFence.name)
        softly.assertThat(apiSpeedFence.isActive).isEqualTo(speedFence.isActive)
        softly.assertThat(apiSpeedFence.endTime).isEqualTo(speedFence.endTime)
        softly.assertThat(apiSpeedFence.threshold).isEqualTo(speedFence.threshold)
        softly.assertThat(apiSpeedFence.violationDelay).isEqualTo(speedFence.violationDelay)
        softly.assertThat(apiSpeedFence.violationTypes?.get(0)?.name).isEqualTo(speedFence.violationTypes[0].name)
        softly.assertThat(apiSpeedFence.timestamp).isEqualTo(speedFence.timestamp)
        softly.assertThat(apiSpeedFence.speedFenceId).isEqualTo(speedFence.speedFenceId)
        softly.assertThat(apiSpeedFence.syncStatus?.name).isEqualTo(speedFence.syncStatus?.name)
    }

    @Test
    fun `map ApiSpeedFence to SpeedFence`(softly: SoftAssertions) {
        val apiSpeedFence = ApiSpeedFence(
            0,
            "",
            true,
            2,
            4,
            5,
            listOf(ApiSpeedfenceViolationType.ENTER),
            6L,
            7,
            ApiSyncStatus.PENDING
        )
        val speedFence = apiSpeedFence.toSpeedFence()

        softly.assertThat(speedFence.geoFenceId).isEqualTo(apiSpeedFence.geoFenceId)
        softly.assertThat(speedFence.name).isEqualTo(apiSpeedFence.name)
        softly.assertThat(speedFence.isActive).isEqualTo(apiSpeedFence.isActive)
        softly.assertThat(speedFence.endTime).isEqualTo(apiSpeedFence.endTime)
        softly.assertThat(speedFence.threshold).isEqualTo(apiSpeedFence.threshold)
        softly.assertThat(speedFence.violationDelay).isEqualTo(apiSpeedFence.violationDelay)
        softly.assertThat(speedFence.violationTypes[0].name).isEqualTo(apiSpeedFence.violationTypes?.get(0)?.name)
        softly.assertThat(speedFence.timestamp).isEqualTo(apiSpeedFence.timestamp)
        softly.assertThat(speedFence.speedFenceId).isEqualTo(apiSpeedFence.speedFenceId)
        softly.assertThat(speedFence.syncStatus?.name).isEqualTo(apiSpeedFence.syncStatus?.name)
    }
}
