package com.daimler.mbmobilesdk.login

import com.daimler.mbmobilesdk.registration.LoginUser

internal interface MBLoginCallback {

    fun setStageSelectorVisibility(isVisible: Boolean)

    fun hideToolbar()

    fun setToolbarTitle(title: String)

    fun onStarted()

    fun onSuccess(user: LoginUser, isRegistration: Boolean)

    fun onError(error: String)

    fun onShowLocaleSelection(user: LoginUser)

    fun onShowNatcon(user: LoginUser)

    fun onShowRegistration(user: LoginUser)

    fun onShowLogin(user: LoginUser)

    fun onShowPinVerification(user: LoginUser, isLogin: Boolean)

    fun onShowLegal()
}