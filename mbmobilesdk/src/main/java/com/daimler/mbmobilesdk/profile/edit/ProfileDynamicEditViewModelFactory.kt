package com.daimler.mbmobilesdk.profile.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbingresskit.common.User

internal class ProfileDynamicEditViewModelFactory(
    private val app: Application,
    private val user: User,
    private val editMode: ProfileEditMode
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileDynamicEditViewModel(app, user, editMode) as T
    }
}