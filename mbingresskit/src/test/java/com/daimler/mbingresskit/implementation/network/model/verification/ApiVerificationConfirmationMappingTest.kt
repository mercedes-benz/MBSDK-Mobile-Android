package com.daimler.mbingresskit.implementation.network.model.verification

import com.daimler.mbingresskit.common.VerificationConfirmation
import com.daimler.mbingresskit.common.VerificationType
import com.daimler.mbingresskit.implementation.network.service.RetrofitVerificationServiceTest
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

@ExtendWith(SoftAssertionsExtension::class)
class ApiVerificationConfirmationMappingTest {

    @ParameterizedTest
    @ArgumentsSource(RetrofitVerificationServiceTest.VerificationTransactionProvider::class)
    fun `map ApiVerificationConfirmation from VerificationConfirmation`(
        verificationType: VerificationType,
        verificationSubject: String,
        softAssertions: SoftAssertions
    ) {
        val verificationConfirmation = VerificationConfirmation(
            verificationType,
            verificationSubject,
            "12345"
        )
        val apiVerificationConfirmation =
            ApiVerificationConfirmation.fromVerificationConfirmation(verificationConfirmation)
        softAssertions.assertThat(apiVerificationConfirmation.type.name)
            .isEqualTo(verificationConfirmation.type.name)
        softAssertions.assertThat(apiVerificationConfirmation.subject)
            .isEqualTo(verificationConfirmation.subject)
        softAssertions.assertThat(apiVerificationConfirmation.code)
            .isEqualTo(verificationConfirmation.code)
    }
}
