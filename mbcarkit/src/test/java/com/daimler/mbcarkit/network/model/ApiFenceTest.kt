package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.ActiveTimes
import com.daimler.mbcarkit.business.model.geofencing.Fence
import com.daimler.mbcarkit.business.model.geofencing.GeofenceViolationType
import com.daimler.mbcarkit.business.model.geofencing.Shape
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiFenceTest {

    @Test
    fun `map Fence to ApiFence`(softly: SoftAssertions) {
        val fence = Fence(
            0,
            false,
            "name",
            ActiveTimes(
                listOf(),
                0,
                1
            ),
            GeofenceViolationType.LEAVE,
            Shape(null, null)
        )
        val apiFence = ApiFence.fromFence(fence)

        softly.assertThat(apiFence.id).isNull()
        softly.assertThat(apiFence.isActive).isNull()
        softly.assertThat(apiFence.name).isEqualTo(fence.name)
        softly.assertThat(apiFence.activeTimes.begin).isEqualTo(fence.activeTimes.begin)
        softly.assertThat(apiFence.activeTimes.end).isEqualTo(fence.activeTimes.end)
        softly.assertThat(apiFence.shape.circle).isNull()
        softly.assertThat(apiFence.shape.polygon).isNull()
    }
}
