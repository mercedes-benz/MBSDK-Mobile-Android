package com.daimler.mbmobilesdk.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment

abstract class BaseLoginFragment<T : ViewModel> : MBBaseViewModelFragment<T>() {

    private var loginCallback: MBLoginCallback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if ((context is MBLoginCallback).not()) {
            throw IllegalStateException("Parent Activity which hosts ${BaseLoginFragment::class.java.simpleName} " +
                "must implement ${MBLoginCallback::class.java.simpleName}")
        } else {
            loginCallback = context as MBLoginCallback
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginCallback = null
    }

    internal fun notifyEndpointVisibilityChanged(isVisible: Boolean) {
        loginCallback?.setStageSelectorVisibility(isVisible)
    }

    internal fun notifyHideToolbar() {
        loginCallback?.hideToolbar()
    }

    internal fun notifyToolbarTitle(title: String) {
        loginCallback?.setToolbarTitle(title)
    }

    internal fun notifyLoginStarted() {
        loginCallback?.onStarted()
    }

    internal fun notifyPinVerified(user: LoginUser, isRegistration: Boolean) {
        loginCallback?.onSuccess(user, isRegistration)
    }

    internal fun notifyPinError(error: String) {
        loginCallback?.onError(error)
    }

    internal fun notifyShowLocaleSelection(user: LoginUser) {
        loginCallback?.onShowLocaleSelection(user)
    }

    internal fun notifyShowRegistration(user: LoginUser) {
        loginCallback?.onShowRegistration(user)
    }

    internal fun notifyShowLogin(user: LoginUser) {
        loginCallback?.onShowLogin(user)
    }

    internal fun notifyShowNatcon(user: LoginUser) {
        loginCallback?.onShowNatcon(user)
    }

    internal fun notifyShowPinVerification(user: LoginUser, isLogin: Boolean) {
        loginCallback?.onShowPinVerification(user, isLogin)
    }

    internal fun notifyShowLegal() {
        loginCallback?.onShowLegal()
    }
}