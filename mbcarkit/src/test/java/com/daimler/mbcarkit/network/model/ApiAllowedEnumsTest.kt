package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.AllowedEnums
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiAllowedEnumsTest {

    @Test
    fun `Mapping 'null' ApiAllowedEnums should default to UNKNOWN`() {
        Assertions.assertEquals(AllowedEnums.UNKNOWN, null.toAllowedEnums())
    }

    @ParameterizedTest
    @EnumSource(ApiAllowedEnums::class)
    fun `Mapping from ApiAllowedEnums to ApiAllowedEnums enum`(apiAllowedEnums: ApiAllowedEnums) {
        val expectedValue = apiAllowedEnums.allowedEnums?.name ?: apiAllowedEnums.name
        Assertions.assertEquals(expectedValue, apiAllowedEnums.toAllowedEnums().name)
    }
}
