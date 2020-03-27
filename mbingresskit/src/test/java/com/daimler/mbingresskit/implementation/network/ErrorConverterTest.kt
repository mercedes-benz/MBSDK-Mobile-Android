package com.daimler.mbingresskit.implementation.network

import com.daimler.mbingresskit.implementation.network.model.ApiInputError
import com.daimler.mbingresskit.implementation.network.model.ApiInputErrors
import com.daimler.mbingresskit.implementation.network.model.ConsentApiInputErrors
import com.daimler.mbingresskit.implementation.network.model.toRegistrationErrors
import com.daimler.mbingresskit.implementation.network.model.toUserInputError
import com.daimler.mbingresskit.implementation.network.model.toUserInputErrors
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ErrorConverterTest {

    @Test
    fun `test mapping from ApiInputErrors to UserInputErrors`(softly: SoftAssertions) {
        val apiError = ApiInputError("name", "description")
        val error = apiError.toUserInputError()
        softly.assertThat(error.fieldName).isEqualTo(apiError.fieldName)
        softly.assertThat(error.description).isEqualTo(apiError.description)
        val apiErrors = ApiInputErrors(listOf(apiError))
        val errors = apiErrors.toUserInputErrors()
        softly.assertThat(errors.errors.size).isEqualTo(1)
        softly.assertThat(errors.errors.first()).isEqualTo(error)
    }

    @Test
    fun `test mapping from ConsentApiErrors to RegistrationErrors`(softly: SoftAssertions) {
        val apiErrors = ConsentApiInputErrors(null, true)
        val errors = apiErrors.toRegistrationErrors()
        softly.assertThat(errors.consentNotGiven).isEqualTo(apiErrors.consentNotGiven)
    }
}
