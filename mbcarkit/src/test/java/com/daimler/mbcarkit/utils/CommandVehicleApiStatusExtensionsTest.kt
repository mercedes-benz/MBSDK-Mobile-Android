package com.daimler.mbcarkit.utils

import com.daimler.mbcarkit.business.model.command.GenericCommandError
import com.daimler.mbcarkit.business.model.command.InternalVehicleCommandError
import com.daimler.mbcarkit.business.model.command.VehicleCommand
import com.daimler.mbcarkit.util.containsPinInvalidError
import com.daimler.mbcarkit.util.containsUserBlockedError
import com.daimler.mbcarkit.util.findGenericError
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(SoftAssertionsExtension::class)
internal class CommandVehicleApiStatusExtensionsTest {

    @Test
    fun `findGenericError() should return correct error if available`(softly: SoftAssertions) {
        val commandStatus = createCommandStatus(
            errors = listOf(createPinInvalidError())
        )

        val command = VehicleCommand.DoorsUnlock("")
        val error =
            commandStatus.findGenericError<GenericCommandError.PinInvalid>(command)
        softly.assertThat(error).isNotNull
        softly.assertThat(error).isInstanceOf(GenericCommandError.PinInvalid::class.java)
    }

    @Test
    fun `findGenericError() should return null if error not available`(softly: SoftAssertions) {
        val commandStatus = createCommandStatus(
            errors = listOf(createUserBlockedError())
        )

        val command = VehicleCommand.DoorsUnlock("")
        val error =
            commandStatus.findGenericError<GenericCommandError.PinInvalid>(command)
        softly.assertThat(error).isNull()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `containsPinInvalidError() should return correct result`(containsError: Boolean, softly: SoftAssertions) {
        val pinInvalidError = createPinInvalidError()

        val commandStatus = createCommandStatus(
            if (containsError) listOf(pinInvalidError) else emptyList()
        )

        softly.assertThat(commandStatus.containsPinInvalidError()).isEqualTo(containsError)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `containsUserBlockedError() should return correct result`(containsError: Boolean, softly: SoftAssertions) {
        val userBlockedError = createUserBlockedError()

        val commandStatus = createCommandStatus(
            if (containsError) listOf(userBlockedError) else emptyList()
        )

        softly.assertThat(commandStatus.containsUserBlockedError()).isEqualTo(containsError)
    }

    private fun createPinInvalidError() =
        createCommandVehicleApiError(InternalVehicleCommandError.PinInvalid.errorCodes.first())

    private fun createUserBlockedError() =
        createCommandVehicleApiError(InternalVehicleCommandError.UserBlocked.errorCodes.first())
}
