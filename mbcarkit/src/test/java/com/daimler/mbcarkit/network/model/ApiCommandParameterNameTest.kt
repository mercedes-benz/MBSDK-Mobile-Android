package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.CommandParameterName
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiCommandParameterNameTest {

    @Test
    fun `Mapping 'null' ApiCommandParameterName should default to UNKNOWN`() {
        Assertions.assertEquals(CommandParameterName.UNKNOWN, null.toCommandParameterName())
    }

    @ParameterizedTest
    @EnumSource(ApiCommandParameterName::class)
    fun `Mapping from ApiCommandParameterName to CommandParameterName enum`(apiCommandParameterName: ApiCommandParameterName) {
        Assertions.assertEquals(apiCommandParameterName.name, apiCommandParameterName.toCommandParameterName().name)
    }
}
