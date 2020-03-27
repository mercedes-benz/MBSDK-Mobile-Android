package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.DealerRole
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiDealerRoleTest {

    @Test
    fun `Mapping 'null' ApiDealerRole should default to UNKNOWN`() {
        Assertions.assertEquals(DealerRole.UNKNOWN, null.toDealerRole())
    }

    @ParameterizedTest
    @EnumSource(ApiDealerRole::class)
    fun `Mapping from ApiDealerRole to DealerRole enum`(apiDealerRole: ApiDealerRole) {
        Assertions.assertEquals(apiDealerRole.name, apiDealerRole.toDealerRole().name)
    }

    @ParameterizedTest
    @EnumSource(DealerRole::class)
    fun `Mapping from DealerRole to ApiDealerRole enum`(dealerRole: DealerRole) {
        if (dealerRole == DealerRole.UNKNOWN) {
            Assertions.assertNull(ApiDealerRole.fromDealerRole(dealerRole)?.name)
        } else {
            Assertions.assertEquals(dealerRole.name, ApiDealerRole.fromDealerRole(dealerRole)?.name)
        }
    }
}
