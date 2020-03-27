package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiDayTest {

    @Test
    fun `map ApiDay to Day`(softly: SoftAssertions) {
        val apiDay = ApiDay(
            "status",
            listOf(
                ApiPeriod("from_one", "until_one"),
                ApiPeriod("from_two", "until_two")
            )
        )
        val day = apiDay.toDay()

        softly.assertThat(day?.status).isEqualTo(apiDay.status)
        softly.assertThat(day?.periods?.size).isEqualTo(apiDay.periods?.size)
        softly.assertThat(day?.periods?.get(0)?.from).isEqualTo(apiDay.periods?.get(0)?.from)
        softly.assertThat(day?.periods?.get(0)?.until).isEqualTo(apiDay.periods?.get(0)?.until)
        softly.assertThat(day?.periods?.get(1)?.from).isEqualTo(apiDay.periods?.get(1)?.from)
        softly.assertThat(day?.periods?.get(1)?.until).isEqualTo(apiDay.periods?.get(1)?.until)
    }
}
