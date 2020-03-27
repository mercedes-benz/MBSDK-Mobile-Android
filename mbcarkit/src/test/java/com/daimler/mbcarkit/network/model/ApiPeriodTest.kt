package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiPeriodTest {

    @Test
    fun `map ApiPeriod to Period`(softly: SoftAssertions) {
        val apiPeriod = ApiPeriod("from", "until")
        val period = apiPeriod.toPeriod()

        softly.assertThat(period.from).isEqualTo(apiPeriod.from)
        softly.assertThat(period.until).isEqualTo(apiPeriod.until)
    }
}
