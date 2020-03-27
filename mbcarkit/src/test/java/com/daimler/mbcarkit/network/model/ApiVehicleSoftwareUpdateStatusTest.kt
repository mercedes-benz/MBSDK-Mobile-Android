package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiVehicleSoftwareUpdateStatusTest {

    @ParameterizedTest
    @EnumSource(ApiVehicleSoftwareUpdateStatus::class)
    fun `Mapping from ApiVehicleSoftwareUpdateStatus to VehicleSoftwareUpdateStatus enum`(apiStatus: ApiVehicleSoftwareUpdateStatus) {
        Assertions.assertEquals(apiStatus.name, apiStatus.toVehicleSoftwareUpdateStatus()!!.name)
    }
}
