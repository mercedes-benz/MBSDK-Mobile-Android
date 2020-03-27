package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiLocalProfileTest {

    @Test
    fun `map ApiLocalProfile to VehicleLocalProfile`(softly: SoftAssertions) {
        val apiLocalProfile = ApiLocalProfile(
            "id",
            "name"
        )
        val vehicleLocalProfile = apiLocalProfile.toVehicleLocalProfile()

        softly.assertThat(vehicleLocalProfile).isNotNull
        softly.assertThat(vehicleLocalProfile?.id).isEqualTo(apiLocalProfile.id)
        softly.assertThat(vehicleLocalProfile?.name).isEqualTo(apiLocalProfile.name)
    }
}
