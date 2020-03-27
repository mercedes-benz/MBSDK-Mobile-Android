package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUser
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUserStatus
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleLocalProfile
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiVehicleUsersManagementTest {

    @Test
    fun `verify mapping from ApiVehicleUsersManagement to VehicleUserManagement`(softly: SoftAssertions) {
        val finOrVin = "finOrVin"
        val apiUserManagement = createApiUserManagement()

        val userManagement = apiUserManagement.toVehicleUserManagement(finOrVin)

        softly.assertThat(userManagement.finOrVin).isEqualTo(finOrVin)
        softly.assertThat(userManagement.maxProfiles).isEqualTo(apiUserManagement.generalData?.maxNumberOfProfiles)
        softly.assertThat(userManagement.occupiedProfiles).isEqualTo(apiUserManagement.generalData?.numberOfOccupiedProfiles)
        assertVehicleAssignedUser(apiUserManagement.owner, userManagement.owner, VehicleAssignedUserStatus.OWNER, softly)
        val apiPendingUser = apiUserManagement.subUsers?.pendingSubusers?.firstOrNull()
        assertUsers(apiPendingUser, userManagement, VehicleAssignedUserStatus.PENDING_USER, softly)
        val apiValidUser = apiUserManagement.subUsers?.validSubusers?.firstOrNull()
        assertUsers(apiValidUser, userManagement, VehicleAssignedUserStatus.VALID_USER, softly)
        val apiTemporaryUser = apiUserManagement.subUsers?.temporarySubusers?.firstOrNull()
        assertUsers(apiTemporaryUser, userManagement, VehicleAssignedUserStatus.TEMPORARY_USER, softly)
        val apiLocalProfile = apiUserManagement.localProfiles?.firstOrNull()
        assertLocalProfile(apiLocalProfile, userManagement.profiles?.firstOrNull(), softly)
    }

    private fun createApiUserManagement(): ApiVehicleUsersManagement {
        val apiGeneral = ApiVehicleUserGeneralData(
            10,
            5,
            "ON"
        )
        val owner = ApiVehicleAssignedUser(
            "id",
            null,
            "display",
            "mail",
            "mobil",
            "url",
            null
        )
        val pendingUser = ApiVehicleAssignedUser(
            "id",
            null,
            "display",
            null,
            null,
            null,
            Date()
        )
        val validUser = ApiVehicleAssignedUser(
            "id",
            null,
            "display",
            null,
            null,
            null,
            null
        )
        val temporaryUser = ApiVehicleAssignedUser(
            "id",
            null,
            "display",
            null,
            null,
            null,
            Date()
        )
        val subUsers = ApiVehicleAssignedSubusers(listOf(pendingUser), listOf(validUser), listOf(temporaryUser))
        val localProfile = ApiLocalProfile("id", "name")

        return ApiVehicleUsersManagement(apiGeneral, owner, subUsers, listOf(localProfile))
    }

    private fun assertVehicleAssignedUser(
        apiUser: ApiVehicleAssignedUser?,
        user: VehicleAssignedUser?,
        type: VehicleAssignedUserStatus,
        softly: SoftAssertions
    ) {
        softly.assertThat(user?.authorizationId).isEqualTo(apiUser?.authorizationId)
        softly.assertThat(user?.displayName).isEqualTo(apiUser?.displayName)
        softly.assertThat(user?.email).isEqualTo(apiUser?.email)
        softly.assertThat(user?.mobileNumber).isEqualTo(apiUser?.mobileNumber)
        softly.assertThat(user?.profilePictureLink).isEqualTo(apiUser?.profilePictureLink)
        softly.assertThat(user?.status).isEqualTo(type)
        softly.assertThat(user?.validUntil).isEqualTo(apiUser?.validUntil)
    }

    private fun assertUsers(
        apiUser: ApiVehicleAssignedUser?,
        userManagement: VehicleUserManagement,
        status: VehicleAssignedUserStatus,
        softly: SoftAssertions
    ) {
        val users = userManagement.users?.filter { it.status == status }
        softly.assertThat(users?.size).isEqualTo(1)
        assertVehicleAssignedUser(apiUser, users?.firstOrNull(), status, softly)
    }

    private fun assertLocalProfile(
        apiLocalProfile: ApiLocalProfile?,
        localProfile: VehicleLocalProfile?,
        softly: SoftAssertions
    ) {
        softly.assertThat(localProfile?.id).isEqualTo(apiLocalProfile?.id)
        softly.assertThat(localProfile?.name).isEqualTo(apiLocalProfile?.name)
    }
}
