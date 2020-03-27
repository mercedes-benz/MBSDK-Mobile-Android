package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiMissingAccountLinkageTest {

    @Test
    fun `mapping from ApiMissingAccountLinkage to MissingAccountLinkage`(softly: SoftAssertions) {
        val apiModel = ApiMissingAccountLinkage(true, ApiAccountType.CHARGING)
        val model = apiModel.toMissingAccountLinkage()
        softly.assertThat(model.mandatory).isEqualTo(apiModel.mandatory)
        softly.assertThat(model.type).isEqualTo(AccountType.CHARGING)
    }
}
