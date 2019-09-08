package com.daimler.mbmobilesdk.profile.locale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbingresskit.common.User

internal class UserLocaleChangeViewModelFactory(
    private val user: User,
    private val userLocale: UserLocale
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserLocaleChangeViewModel(user, userLocale) as T
    }
}