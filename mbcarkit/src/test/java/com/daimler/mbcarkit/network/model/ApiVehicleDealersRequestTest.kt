package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.Merchant
import com.daimler.mbcarkit.business.model.vehicle.DealerRole
import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiVehicleDealersRequestTest {

    @Test
    fun `map ApiVehicleDealersRequest from VehicleDealer`(softly: SoftAssertions) {
        val vehicleDealers = listOf(
            VehicleDealer(
                "dealerId",
                DealerRole.SERVICE,
                null,
                Merchant(
                    "id",
                    "legalName",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            )
        )
        val apiVehicleDealersRequest = ApiVehicleDealersRequest.fromVehicleDealers(vehicleDealers)

        softly.assertThat(apiVehicleDealersRequest.items[0].dealerId).isEqualTo(vehicleDealers[0].dealerId)
        softly.assertThat(apiVehicleDealersRequest.items[0].role.name).isEqualTo(vehicleDealers[0].role.name)
    }
}
