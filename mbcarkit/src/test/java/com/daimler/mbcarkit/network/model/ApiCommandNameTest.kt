package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.CommandName
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiCommandNameTest {

    @Test
    fun `Mapping 'null' ApiCommandName should default to UNKNOWN`() {
        Assertions.assertEquals(CommandName.UNKNOWN, null.toCommandName())
    }

    @ParameterizedTest
    @EnumSource(ApiCommandName::class)
    fun `Mapping from ApiCommandName to CommandName enum`(apiCommandName: ApiCommandName) {
        Assertions.assertEquals(apiCommandName.name, apiCommandName.toCommandName().name)
    }
}
