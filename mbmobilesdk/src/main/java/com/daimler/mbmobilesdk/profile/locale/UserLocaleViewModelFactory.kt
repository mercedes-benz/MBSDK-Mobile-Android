package com.daimler.mbmobilesdk.profile.locale

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.app.UserLocale

internal class UserLocaleViewModelFactory(
    private val app: Application,
    private val userLocale: UserLocale
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserLocaleViewModel(app, userLocale) as T
    }
}