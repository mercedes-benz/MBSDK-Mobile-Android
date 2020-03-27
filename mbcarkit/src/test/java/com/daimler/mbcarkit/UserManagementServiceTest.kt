package com.daimler.mbcarkit

import com.daimler.mbcarkit.business.UserManagementService
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUser
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUserStatus
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement
import com.daimler.mbcarkit.implementation.CachedUserManagementService
import com.daimler.mbcarkit.socket.UserManagementCache
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.spyk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class UserManagementServiceTest {

    lateinit var userManagementService: CachedUserManagementService

    @MockK
    lateinit var retrofitServiceMock: UserManagementService
    @MockK
    lateinit var userManagementCache: UserManagementCache

    private val positiveCheckerFetchUserManagement: (a: VehicleUserManagement) -> Unit = spyk({ _ -> })
    private val negativeCheckerFetchUserManagement: (Unit) -> Unit = spyk({ _ -> })

    private val completeCaptured = slot<VehicleUserManagement>()
    private val failCaptured = slot<Unit>()
    private val vehicleUsersTask = spyk(TaskObject<VehicleUserManagement, ResponseError<out RequestError>?>())

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        userManagementService = CachedUserManagementService(
            userManagementCache,
            retrofitServiceMock
        )

        every { positiveCheckerFetchUserManagement.invoke(capture(completeCaptured)) } returns Unit
        every { negativeCheckerFetchUserManagement.invoke(capture(failCaptured)) } returns Unit

        every { retrofitServiceMock.fetchVehicleUsers(any(), any()) } returns vehicleUsersTask.futureTask()
        every { userManagementCache.loadUserManagement(VIN_CACHED) } returns VehicleUserManagement(
            VIN_CACHED,
            0,
            0,
            VehicleAssignedUser("", "", "", "", "", VehicleAssignedUserStatus.OWNER, null),
            null,
            listOf()
        )
        every { userManagementCache.loadUserManagement(VIN_NOT_CACHED) } returns null
    }

    @Test
    fun testFetchVehicleUsersRest() {
        userManagementService.fetchVehicleUsers("", "")
            .onComplete {
                positiveCheckerFetchUserManagement.invoke(it)
            }
            .onFailure {
                negativeCheckerFetchUserManagement.invoke(Unit)
            }

        vehicleUsersTask.complete(
            VehicleUserManagement(
                VIN_REST,
                0,
                0,
                VehicleAssignedUser(
                    "",
                    "",
                    "",
                    "",
                    "",
                    VehicleAssignedUserStatus.OWNER,
                    null
                ),
                null,
                listOf()
            )
        )

        Assert.assertTrue(completeCaptured.isCaptured)
        Assert.assertTrue(!failCaptured.isCaptured)
        Assert.assertTrue(completeCaptured.captured.finOrVin == VIN_REST)
    }

    @Test
    fun testFetchVehicleUsersCached() {
        userManagementService.fetchVehicleUsers("", VIN_CACHED)
            .onComplete {
                positiveCheckerFetchUserManagement.invoke(it)
            }
            .onFailure {
                negativeCheckerFetchUserManagement.invoke(Unit)
            }

        vehicleUsersTask.fail(ResponseError.networkError(NetworkError.NO_CONNECTION))

        Assert.assertTrue(completeCaptured.isCaptured)
        Assert.assertTrue(!failCaptured.isCaptured)
        Assert.assertTrue(completeCaptured.captured.finOrVin == VIN_CACHED)
    }

    @Test
    fun testFetchVehicleUsersFail() {
        userManagementService.fetchVehicleUsers("", VIN_NOT_CACHED)
            .onComplete {
                positiveCheckerFetchUserManagement.invoke(it)
            }
            .onFailure {
                negativeCheckerFetchUserManagement.invoke(Unit)
            }

        vehicleUsersTask.fail(ResponseError.networkError(NetworkError.NO_CONNECTION))

        Assert.assertTrue(failCaptured.isCaptured)
        Assert.assertTrue(!completeCaptured.isCaptured)
    }

    companion object {
        const val VIN_REST = "REST"
        const val VIN_CACHED = "CACHED"
        const val VIN_NOT_CACHED = "NOT_CACHED"
    }
}
