package com.daimler.mbmobilesdk.tou.natcon

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class NatconLegalViewModelFactory(
    private val app: Application,
    private val countryCode: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NatconLegalViewModel(app, countryCode) as T
    }
}