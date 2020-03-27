package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiCommandCapabilitiesTest {

    @Test
    fun `map ApiCommandCapabilities to CommandCapabilities`(softly: SoftAssertions) {
        val apiCommand = ApiCommand(
            ApiCommandName.AUXHEAT_START,
            true,
            listOf(
                ApiCommandParameter(ApiCommandParameterName.DOORS, 0.0, 1.0, 2.0, null, null)
            ),
            emptyList()
        )
        val apiCommandCapabilities = ApiCommandCapabilities(listOf(apiCommand))
        val commandCapabilities = apiCommandCapabilities.toCommandCapabilities()

        softly.assertThat(commandCapabilities.commands[0].name.name).isEqualTo(apiCommand.name?.name)
        softly.assertThat(commandCapabilities.commands[0].available).isEqualTo(apiCommand.available)
        softly.assertThat(commandCapabilities.commands[0].parameters[0].name.name).isEqualTo(apiCommand.parameters?.get(0)?.name?.name)
        softly.assertThat(commandCapabilities.commands[0].additionalInformation).isEqualTo(apiCommand.additionalInformation)
    }
}
