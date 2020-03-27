package com.daimler.mbingresskit.implementation.network.service

import com.daimler.mbingresskit.common.RegistrationUserAgreementUpdates
import com.daimler.mbingresskit.common.UserBiometricState
import com.daimler.mbingresskit.implementation.etag.ETagProvider
import com.daimler.mbingresskit.implementation.network.api.UserApi
import com.daimler.mbingresskit.implementation.network.error.NotModifiedError
import com.daimler.mbingresskit.implementation.network.model.country.CountryResponse
import com.daimler.mbingresskit.implementation.network.model.pin.LoginUserResponse
import com.daimler.mbingresskit.implementation.network.model.user.create.CreateUserResponse
import com.daimler.mbingresskit.implementation.network.model.user.fetch.UserTokenResponse
import com.daimler.mbingresskit.implementation.network.ropc.nonce.NonceProvider
import com.daimler.mbingresskit.testutils.ResponseTaskTestCase
import com.daimler.mbingresskit.testutils.createUser
import com.daimler.mbnetworkkit.header.HeaderService
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class RetrofitUserServiceTest :
    BaseRetrofitServiceTest<UserApi, RetrofitUserService>() {

    override val api: UserApi = mockk()
    override val headerService: HeaderService = mockk(relaxed = true)

    private val eTagProvider = mockk<ETagProvider>(relaxed = true)
    private val nonceProvider = mockk<NonceProvider>(relaxed = true)

    private val sendPinResponse = mockk<Response<LoginUserResponse>>(relaxed = true)
    private val responseBodyResponse = mockk<Response<ResponseBody>>()
    private val loadUserResponse = mockk<Response<UserTokenResponse>>()
    private val createUserResponse = mockk<Response<CreateUserResponse>>()
    private val updateUserResponse = mockk<Response<UserTokenResponse>>()
    private val countriesResponse = mockk<Response<List<CountryResponse>>>()
    private val registrationConsent = RegistrationUserAgreementUpdates(
        countryCode = "DE",
        updates = emptyList()
    )

    @BeforeEach
    fun setup() {
        api.apply {
            coEvery { sendTan(any()) } returns sendPinResponse
            coEvery { fetchUserData(any()) } returns loadUserResponse
            coEvery { createUser(any(), any()) } returns createUserResponse
            coEvery { updateUser(any(), any(), any()) } returns updateUserResponse
            coEvery { deleteUser(any(), any()) } returns noContentResponse
            coEvery { setPin(any(), any()) } returns noContentResponse
            coEvery { changePin(any(), any()) } returns noContentResponse
            coEvery { deletePin(any(), any()) } returns noContentResponse
            coEvery { resetPin(any()) } returns noContentResponse
            coEvery { updateProfilePicture(any(), any()) } returns noContentResponse
            coEvery {
                fetchProfilePictureIfModified(
                    any(),
                    any()
                )
            } returns responseBodyResponse
            coEvery { fetchCountries(any()) } returns countriesResponse
            coEvery {
                sendBiometricActivation(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
        }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    override fun createSubject(scope: CoroutineScope): RetrofitUserService =
        RetrofitUserService(api, headerService, eTagProvider, nonceProvider, scope)

    @Test
    fun `send pin and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(sendPinResponse) {
            subject.sendPin("", "")
        }

        scope.runBlockingTest {
            case.finish(LoginUserResponse("", false))
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.sendTan(any())
            }
        }
    }

    @Test
    fun `load user and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(loadUserResponse) {
            subject.loadUser("")
        }
        scope.runBlockingTest {
            case.finish(emptyUserTokenResponse())
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.fetchUserData(any())
            }
        }
    }

    @Test
    fun `create user and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(createUserResponse) {
            subject.createUser(true, createUser())
        }
        val data = CreateUserResponse(
            "", "", "", "",
            null, "", "", null, null
        )
        scope.runBlockingTest {
            case.finish(data)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.createUser(any(), any())
            }
        }
    }

    @Test
    fun `create user with consent and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(createUserResponse) {
            subject.createUserWithConsent(true, createUser(), registrationConsent)
        }
        val data = CreateUserResponse(
            "", "", "", "",
            null, "", "", null, null
        )
        scope.runBlockingTest {
            case.finish(data)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.createUser(any(), any())
            }
        }
    }

    @Test
    fun `update user and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(updateUserResponse) {
            subject.updateUser("", createUser())
        }
        scope.runBlockingTest {
            case.finish(emptyUserTokenResponse())
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.updateUser(any(), any(), any())
            }
        }
    }

    @Test
    fun `delete user and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteUser("", createUser())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.deleteUser(any(), any())
            }
        }
    }

    @Test
    fun `update profile picture and verify user api call`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.updateProfilePicture("", ByteArray(1), "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.updateProfilePicture(any(), any())
            }
        }
    }

    @Test
    fun `fetch profile picture and verify user api call`(scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(responseBodyResponse) {
            subject.fetchProfilePictureBytes("")
        }
        scope.runBlockingTest {
            case.finish(null)
            coVerify {
                api.fetchProfilePictureIfModified(any(), any())
            }
        }
    }

    @Test
    fun `fetch profile picture should fail on 304`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        responseBodyResponse.apply {
            every { isSuccessful } returns false
            every { code() } returns 304
            every { body() } returns null
        }
        val case = ResponseTaskTestCase(responseBodyResponse) {
            subject.fetchProfilePictureBytes("")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(NotModifiedError::class.java)
        }
    }

    @Test
    fun `fetch countries and verify user api call`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(countriesResponse) {
            subject.fetchCountries()
        }
        val data = CountryResponse(
            false, "", "", null, "",
            null, null, false, false
        )
        scope.runBlockingTest {
            case.finish(listOf(data))
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.fetchCountries(any())
            }
        }
    }

    @Test
    fun `set pin and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.setPin("", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.setPin(any(), any())
            }
        }
    }

    @Test
    fun `change pin and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.changePin("", "", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.changePin(any(), any())
            }
        }
    }

    @Test
    fun `delete pin and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deletePin("", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.deletePin(any(), any())
            }
        }
    }

    @Test
    fun `reset pin and verify user api call`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.resetPin("")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.resetPin(any())
            }
        }
    }

    @Test
    fun `send biometric activation and verify user api call`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.sendBiometricActivation("", "", UserBiometricState.ENABLED, "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            coVerify {
                api.sendBiometricActivation(any(), any(), any())
            }
        }
    }

    private fun emptyUserTokenResponse() = UserTokenResponse(
        "", null, null, null, null, null,
        null, null, null, null, null,
        "", "", null, null, null, null, null,
        null, null, null, null, false, false, false
    )
}
