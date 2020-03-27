package com.daimler.mbingresskit.login

import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class UserCredentialsLoginStateTest {

    private val loginActionHandler: LoginActionHandler = mockk(relaxUnitFun = true)

    private val loggedOutProcess = spyk(LoginProcess(loginActionHandler, UserCredentialsLoginState.LoggedOut))
    private val requestingTokenProcess = spyk(LoginProcess(loginActionHandler, UserCredentialsLoginState.RequestingToken))
    private val loggedInProcess = spyk(LoginProcess(loginActionHandler, UserCredentialsLoginState.LoggedIn))

    @BeforeEach
    fun setup() {
        MockKAnnotations.init()
    }

    @Test
    fun `LoggedOut-login should set state to RequestingToken`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.LoggedOut.login(loggedOutProcess)

        softly.assertThat(optionalFailure).isNull()
        softly.assertThat(loggedOutProcess.loginState).isEqualTo(UserCredentialsLoginState.RequestingToken)
    }

    @Test
    fun `LoggedOut-authorized should fail`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.LoggedOut.authorized(loggedOutProcess)

        softly.assertThat(optionalFailure).isEqualTo(LoginFailure.OPERATION_NOT_REQUIRED)
    }

    @Test
    fun `LoggedOut-tokenReceived should fail`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.LoggedOut.tokenReceived(loggedOutProcess)

        softly.assertThat(optionalFailure).isEqualTo(LoginFailure.TOKENRECEIVED_CALLED_WHEN_CLIENT_NOT_AUTHORIZED)
    }

    @Test
    fun `LoggedOut-logout should set state to LoggedOut`(softly: SoftAssertions) {
        UserCredentialsLoginState.LoggedOut.logout(loggedOutProcess)

        softly.assertThat(loggedOutProcess.loginState).isEqualTo(UserCredentialsLoginState.LoggedOut)
    }

    @Test
    fun `RequestingToken-login should fail`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.RequestingToken.login(requestingTokenProcess)

        softly.assertThat(optionalFailure).isEqualTo(LoginFailure.LOGIN_CALLED_WHEN_LOGIN_ALREADY_STARTED)
    }

    @Test
    fun `RequestingToken-authorized should fail`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.RequestingToken.authorized(requestingTokenProcess)

        softly.assertThat(optionalFailure).isEqualTo(LoginFailure.OPERATION_NOT_REQUIRED)
    }

    @Test
    fun `RequestingToken-tokenReceived should set state to LoggedIn`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.RequestingToken.tokenReceived(requestingTokenProcess)

        softly.assertThat(optionalFailure).isNull()
        softly.assertThat(requestingTokenProcess.loginState).isEqualTo(UserCredentialsLoginState.LoggedIn)
    }

    @Test
    fun `RequestingToken-logout should set state to LoggedOut`(softly: SoftAssertions) {
        UserCredentialsLoginState.RequestingToken.logout(requestingTokenProcess)

        softly.assertThat(requestingTokenProcess.loginState).isEqualTo(UserCredentialsLoginState.LoggedOut)
    }

    @Test
    fun `LoggedIn-login should fail`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.LoggedIn.login(loggedInProcess)

        softly.assertThat(optionalFailure).isEqualTo(LoginFailure.LOGIN_CALLED_WHEN_ALREADY_LOGGED_IN)
    }

    @Test
    fun `LoggedIn-authorized should fail`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.LoggedIn.authorized(loggedInProcess)

        softly.assertThat(optionalFailure).isEqualTo(LoginFailure.OPERATION_NOT_REQUIRED)
    }

    @Test
    fun `LoggedIn-tokenReceived should fail`(softly: SoftAssertions) {
        val optionalFailure = UserCredentialsLoginState.LoggedIn.tokenReceived(loggedInProcess)

        softly.assertThat(optionalFailure).isEqualTo(LoginFailure.TOKENRECEIVED_CALLED_WHEN_ALREADY_LOGGED_IN)
    }

    @Test
    fun `LoggedIn-logout should set state to LoggedOut`(softly: SoftAssertions) {
        UserCredentialsLoginState.LoggedIn.logout(loggedInProcess)

        softly.assertThat(loggedInProcess.loginState).isEqualTo(UserCredentialsLoginState.LoggedOut)
    }
}
