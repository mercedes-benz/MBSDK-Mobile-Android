package com.daimler.mbmobilesdk.profile.completion

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbingresskit.common.User

internal class ProfileCompletionViewModelFactory(
    private val app: Application,
    private val user: User
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileCompletionViewModel(app, user) as T
    }
}