package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiWindowsLiftCountTest {

    @Test
    fun `Mapping 'null' ApiWindowsLiftCount should default to 'null'`() {
        Assertions.assertNull(null.toWindowsLiftCount())
    }

    @ParameterizedTest
    @EnumSource(ApiWindowsLiftCount::class)
    fun `Mapping from ApiWindowsLiftCount to WindowsLiftCount enum`(apiWindowsLiftCount: ApiWindowsLiftCount) {
        Assertions.assertEquals(apiWindowsLiftCount.name, apiWindowsLiftCount.toWindowsLiftCount()?.name)
    }
}
