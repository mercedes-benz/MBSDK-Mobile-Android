package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.vehicleusers.ProfileSyncStatus
import com.daimler.mbcarkit.network.model.ApiAutomaticSyncResponse
import com.daimler.mbcarkit.network.model.ApiNormalizedProfileControl
import com.daimler.mbcarkit.network.model.ApiProfileSyncStatus
import com.daimler.mbcarkit.network.model.ApiVehicleUserGeneralData
import com.daimler.mbcarkit.network.model.ApiVehicleUsersManagement
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.random.Random

@ExperimentalCoroutinesApi
internal class RetrofitUserManagementServiceTest : BaseRetrofitServiceTest<RetrofitUserManagementService>() {

    private val usersManagementResponse = mockk<Response<ApiVehicleUsersManagement>>()
    private val responseBodyResponse = mockk<Response<ResponseBody>>()
    private val automaticSyncResponse = mockk<Response<ApiAutomaticSyncResponse>>()
    private val normalizedProfileControlResponse = mockk<Response<ApiNormalizedProfileControl>>()

    override fun createSubject(scope: CoroutineScope): RetrofitUserManagementService =
        RetrofitUserManagementService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery {
                fetchVehicleUsers(any(), any())
            } returns usersManagementResponse
            coEvery {
                createQrInvitation(any(), any())
            } returns responseBodyResponse
            coEvery {
                deleteSubUser(any(), any(), any())
            } returns noContentResponse
            coEvery {
                configureProfileAutoSync(any(), any(), any())
            } returns noContentResponse
            coEvery {
                upgradeTemporaryUser(any(), any(), any())
            } returns noContentResponse
            coEvery {
                fetchAutomaticSyncStatus(any(), any())
            } returns automaticSyncResponse
            coEvery {
                fetchNormalizedProfileControl(any())
            } returns normalizedProfileControlResponse
            coEvery {
                configureNormalizedProfileControl(any(), any())
            } returns noContentResponse
        }
    }

    @Test
    fun `fetchVehicleUsers() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val finOrVin = "finOrVin"
        val case = ResponseTaskTestCase(usersManagementResponse) {
            subject.fetchVehicleUsers("", finOrVin)
        }

        scope.runBlockingTest {
            val api = ApiVehicleUsersManagement(
                ApiVehicleUserGeneralData(5, 1, null),
                null,
                null,
                null
            )
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.finOrVin).isEqualTo(finOrVin)
        }
    }

    @Test
    fun `fetchVehicleUsers() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        usersManagementResponse.mockError()
        val case = ResponseTaskTestCase(usersManagementResponse) {
            subject.fetchVehicleUsers("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `createQrInvitationByteArray() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(responseBodyResponse) {
            subject.createQrInvitationByteArray("", "", "")
        }

        scope.runBlockingTest {
            val bytes = Random.nextBytes(20)
            val responseBody = mockk<ResponseBody>().also {
                coEvery { it.bytes() } returns bytes
            }
            case.finish(responseBody)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success).isEqualTo(bytes)
        }
    }

    @Test
    fun `createQrInvitationByteArray() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        responseBodyResponse.mockError()
        val case = ResponseTaskTestCase(responseBodyResponse) {
            subject.createQrInvitationByteArray("", "", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteUser() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteUser("", "", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `deleteUser() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteUser("", "", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `configureAutomaticSync() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.configureAutomaticSync("", "", true)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `configureAutomaticSync() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.configureAutomaticSync("", "", true)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `upgradeTemporaryUser() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.upgradeTemporaryUser("", "", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `upgradeTemporaryUser() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.upgradeTemporaryUser("", "", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchAutomaticSyncStatus() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val api = ApiAutomaticSyncResponse(ApiProfileSyncStatus.ON)
        val case = ResponseTaskTestCase(automaticSyncResponse) {
            subject.fetchAutomaticSyncStatus("", "")
        }
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success).isEqualTo(ProfileSyncStatus.ON)
        }
    }

    @Test
    fun `fetchAutomaticSyncStatus() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        automaticSyncResponse.mockError()
        val case = ResponseTaskTestCase(automaticSyncResponse) {
            subject.fetchAutomaticSyncStatus("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchNormalizedProfileControl() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val api = ApiNormalizedProfileControl(true)
        val case = ResponseTaskTestCase(normalizedProfileControlResponse) {
            subject.fetchNormalizedProfileControl("")
        }
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success?.enabled).isEqualTo(api.enabled)
        }
    }

    @Test
    fun `fetchNormalizedProfileControl() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        normalizedProfileControlResponse.mockError()
        val case = ResponseTaskTestCase(normalizedProfileControlResponse) {
            subject.fetchNormalizedProfileControl("")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `configureNormalizedProfileControl() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.configureNormalizedProfileControl("", true)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `configureNormalizedProfileControl() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.configureNormalizedProfileControl("", true)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }
}
