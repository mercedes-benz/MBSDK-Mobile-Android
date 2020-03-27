package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.ActiveTimes
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiActiveTimesTest {

    @Test
    fun `map ActiveTimes to ApiActiveTimes`(softly: SoftAssertions) {
        val activeTimes = ActiveTimes(
            listOf(1, 2, 3),
            1234,
            5678
        )
        val apiActiveTimes = ApiActiveTimes.fromActiveTimes(activeTimes)

        softly.assertThat(apiActiveTimes.days).isEqualTo(activeTimes.days)
        softly.assertThat(apiActiveTimes.begin).isEqualTo(activeTimes.begin)
        softly.assertThat(apiActiveTimes.end).isEqualTo(activeTimes.end)
    }
}
