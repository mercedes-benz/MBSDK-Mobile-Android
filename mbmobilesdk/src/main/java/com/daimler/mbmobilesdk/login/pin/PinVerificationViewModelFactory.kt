package com.daimler.mbmobilesdk.login.pin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.registration.LoginUser

class PinVerificationViewModelFactory(
    private val app: Application,
    private val isRegistration: Boolean,
    private val user: LoginUser
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PinVerificationViewModel(app, isRegistration, user) as T
    }
}