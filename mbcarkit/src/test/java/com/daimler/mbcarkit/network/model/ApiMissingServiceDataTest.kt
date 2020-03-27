package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiMissingServiceDataTest {

    @Test
    fun `mapping from ApiMissingServiceData toMissingServiceData`(softly: SoftAssertions) {
        val apiLinkage = ApiMissingAccountLinkage(true, ApiAccountType.CHARGING)
        val apiModel = ApiMissingServiceData(apiLinkage)
        val model = apiModel.toMissingServiceData()
        softly.assertThat(model.missingAccountLinkage).isNotNull
        softly.assertThat(model.missingAccountLinkage?.mandatory).isEqualTo(apiLinkage.mandatory)
        softly.assertThat(model.missingAccountLinkage?.type).isEqualTo(AccountType.CHARGING)
    }
}
