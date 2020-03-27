package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.consumption.VehicleConsumptionUnit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiVehicleConsumptionUnitTest {

    @Test
    fun `Mapping 'null' ApiVehicleConsumptionUnit should default to UNKNOWN`() {
        Assertions.assertEquals(VehicleConsumptionUnit.UNKNOWN, null.toVehicleConsumptionUnit())
    }

    @ParameterizedTest
    @EnumSource(ApiVehicleConsumptionUnit::class)
    fun `Mapping from ApiVehicleConsumptionUnit to VehicleConsumptionUnit enum`(apiVehicleConsumptionUnit: ApiVehicleConsumptionUnit) {
        Assertions.assertEquals(apiVehicleConsumptionUnit.name, apiVehicleConsumptionUnit.toVehicleConsumptionUnit().name)
    }
}
