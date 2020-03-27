package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiAccountTypeTest {

    @ParameterizedTest
    @ArgumentsSource(AccountTypeMappingArgumentsProvider::class)
    fun `verify AccountType mapping`(apiAccountType: ApiAccountType, accountType: AccountType, softly: SoftAssertions) {
        softly.assertThat(apiAccountType.toAccountType()).isEqualTo(accountType)
        softly.assertThat(accountType.toApiAccountType()).isEqualTo(apiAccountType)
    }

    private class AccountTypeMappingArgumentsProvider : ArgumentsProvider {

        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
            ApiAccountType.values()
                .map {
                    val pair = it to when (it) {
                        ApiAccountType.MUSIC -> AccountType.MUSIC
                        ApiAccountType.IN_CAR_OFFICE -> AccountType.IN_CAR_OFFICE
                        ApiAccountType.CHARGING -> AccountType.CHARGING
                        ApiAccountType.SMART_HOME -> AccountType.SMART_HOME
                    }
                    Arguments { arrayOf(pair.first, pair.second) }
                }.stream()
    }
}
