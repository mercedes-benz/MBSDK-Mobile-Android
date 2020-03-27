package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceAction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiAllowedServiceActionsTest {

    @Test
    fun `Mapping 'null' ApiAllowedServiceActions should default to UNKNOWN`() {
        Assertions.assertEquals(ServiceAction.UNKNOWN, null.toServiceAction())
    }

    @ParameterizedTest
    @EnumSource(ApiAllowedServiceActions::class)
    fun `Mapping from ApiAllowedServiceActions to ServiceAction enum`(apiAllowedServiceActions: ApiAllowedServiceActions) {
        Assertions.assertEquals(apiAllowedServiceActions.name, apiAllowedServiceActions.toServiceAction().name)
    }
}
