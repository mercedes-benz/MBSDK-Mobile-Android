package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.business.model.services.MissingAccountLinkage
import com.daimler.testutils.arguments.EnumWithNullArgumentsProvider
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

@ExtendWith(SoftAssertionsExtension::class)
internal class RealmMissingAccountLinkageTest {

    @Test
    fun `mapping from RealmMissingAccountLinkage to MissingAccountLinkage`(softly: SoftAssertions) {
        val realmModel = RealmMissingAccountLinkage().apply {
            mandatory = true
            type = AccountType.CHARGING.ordinal
        }
        val model = realmModel.toMissingAccountLinkage()
        softly.assertThat(model.mandatory).isTrue
        softly.assertThat(model.type).isEqualTo(AccountType.CHARGING)
    }

    @Test
    fun `verify applyFromMissingAccountLinkage()`(softly: SoftAssertions) {
        val linkage = MissingAccountLinkage(true, AccountType.CHARGING)
        val model = RealmMissingAccountLinkage()
        model.applyFromMissingAccountLinkage(linkage)
        softly.assertThat(model.mandatory).isTrue
        softly.assertThat(model.type).isEqualTo(AccountType.CHARGING.ordinal)
    }

    @ParameterizedTest
    @ArgumentsSource(AccountTypeWithNullArgumentsProvider::class)
    fun `verify is same account type`(type: AccountType?, softly: SoftAssertions) {
        val model = RealmMissingAccountLinkage().applyFromMissingAccountLinkage(
            MissingAccountLinkage(false, type)
        )
        AccountType.values().forEach {
            softly.assertThat(model.isSameAccountType(it)).isEqualTo(type == it)
        }
    }

    private class AccountTypeWithNullArgumentsProvider : EnumWithNullArgumentsProvider<AccountType>(AccountType::class)
}
