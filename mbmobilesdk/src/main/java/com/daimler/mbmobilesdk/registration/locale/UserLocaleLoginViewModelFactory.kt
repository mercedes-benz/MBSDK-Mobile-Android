package com.daimler.mbmobilesdk.registration.locale

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class UserLocaleLoginViewModelFactory(
    private val app: Application,
    private val user: String,
    private val isMail: Boolean
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserLocaleLoginViewModel(app, user, isMail) as T
    }
}