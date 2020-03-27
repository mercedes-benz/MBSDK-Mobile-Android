package com.daimler.mbcarkit.business.model.vehicle

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class SunroofBlindStateTest {

    @Test
    fun testCorrectEnumPositionsOfSunroofBlindState(softly: SoftAssertions) {
        val states = SunroofBlindState.values()
        softly.assertThat(SunroofBlindState.INTERMEDIATE).isEqualTo(states[0])
        softly.assertThat(SunroofBlindState.COMPLETELY_OPENED).isEqualTo(states[1])
        softly.assertThat(SunroofBlindState.COMPLETELY_CLOSED).isEqualTo(states[2])
        softly.assertThat(SunroofBlindState.OPENING).isEqualTo(states[3])
        softly.assertThat(SunroofBlindState.CLOSING).isEqualTo(states[4])
        softly.assertThat(SunroofBlindState.UNKNOWN).isEqualTo(states[5])
    }
}
