package com.daimler.mbmobilesdk.tou.natcon

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.registration.LoginUser

class NatconLoginViewModelFactory(
    private val app: Application,
    private val user: LoginUser
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NatconLoginViewModel(app, user) as T
    }
}