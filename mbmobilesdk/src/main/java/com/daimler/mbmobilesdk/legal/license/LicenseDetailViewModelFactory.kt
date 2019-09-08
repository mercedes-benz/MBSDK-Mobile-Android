package com.daimler.mbmobilesdk.legal.license

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LicenseDetailViewModelFactory(
    private val app: Application,
    private val name: String,
    private val fileName: String
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LicenseDetailViewModel(app, name, fileName) as T
    }
}