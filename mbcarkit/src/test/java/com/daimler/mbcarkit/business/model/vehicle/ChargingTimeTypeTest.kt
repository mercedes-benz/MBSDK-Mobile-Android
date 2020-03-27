package com.daimler.mbcarkit.business.model.vehicle

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ChargingTimeTypeTest {

    @Test
    fun testCorrectEnumPositionsOfChargingTimeType(softly: SoftAssertions) {
        val states = ChargingTimeType.values()
        softly.assertThat(states[0]).isEqualTo(ChargingTimeType.ABSOLUTE_TIME)
        softly.assertThat(states[1]).isEqualTo(ChargingTimeType.RELATIVE_TIME)
        softly.assertThat(states[2]).isEqualTo(ChargingTimeType.UNKNOWN)
    }
}
