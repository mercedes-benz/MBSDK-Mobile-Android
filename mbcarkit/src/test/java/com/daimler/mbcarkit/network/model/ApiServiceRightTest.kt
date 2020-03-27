package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceRight
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiServiceRightTest {

    @Test
    fun `Mapping 'null' ApiServiceRight should default to UNKNOWN`() {
        Assertions.assertEquals(ServiceRight.UNKNOWN, null.toServiceRight())
    }

    @ParameterizedTest
    @EnumSource(ApiServiceRight::class)
    fun `Mapping from ApiServiceRight to ServiceRight enum`(apiServiceRight: ApiServiceRight) {
        Assertions.assertEquals(apiServiceRight.name, apiServiceRight.toServiceRight().name)
    }
}
