package com.daimler.mbmobilesdk.languageselection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LanguageChooserViewModelFactory(
    private val app: Application,
    private val currentLanguageCode: String?
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LanguageChooserViewModel(app, currentLanguageCode) as T
    }
}