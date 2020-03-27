package com.daimler.mbingresskit.implementation

import com.daimler.mbingresskit.common.JwtToken
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.network.ropc.TokenRepository
import com.daimler.mbingresskit.login.LoginActionHandler
import com.daimler.mbingresskit.login.LoginFailure
import com.daimler.mbingresskit.login.LoginProcess
import com.daimler.mbingresskit.login.LoginService
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.Task
import com.daimler.mbnetworkkit.task.TaskObject
import com.daimler.testutils.reflection.mockFields
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class UserCredentialsLoginServiceTest {

    private val authStateRepository: AuthstateRepository = mockk(relaxed = true)
    private val tokenRepository: TokenRepository = mockk()

    @Test
    fun `start login with no usercredentials should fail with missing credentials`(softly: SoftAssertions) {
        var successful: Boolean? = null
        var failure: ResponseError<out RequestError>? = null

        val subject: LoginService = UserCredentialsLoginService(
            authStateRepository = authStateRepository,
            userCredentials = null,
            tokenRepository = tokenRepository,
            deviceId = DEVICE_ID
        )

        subject.startLogin().onComplete {
            successful = true
        }.onFailure {
            failure = it
        }

        softly.assertThat(successful).isNull()
        softly.assertThat(failure?.requestError).isEqualTo(LoginFailure.MISSING_USER_CREDENTIALS)
    }

    @Test
    fun `start login with usercredentials should trigger login process`(softly: SoftAssertions) {
        val loginProcess: LoginProcess = mockk(relaxed = true)
        val subject: LoginService = UserCredentialsLoginService(
            authStateRepository = authStateRepository,
            userCredentials = UserCredentials("username", "password"),
            tokenRepository = tokenRepository,
            deviceId = DEVICE_ID
        )
        subject.mockFields(loginProcess)
        subject.startLogin()

        verify {
            loginProcess.login()
        }
    }

    @Test
    fun `request token will fail when password is null`(softly: SoftAssertions) {
        val loginTask: Task<Unit, ResponseError<out RequestError>?> = mockk(relaxed = true)
        val subject: LoginActionHandler = UserCredentialsLoginService(
            authStateRepository = authStateRepository,
            userCredentials = UserCredentials("username"),
            tokenRepository = tokenRepository,
            deviceId = DEVICE_ID
        )
        subject.mockFields(loginTask)
        subject.requestToken()

        verify {
            loginTask.fail(any())
        }
    }

    @Test
    fun `request token will success when usercredentials are provided`(softly: SoftAssertions) {
        val loginProcess: LoginProcess = mockk(relaxed = true)
        val userCredentials = UserCredentials("username", "password")
        val subject: LoginActionHandler = UserCredentialsLoginService(
            authStateRepository = authStateRepository,
            userCredentials = userCredentials,
            tokenRepository = tokenRepository,
            deviceId = DEVICE_ID
        )

        val tokenTask = TaskObject<Token, ResponseError<out RequestError>?>()
        every { tokenRepository.requestToken(DEVICE_ID, userCredentials) } returns tokenTask.futureTask()
        subject.mockFields(loginProcess)
        subject.requestToken()
        tokenTask.complete(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE,
                authenticationType = AuthenticationType.KEYCLOAK
            )
        )

        verify {
            loginProcess.tokenReceived()
        }
    }

    @Test
    fun `request token will fail when requests fails`(softly: SoftAssertions) {
        val loginTask: Task<Unit, ResponseError<out RequestError>?> = mockk(relaxed = true)
        val userCredentials = UserCredentials("username", "password")
        val subject: LoginActionHandler = UserCredentialsLoginService(
            authStateRepository = authStateRepository,
            userCredentials = userCredentials,
            tokenRepository = tokenRepository,
            deviceId = DEVICE_ID
        )

        val tokenTask = TaskObject<Token, ResponseError<out RequestError>?>()
        every { tokenRepository.requestToken(DEVICE_ID, userCredentials) } returns tokenTask.futureTask()
        subject.mockFields(loginTask)
        subject.requestToken()
        tokenTask.fail(ResponseError.networkError(NetworkError.NO_CONNECTION))

        verify {
            loginTask.fail(any())
        }
    }

    companion object {
        private const val DEVICE_ID = "deviceID"
        private const val TEST_ACCESS_TOKEN = "1234asdf5678jklö"
        private const val TEST_REFRESH_TOKEN = "asdf1234jklö5678"
        private const val TEST_JWT_TOKEN = "asdfjklöasdfjklö"
        private const val TOKEN_TYPE_REFRESH = "Refresh"
    }
}
