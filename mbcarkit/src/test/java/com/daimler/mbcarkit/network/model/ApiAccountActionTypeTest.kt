package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountActionType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiAccountActionTypeTest {

    @ParameterizedTest
    @EnumSource(ApiAccountActionType::class)
    fun `verify AccountType mapping`(actionType: ApiAccountActionType) {
        val expected = when (actionType) {
            ApiAccountActionType.CONNECT -> AccountActionType.CONNECT
            ApiAccountActionType.DELETE -> AccountActionType.DELETE
            ApiAccountActionType.SET_DEFAULT -> AccountActionType.SET_DEFAULT
            ApiAccountActionType.CONNECT_WITH_VOUCHER -> AccountActionType.CONNECT_WITH_VOUCHER
        }
        Assertions.assertEquals(expected, actionType.toAccountAction())
    }
}
