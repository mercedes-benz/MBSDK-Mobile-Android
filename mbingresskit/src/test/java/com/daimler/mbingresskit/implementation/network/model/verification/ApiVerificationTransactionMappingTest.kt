package com.daimler.mbingresskit.implementation.network.model.verification

import com.daimler.mbingresskit.common.VerificationTransaction
import com.daimler.mbingresskit.common.VerificationType
import com.daimler.mbingresskit.implementation.network.service.RetrofitVerificationServiceTest
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

@ExtendWith(SoftAssertionsExtension::class)
class ApiVerificationTransactionMappingTest {

    @ParameterizedTest
    @ArgumentsSource(RetrofitVerificationServiceTest.VerificationTransactionProvider::class)
    fun `map ApiVerificationTransaction from VerificationTransaction`(
        verificationType: VerificationType,
        verificationSubject: String,
        softAssertions: SoftAssertions
    ) {
        val verificationTransaction = VerificationTransaction(
            verificationType,
            verificationSubject
        )
        val apiVerificationTransaction =
            ApiVerificationTransaction.fromVerificationTransaction(verificationTransaction)
        softAssertions.assertThat(apiVerificationTransaction.type.name)
            .isEqualTo(verificationTransaction.type.name)
        softAssertions.assertThat(apiVerificationTransaction.subject)
            .isEqualTo(verificationTransaction.subject)
    }
}
