package com.daimler.mbmobilesdk.countryselection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocaleChooserViewModelFactory(
    private val app: Application,
    private val currentCountryCode: String?
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocaleChooserViewModel(app, currentCountryCode) as T
    }
}