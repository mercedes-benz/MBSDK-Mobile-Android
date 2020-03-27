package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountActionType
import com.daimler.mbcarkit.utils.ApiAccountLinkageFactory
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiAccountActionTest {

    @Test
    fun `verify mapping from ApiAccountAction to AccountAction`(softly: SoftAssertions) {
        val apiAccountAction = ApiAccountLinkageFactory.createApiAccountAction(
            action = ApiAccountActionType.CONNECT
        )
        val accountAction = apiAccountAction.toAccountAction()
        softly.assertThat(accountAction.label).isEqualTo(apiAccountAction.label)
        softly.assertThat(accountAction.actionType).isEqualTo(AccountActionType.CONNECT)
        softly.assertThat(accountAction.url).isEqualTo(apiAccountAction.url)
    }
}
