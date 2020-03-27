package com.daimler.mbcarkit.business.model.vehicle

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class DrivingModeStateTest {

    @Test
    fun testCorrectEnumPositionsOfDrivingModeState(softly: SoftAssertions) {
        val states = DrivingModeState.values()
        softly.assertThat(DrivingModeState.UNKNOWN).isEqualTo(states[0])
        softly.assertThat(DrivingModeState.OFF).isEqualTo(states[1])
        softly.assertThat(DrivingModeState.ON).isEqualTo(states[2])
        softly.assertThat(DrivingModeState.PENDING_OFF).isEqualTo(states[3])
        softly.assertThat(DrivingModeState.PENDING_ON).isEqualTo(states[4])
        softly.assertThat(DrivingModeState.ERROR).isEqualTo(states[5])
    }
}
