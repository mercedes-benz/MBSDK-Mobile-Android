package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiCommandTest {

    @Test
    fun `map ApiCommand to Command`(softly: SoftAssertions) {
        val apiCommand = ApiCommand(
            ApiCommandName.AUXHEAT_START,
            true,
            listOf(
                ApiCommandParameter(ApiCommandParameterName.DOORS, 0.0, 1.0, 2.0, null, null)
            ),
            emptyList()
        )
        val command = apiCommand.toCommand()

        softly.assertThat(command.name.name).isEqualTo(apiCommand.name?.name)
        softly.assertThat(command.available).isEqualTo(apiCommand.available)
        softly.assertThat(command.parameters[0].name.name).isEqualTo(apiCommand.parameters?.get(0)?.name?.name)
        softly.assertThat(command.additionalInformation).isEqualTo(apiCommand.additionalInformation)
    }
}
