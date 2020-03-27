package com.daimler.mbcarkit.business.model.vehicle

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ChargingErrorWimTest {

    @Test
    fun testCorrectEnumPositionsOfChargingErrorWimTest(softly: SoftAssertions) {
        val states = ChargingErrorWim.values()
        softly.assertThat(states[0]).isEqualTo(ChargingErrorWim.NO_ERROR)
        softly.assertThat(states[1]).isEqualTo(ChargingErrorWim.MESSAGE1)
        softly.assertThat(states[2]).isEqualTo(ChargingErrorWim.MESSAGE2)
        softly.assertThat(states[3]).isEqualTo(ChargingErrorWim.MESSAGE3)
        softly.assertThat(states[4]).isEqualTo(ChargingErrorWim.MESSAGE4)
        softly.assertThat(states[5]).isEqualTo(ChargingErrorWim.MESSAGE5)
        softly.assertThat(states[6]).isEqualTo(ChargingErrorWim.MESSAGE6)
        softly.assertThat(states[7]).isEqualTo(ChargingErrorWim.MESSAGE7)
        softly.assertThat(states[8]).isEqualTo(ChargingErrorWim.MESSAGE8)
        softly.assertThat(states[9]).isEqualTo(ChargingErrorWim.MESSAGE9)
        softly.assertThat(states[10]).isEqualTo(ChargingErrorWim.MESSAGE10)
        softly.assertThat(states[11]).isEqualTo(ChargingErrorWim.MESSAGE11)
        softly.assertThat(states[12]).isEqualTo(ChargingErrorWim.MESSAGE12)
        softly.assertThat(states[13]).isEqualTo(ChargingErrorWim.MESSAGE13)
        softly.assertThat(states[14]).isEqualTo(ChargingErrorWim.MESSAGE14)
        softly.assertThat(states[15]).isEqualTo(ChargingErrorWim.UNKNOWN)
    }
}
