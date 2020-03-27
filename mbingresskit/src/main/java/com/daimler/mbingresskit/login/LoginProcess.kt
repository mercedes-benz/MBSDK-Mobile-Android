package com.daimler.mbingresskit.login

class LoginProcess(
    private var loginActionHandler: LoginActionHandler,
    initialState: LoginState
) {

    var loginState = initialState
        internal set

    fun login() = loginState.login(this)

    fun authorized() = loginState.authorized(this)

    fun tokenReceived() = loginState.tokenReceived(this)

    fun logout() = loginState.logout(this)

    internal fun authorize() = loginActionHandler.authorize()

    internal fun requestToken() = loginActionHandler.requestToken()

    internal fun finishLogin() = loginActionHandler.finishLogin()

    internal fun finishLogout() = loginActionHandler.finishLogout()
}
