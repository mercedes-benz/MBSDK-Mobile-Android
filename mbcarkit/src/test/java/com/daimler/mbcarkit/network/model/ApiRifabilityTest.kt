package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@ExtendWith(SoftAssertionsExtension::class)
class ApiRifabilityTest {

    @Test
    fun `map ApiRifability to Rifability with canReceiveVac enabled`(softly: SoftAssertions) {
        val apiRifability = ApiRifability(
            null,
            null,
            null,
            true,
            ApiVehicleConnectivity.ADAPTER
        )
        val rifability = apiRifability.toRifability()

        softly.assertThat(rifability.isRifable).isEqualTo(true)
    }

    @Test
    fun `map ApiRifability to Rifability with rifSupportedForVac enabled`(softly: SoftAssertions) {
        val apiRifability = ApiRifability(
            null,
            true,
            null,
            null,
            ApiVehicleConnectivity.ADAPTER
        )
        val rifability = apiRifability.toRifability()

        softly.assertThat(rifability.isRifable).isEqualTo(true)
    }

    @Test
    fun `map ApiRifability to Rifability with rifable enabled`(softly: SoftAssertions) {
        val apiRifability = ApiRifability(
            true,
            null,
            null,
            null,
            ApiVehicleConnectivity.ADAPTER
        )
        val rifability = apiRifability.toRifability()

        softly.assertThat(rifability.isRifable).isEqualTo(true)
    }

    @Test
    fun `map ApiRifability to Rifability with nothing enabled`(softly: SoftAssertions) {
        val apiRifability = ApiRifability(
            null,
            null,
            null,
            null,
            ApiVehicleConnectivity.ADAPTER
        )
        val rifability = apiRifability.toRifability()

        softly.assertThat(rifability.isRifable).isEqualTo(false)
    }

    @ParameterizedTest
    @EnumSource(ApiVehicleConnectivity::class)
    internal fun `map ApiRifability to Rifability should set isConnectVehicle correctly`(vehicleConnectivity: ApiVehicleConnectivity, softly: SoftAssertions) {
        val expectedIsConnectVehicle = vehicleConnectivity == ApiVehicleConnectivity.BUILT_IN

        val apiRifability = ApiRifability(
            null,
            null,
            null,
            null,
            vehicleConnectivity
        )
        val rifability = apiRifability.toRifability()

        softly.assertThat(rifability.isConnectVehicle).isEqualTo(expectedIsConnectVehicle)
    }
}
