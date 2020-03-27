package com.daimler.mbingresskit.login

interface LoginActionHandler {
    fun authorize()
    fun requestToken()
    fun finishLogin()
    fun finishLogout()
}
