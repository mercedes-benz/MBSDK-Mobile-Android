package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiServiceStatusTest {

    @Test
    fun `Mapping 'null' ApiServiceStatus should default to UNKNOWN`() {
        Assertions.assertEquals(ServiceStatus.UNKNOWN, null.toServiceStatus())
    }

    @ParameterizedTest
    @EnumSource(ApiServiceStatus::class)
    fun `Mapping from ApiServiceStatus to ServiceStatus enum`(apiServiceStatus: ApiServiceStatus) {
        Assertions.assertEquals(apiServiceStatus.name, apiServiceStatus.toServiceStatus().name)
    }
}
