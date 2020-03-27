package com.daimler.mbingresskit.login

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginProcessTest {

    private val loginActionHandler: LoginActionHandler = mockk(relaxUnitFun = true)
    private val loginState: LoginState = mockk()

    private val loginProcess = LoginProcess(loginActionHandler, loginState)

    @BeforeEach
    fun setup() {
        MockKAnnotations.init()

        every { loginState.login(any()) } returns null
        every { loginState.authorized(any()) } returns null
        every { loginState.tokenReceived(any()) } returns null
        every { loginState.logout(any()) } returns Unit
    }

    @Test
    fun `login() should call loginState_login()`() {
        loginProcess.login()

        verify { loginState.login(any()) }
    }

    @Test
    fun `authorized() should call loginState_authorized()`() {
        loginProcess.authorized()

        verify { loginState.authorized(any()) }
    }

    @Test
    fun `tokenReceived() should call loginState_tokenReceived()`() {
        loginProcess.tokenReceived()

        verify { loginState.tokenReceived(any()) }
    }

    @Test
    fun `logout() should call loginState_logout()`() {
        loginProcess.logout()

        verify { loginState.logout(any()) }
    }

    @Test
    fun `authorize() should call loginActionHandler_authorize()`() {
        loginProcess.authorize()

        verify { loginActionHandler.authorize() }
    }

    @Test
    fun `requestToken() should call loginActionHandler_requestToken()`() {
        loginProcess.requestToken()

        verify { loginActionHandler.requestToken() }
    }

    @Test
    fun `finishLogin() should call loginActionHandler_finishLogin()`() {
        loginProcess.finishLogin()

        verify { loginActionHandler.finishLogin() }
    }

    @Test
    fun `finishLogout() should call loginActionHandler_finishLogout()`() {
        loginProcess.finishLogout()

        verify { loginActionHandler.finishLogout() }
    }
}
