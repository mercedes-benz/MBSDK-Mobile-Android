package com.daimler.mbmobilesdk.pin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserPinViewModelFactory(
    private val app: Application,
    private val initialPin: Boolean
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        UserPinViewModel(app, initialPin) as T
}