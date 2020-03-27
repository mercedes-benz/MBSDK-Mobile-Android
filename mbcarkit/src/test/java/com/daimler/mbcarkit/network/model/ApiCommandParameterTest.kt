package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiCommandParameterTest {

    @Test
    fun `map ApiCommandParameter to CommandParameter`(softly: SoftAssertions) {
        val apiCommandParameter = ApiCommandParameter(
            ApiCommandParameterName.DOORS,
            0.0,
            0.0,
            0.0,
            listOf(ApiAllowedEnums.DEFAULT),
            ApiAllowedBools.ONLY_TRUE
        )
        val commandParameter = apiCommandParameter.toCommandParameter()

        softly.assertThat(commandParameter.name.name).isEqualTo(apiCommandParameter.name?.name)
        softly.assertThat(commandParameter.minValue).isEqualTo(apiCommandParameter.minValue)
        softly.assertThat(commandParameter.maxValue).isEqualTo(apiCommandParameter.maxValue)
        softly.assertThat(commandParameter.steps).isEqualTo(apiCommandParameter.steps)
        softly.assertThat(commandParameter.allowedEnums[0].name).isEqualTo(apiCommandParameter.allowedEnums?.get(0)?.name)
        softly.assertThat(commandParameter.allowedBools.name).isEqualTo(apiCommandParameter.allowedBools?.name)
    }
}
