package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class RealmMissingServiceDataTest {

    @Test
    fun `mapping from RealmMissingServiceData to MissingServiceData`(softly: SoftAssertions) {
        val realmMissingLinkage = RealmMissingAccountLinkage().apply {
            mandatory = true
            type = AccountType.CHARGING.ordinal
        }
        val realmModel = RealmMissingServiceData().apply {
            missingAccountLinkage = realmMissingLinkage
        }
        val model = realmModel.toMissingServiceData()
        softly.assertThat(model.missingAccountLinkage).isNotNull
        softly.assertThat(model.missingAccountLinkage?.mandatory).isTrue
        softly.assertThat(model.missingAccountLinkage?.type).isEqualTo(AccountType.CHARGING)
    }

    @Test
    fun `verify applyFrom()`(softly: SoftAssertions) {
        val realmLinkage = RealmMissingAccountLinkage().apply {
            mandatory = false
            type = AccountType.MUSIC.ordinal
        }
        val model = RealmMissingServiceData()
        model.applyFrom(realmLinkage)
        softly.assertThat(model.missingAccountLinkage).isEqualTo(realmLinkage)
    }
}
