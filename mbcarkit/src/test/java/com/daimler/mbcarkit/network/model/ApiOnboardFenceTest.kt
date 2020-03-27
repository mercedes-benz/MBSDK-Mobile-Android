package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.onboardfence.FenceType
import com.daimler.mbcarkit.business.model.onboardfence.OnboardFence
import com.daimler.mbcarkit.business.model.onboardfence.SyncStatus
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiOnboardFenceTest {

    @Test
    fun `map ApiOnboardFence from OnboardFence`(softly: SoftAssertions) {
        val onboardFence = OnboardFence(
            0,
            "name",
            true,
            GeoCoordinates(1.0, 2.0),
            FenceType.CIRCLE,
            2,
            3,
            listOf(GeoCoordinates(3.0, 4.0)),
            SyncStatus.PENDING
        )
        val apiOnboardFence = ApiOnboardFence.fromOnboardFence(onboardFence)

        softly.assertThat(apiOnboardFence.geoFenceId).isEqualTo(onboardFence.geoFenceId)
        softly.assertThat(apiOnboardFence.name).isEqualTo(onboardFence.name)
        softly.assertThat(apiOnboardFence.isActive).isEqualTo(onboardFence.isActive)
        softly.assertThat(apiOnboardFence.center.latitude).isEqualTo(onboardFence.center.latitude)
        softly.assertThat(apiOnboardFence.center.longitude).isEqualTo(onboardFence.center.longitude)
        softly.assertThat(apiOnboardFence.fenceType?.name).isEqualTo(onboardFence.fenceType?.name)
        softly.assertThat(apiOnboardFence.radiusInMeters).isEqualTo(onboardFence.radiusInMeters)
        softly.assertThat(apiOnboardFence.verticesCount).isEqualTo(onboardFence.verticesCount)
        softly.assertThat(apiOnboardFence.verticesPositions?.size).isEqualTo(onboardFence.verticesPositions?.size)
        softly.assertThat(apiOnboardFence.verticesPositions?.get(0)?.latitude).isEqualTo(onboardFence.verticesPositions?.get(0)?.latitude)
        softly.assertThat(apiOnboardFence.verticesPositions?.get(0)?.longitude).isEqualTo(onboardFence.verticesPositions?.get(0)?.longitude)
        softly.assertThat(apiOnboardFence.syncStatus?.name).isEqualTo(onboardFence.syncStatus?.name)
    }

    @Test
    fun `map ApiOnboardFence to OnboardFence`(softly: SoftAssertions) {
        val apiOnboardFence = ApiOnboardFence(
            0,
            "name",
            true,
            ApiGeoCoordinates(1.0, 2.0),
            ApiFenceType.CIRCLE,
            2,
            3,
            listOf(ApiGeoCoordinates(3.0, 4.0)),
            ApiSyncStatus.FINISHED
        )
        val onboardFence = apiOnboardFence.toOnboardFence()

        softly.assertThat(onboardFence.geoFenceId).isEqualTo(apiOnboardFence.geoFenceId)
        softly.assertThat(onboardFence.name).isEqualTo(apiOnboardFence.name)
        softly.assertThat(onboardFence.isActive).isEqualTo(apiOnboardFence.isActive)
        softly.assertThat(onboardFence.center.latitude).isEqualTo(apiOnboardFence.center.latitude)
        softly.assertThat(onboardFence.center.longitude).isEqualTo(apiOnboardFence.center.longitude)
        softly.assertThat(onboardFence.fenceType?.name).isEqualTo(apiOnboardFence.fenceType?.name)
        softly.assertThat(onboardFence.radiusInMeters).isEqualTo(apiOnboardFence.radiusInMeters)
        softly.assertThat(onboardFence.verticesCount).isEqualTo(apiOnboardFence.verticesCount)
        softly.assertThat(onboardFence.verticesPositions?.size).isEqualTo(apiOnboardFence.verticesPositions?.size)
        softly.assertThat(onboardFence.verticesPositions?.get(0)?.latitude).isEqualTo(apiOnboardFence.verticesPositions?.get(0)?.latitude)
        softly.assertThat(onboardFence.verticesPositions?.get(0)?.longitude).isEqualTo(apiOnboardFence.verticesPositions?.get(0)?.longitude)
        softly.assertThat(onboardFence.syncStatus?.name).isEqualTo(apiOnboardFence.syncStatus?.name)
    }
}
