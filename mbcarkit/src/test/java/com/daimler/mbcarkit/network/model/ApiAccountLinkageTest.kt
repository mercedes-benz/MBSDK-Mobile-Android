package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountConnectionState
import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.utils.ApiAccountLinkageFactory
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiAccountLinkageTest {

    @Test
    fun `mapping to AccountLinkage`(softly: SoftAssertions) {
        val apiAccountLinkage = ApiAccountLinkageFactory.createApiAccountLinkage(
            connectionState = ApiAccountConnectionState.DISCONNECTED,
            accountType = ApiAccountType.IN_CAR_OFFICE,
            userAccountId = "account",
            vendorId = "vendorId",
            description = "description",
            descriptionLinks = mapOf("key1" to "value1", "key2" to "value2"),
            isDefault = true,
            iconUrl = "iconUrl",
            bannerUrl = "bannerUrl",
            vendorDisplayName = "vendorName",
            possibleActions = listOf(ApiAccountLinkageFactory.createApiAccountAction()),
            legalTextUrl = "legalTextUrl"
        )
        val accountLinkage = apiAccountLinkage.toAccountLinkage()

        softly.assertThat(accountLinkage.connectionState).isEqualTo(AccountConnectionState.DISCONNECTED)
        softly.assertThat(accountLinkage.userAccountId).isEqualTo(apiAccountLinkage.userAccountId)
        softly.assertThat(accountLinkage.vendorId).isEqualTo(apiAccountLinkage.vendorId)
        softly.assertThat(accountLinkage.description).isEqualTo(apiAccountLinkage.description)
        softly.assertThat(accountLinkage.isDefault).isEqualTo(apiAccountLinkage.isDefault)
        softly.assertThat(accountLinkage.iconUrl).isEqualTo(apiAccountLinkage.iconUrl)
        softly.assertThat(accountLinkage.bannerUrl).isEqualTo(apiAccountLinkage.bannerUrl)
        softly.assertThat(accountLinkage.vendorDisplayName).isEqualTo(apiAccountLinkage.vendorDisplayName)
        softly.assertThat(accountLinkage.accountType).isEqualTo(AccountType.IN_CAR_OFFICE)
        softly.assertThat(accountLinkage.actions.size).isEqualTo(apiAccountLinkage.possibleActions?.size)
        softly.assertThat(accountLinkage.legalTextUrl).isEqualTo(apiAccountLinkage.legalTextUrl)
    }
}
