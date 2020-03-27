package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.OutletActivity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiOutletActivityTest {

    @ParameterizedTest
    @EnumSource(OutletActivity::class)
    fun `Mapping from OutletActivity to ApiOutletActivity enum`(outletActivity: OutletActivity) {
        Assertions.assertEquals(outletActivity.name, ApiOutletActivity.fromOutletActivity(outletActivity)?.name)
    }
}
