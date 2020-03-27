package com.daimler.mbingresskit.implementation.network.service

import com.daimler.mbingresskit.common.VerificationConfirmation
import com.daimler.mbingresskit.common.VerificationTransaction
import com.daimler.mbingresskit.common.VerificationType
import com.daimler.mbingresskit.implementation.network.api.VerificationApi
import com.daimler.mbingresskit.testutils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.header.HeaderService
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class)
internal class RetrofitVerificationServiceTest :
    BaseRetrofitServiceTest<VerificationApi, RetrofitVerificationService>() {

    override val api: VerificationApi = mockk()
    override val headerService: HeaderService = mockk()

    @BeforeEach
    fun setup() {
        coEvery { api.sendTransaction(any(), any()) } returns noContentResponse
        coEvery { api.sendConfirmation(any(), any()) } returns noContentResponse
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    override fun createSubject(scope: CoroutineScope): RetrofitVerificationService =
        RetrofitVerificationService(api, scope)

    @ParameterizedTest
    @ArgumentsSource(VerificationTransactionProvider::class)
    fun `sendTransaction should complete`(
        verificationType: VerificationType,
        verificationSubject: String,
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val verificationTransaction = VerificationTransaction(
            verificationType,
            verificationSubject
        )
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.sendTransaction("", verificationTransaction)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @ParameterizedTest
    @ArgumentsSource(VerificationTransactionProvider::class)
    fun `sendConfirmation should complete`(
        verificationType: VerificationType,
        verificationSubject: String,
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val verificationConfirmation = VerificationConfirmation(
            verificationType,
            verificationSubject,
            "12345"
        )
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.sendConfirmation("", verificationConfirmation)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    internal class VerificationTransactionProvider : ArgumentsProvider {

        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
            listOf(
                VerificationType.MOBILE_PHONE_NUMBER to "+49 123 456789",
                VerificationType.EMAIL to "dummy@mydomain.com"
            ).map {
                Arguments { arrayOf(it.first, it.second) }
            }.stream()
    }
}
