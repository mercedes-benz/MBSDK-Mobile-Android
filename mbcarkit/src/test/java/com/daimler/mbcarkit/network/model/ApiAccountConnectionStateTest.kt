package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountConnectionState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiAccountConnectionStateTest {

    @ParameterizedTest
    @EnumSource(ApiAccountConnectionState::class)
    fun `mapping to AccountConnectionState`(input: ApiAccountConnectionState) {
        val expected = when (input) {
            ApiAccountConnectionState.CONNECTED -> AccountConnectionState.CONNECTED
            ApiAccountConnectionState.DISCONNECTED -> AccountConnectionState.DISCONNECTED
        }
        Assertions.assertEquals(expected, input.toAccountConnectionState())
    }
}
