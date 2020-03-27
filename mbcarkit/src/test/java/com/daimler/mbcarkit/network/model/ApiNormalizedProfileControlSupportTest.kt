package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiNormalizedProfileControlSupportTest {

    @Test
    fun `Mapping 'null' ApiNormalizedProfileControlSupport should default to 'null'`() {
        Assertions.assertNull(null.toNormalizedProfileControlSupport())
    }

    @ParameterizedTest
    @EnumSource(ApiNormalizedProfileControlSupport::class)
    fun `Mapping from ApiNormalizedProfileControlSupport to NormalizedProfileControlSupport enum`(apiNormalizedProfileControlSupport: ApiNormalizedProfileControlSupport) {
        Assertions.assertEquals(apiNormalizedProfileControlSupport.name, apiNormalizedProfileControlSupport.toNormalizedProfileControlSupport()?.name)
    }
}
