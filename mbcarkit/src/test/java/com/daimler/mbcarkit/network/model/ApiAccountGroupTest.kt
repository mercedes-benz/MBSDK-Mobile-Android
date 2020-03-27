package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.utils.ApiAccountLinkageFactory
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiAccountGroupTest {

    @Test
    fun `verify mapping from ApiAccountGroup to AccountLinkageGroup`(softly: SoftAssertions) {
        val apiLinkage = ApiAccountLinkageFactory.createApiAccountLinkage(userAccountId = "accountId")
        val apiGroup = ApiAccountLinkageFactory.createApiAccountLinkageGroup(
            accountType = ApiAccountType.IN_CAR_OFFICE,
            iconUrl = "iconUrl",
            name = "name",
            bannerImageUrl = "bannerImageUrl",
            bannerTitle = "title",
            description = "description",
            visible = true,
            accounts = listOf(apiLinkage)
        )
        val group = apiGroup.toAccountLinkageGroup()
        softly.assertThat(group.accountType).isEqualTo(AccountType.IN_CAR_OFFICE)
        softly.assertThat(group.iconUrl).isEqualTo(apiGroup.iconUrl)
        softly.assertThat(group.name).isEqualTo(apiGroup.name)
        softly.assertThat(group.bannerImageUrl).isEqualTo(apiGroup.bannerImageUrl)
        softly.assertThat(group.bannerTitle).isEqualTo(apiGroup.bannerTitle)
        softly.assertThat(group.description).isEqualTo(apiGroup.description)
        softly.assertThat(group.visible).isEqualTo(apiGroup.visible)
        softly.assertThat(group.accounts.size).isEqualTo(1)
        softly.assertThat(group.accounts.firstOrNull()?.userAccountId).isEqualTo(apiLinkage.userAccountId)
    }
}
