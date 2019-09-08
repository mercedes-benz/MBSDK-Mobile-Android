package com.daimler.mbmobilesdk.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class CredentialsViewModelFactory(
    private val app: Application,
    private val userId: String?
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CredentialsViewModel(app, userId) as T
    }
}