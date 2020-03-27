package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUserStatus
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiVehicleAssignedUserTest {

    @Test
    fun `map ApiVehicleAssignedUser to VehicleAssignedUser`(softly: SoftAssertions) {
        val apiVehicleAssignedUser = ApiVehicleAssignedUser(
            "authorizationId",
            "ciamId",
            "displayName",
            "email",
            "mobileNumber",
            "profilePictureLink",
            null
        )
        val vehicleAssignedUser = apiVehicleAssignedUser.toVehicleAssignedUser(VehicleAssignedUserStatus.TEMPORARY_USER)

        softly.assertThat(vehicleAssignedUser.authorizationId).isEqualTo(apiVehicleAssignedUser.authorizationId)
        softly.assertThat(vehicleAssignedUser.displayName).isEqualTo(apiVehicleAssignedUser.displayName)
        softly.assertThat(vehicleAssignedUser.email).isEqualTo(apiVehicleAssignedUser.email)
        softly.assertThat(vehicleAssignedUser.mobileNumber).isEqualTo(apiVehicleAssignedUser.mobileNumber)
        softly.assertThat(vehicleAssignedUser.profilePictureLink).isEqualTo(apiVehicleAssignedUser.profilePictureLink)
        softly.assertThat(vehicleAssignedUser.validUntil).isEqualTo(apiVehicleAssignedUser.validUntil)
    }
}
