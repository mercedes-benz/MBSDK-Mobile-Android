package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceMissingField
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiServiceMissingFieldsTest {

    @Test
    fun `Mapping 'null' ApiServiceMissingFields should default to UNKNOWN`() {
        Assertions.assertEquals(ServiceMissingField.UNKNOWN, null.toServiceMissingField())
    }

    @ParameterizedTest
    @EnumSource(ApiServiceMissingFields::class)
    fun `Mapping from ApiServiceMissingFields to ServiceMissingField enum`(apiServiceMissingFields: ApiServiceMissingFields) {
        Assertions.assertEquals(apiServiceMissingFields.name, apiServiceMissingFields.toServiceMissingField().name)
    }
}
