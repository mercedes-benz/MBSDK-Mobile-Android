package com.daimler.mbmobilesdk.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class SettingsViewModelFactory(
    private val app: Application,
    private val biometricsSupported: Boolean
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(app, biometricsSupported) as T
    }
}