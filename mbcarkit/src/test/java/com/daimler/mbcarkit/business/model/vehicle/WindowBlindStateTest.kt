package com.daimler.mbcarkit.business.model.vehicle

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class WindowBlindStateTest {

    @Test
    fun testCorrectEnumPositionsOfSunroofBlindState(softly: SoftAssertions) {
        val states = WindowBlindState.values()
        softly.assertThat(WindowBlindState.INTERMEDIATE).isEqualTo(states[0])
        softly.assertThat(WindowBlindState.COMPLETELY_OPENED).isEqualTo(states[1])
        softly.assertThat(WindowBlindState.COMPLETELY_CLOSED).isEqualTo(states[2])
        softly.assertThat(WindowBlindState.UNKNOWN).isEqualTo(states[3])
    }
}
