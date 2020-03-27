package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.UserManagementService
import com.daimler.mbcarkit.business.model.vehicleusers.NormalizedProfileControl
import com.daimler.mbcarkit.business.model.vehicleusers.ProfileSyncStatus
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement
import com.daimler.mbcarkit.socket.UserManagementCache
import com.daimler.mbcarkit.utils.ResponseTaskObject
import com.daimler.mbcarkit.utils.ResponseTaskObjectUnit
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class CachedUserManagementServiceTest {

    private val userManagementService: UserManagementService = mockk()
    private val userManagementCache: UserManagementCache = mockk()

    private lateinit var vehicleUserManagementTask: ResponseTaskObject<VehicleUserManagement>
    private lateinit var byteArrayTask: ResponseTaskObject<ByteArray>
    private lateinit var profileSyncStatusTask: ResponseTaskObject<ProfileSyncStatus>
    private lateinit var normalizedProfileControlTask: ResponseTaskObject<NormalizedProfileControl>
    private lateinit var unitTask: ResponseTaskObjectUnit

    private val subject = CachedUserManagementService(userManagementCache, userManagementService)

    @BeforeEach
    fun setup() {
        vehicleUserManagementTask = ResponseTaskObject()
        byteArrayTask = ResponseTaskObject()
        profileSyncStatusTask = ResponseTaskObject()
        normalizedProfileControlTask = ResponseTaskObject()
        unitTask = ResponseTaskObjectUnit()
        every {
            userManagementService.fetchVehicleUsers(
                any(),
                any()
            )
        } returns vehicleUserManagementTask
        every {
            userManagementService.createQrInvitationByteArray(
                any(),
                any(),
                any()
            )
        } returns byteArrayTask
        every { userManagementService.deleteUser(any(), any(), any()) } returns unitTask
        every { userManagementService.configureAutomaticSync(any(), any(), any()) } returns unitTask
        every { userManagementService.upgradeTemporaryUser(any(), any(), any()) } returns unitTask
        every {
            userManagementService.fetchAutomaticSyncStatus(
                any(),
                any()
            )
        } returns profileSyncStatusTask
        every { userManagementService.fetchNormalizedProfileControl(any()) } returns normalizedProfileControlTask
        every {
            userManagementService.configureNormalizedProfileControl(
                any(),
                any()
            )
        } returns unitTask

        every { userManagementCache.updateUserManagement(any()) } returns Unit
        every { userManagementCache.deleteUserManagement(any()) } returns Unit
        every { userManagementCache.upgradeTemporaryUser(any()) } returns Unit
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `fetchVehicleUsers with service response should update cache`(softly: SoftAssertions) {
        var success: VehicleUserManagement? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchVehicleUsers("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val vehicleUserManagement = VehicleUserManagement("", null, null, null, null, null)
        vehicleUserManagementTask.complete(vehicleUserManagement)

        verify { userManagementCache.updateUserManagement(any()) }

        softly.assertThat(success).isEqualTo(vehicleUserManagement)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicleUsers with response failure should return cached values`(softly: SoftAssertions) {
        var success: VehicleUserManagement? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchVehicleUsers("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val vehicleUserManagement = VehicleUserManagement("", null, null, null, null, null)
        every { userManagementCache.loadUserManagement(any()) } returns vehicleUserManagement

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        vehicleUserManagementTask.fail(responseError)

        softly.assertThat(success).isEqualTo(vehicleUserManagement)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicleUsers with response failure without cache should fail task`(softly: SoftAssertions) {
        var success: VehicleUserManagement? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchVehicleUsers("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        every { userManagementCache.loadUserManagement(any()) } returns null

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        vehicleUserManagementTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `createQrInvitationByteArray should return retrofit response`() {
        Assertions.assertEquals(byteArrayTask, subject.createQrInvitationByteArray("", "", ""))
    }

    @Test
    fun `deleteUser with service response should update cache`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.deleteUser("", "", "")
            .onComplete { success = true }
            .onFailure { failure = it }

        unitTask.complete(Unit)

        verify { userManagementCache.deleteUserManagement(any()) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `deleteUser with response failure should fail task`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.deleteUser("", "", "")
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        unitTask.fail(responseError)

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `configureAutomaticSync with service response`(
        softly: SoftAssertions
    ) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.configureAutomaticSync("", "", true)
            .onComplete { success = true }
            .onFailure { failure = it }

        unitTask.complete(Unit)

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `configureAutomaticSync with response failure should fail task`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.configureAutomaticSync("", "", true)
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        unitTask.fail(responseError)

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `upgradeTemporaryUser with service response should update cache`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.upgradeTemporaryUser("", "", "")
            .onComplete { success = true }
            .onFailure { failure = it }

        unitTask.complete(Unit)

        verify { userManagementCache.upgradeTemporaryUser(any()) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `upgradeTemporaryUser with response failure should fail task`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.upgradeTemporaryUser("", "", "")
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        unitTask.fail(responseError)

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `fetchAutomaticSyncStatus should return retrofit response`() {
        Assertions.assertEquals(profileSyncStatusTask, subject.fetchAutomaticSyncStatus("", ""))
    }

    @Test
    fun `fetchNormalizedProfileControl should return retrofit response`() {
        Assertions.assertEquals(
            normalizedProfileControlTask,
            subject.fetchNormalizedProfileControl("")
        )
    }

    @Test
    fun `configureNormalizedProfileControl should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.configureNormalizedProfileControl("", true))
    }
}
