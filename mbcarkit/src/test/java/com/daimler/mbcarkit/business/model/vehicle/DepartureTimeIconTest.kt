package com.daimler.mbcarkit.business.model.vehicle

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class DepartureTimeIconTest {

    @Test
    fun testCorrectEnumPositionsOfDepartureTimeIcon(softly: SoftAssertions) {
        val states = DepartureTimeIcon.values()
        softly.assertThat(states[0]).isEqualTo(DepartureTimeIcon.INACTV)
        softly.assertThat(states[1]).isEqualTo(DepartureTimeIcon.ADHOC_ACTV)
        softly.assertThat(states[2]).isEqualTo(DepartureTimeIcon.WEEK_DEP_TM_ACTV)
        softly.assertThat(states[3]).isEqualTo(DepartureTimeIcon.SKIP)
        softly.assertThat(states[4]).isEqualTo(DepartureTimeIcon.TRIP_ACTV)
        softly.assertThat(states[5]).isEqualTo(DepartureTimeIcon.UNKNOWN)
    }
}
