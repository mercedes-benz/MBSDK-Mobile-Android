package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.AllowedBools
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiAllowedBoolsTest {

    @Test
    fun `Mapping 'null' ApiAllowedBools should default to UNKNOWN`() {
        Assertions.assertEquals(AllowedBools.UNKNOWN, null.toAllowedBools())
    }

    @ParameterizedTest
    @EnumSource(ApiAllowedBools::class)
    fun `Mapping from ApiAllowedBools to AllowedBools enum`(apiAllowedBools: ApiAllowedBools) {
        Assertions.assertEquals(apiAllowedBools.name, apiAllowedBools.toAllowedBools().name)
    }
}
