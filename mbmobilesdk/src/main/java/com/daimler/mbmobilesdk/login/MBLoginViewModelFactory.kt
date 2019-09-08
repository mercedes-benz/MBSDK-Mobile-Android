package com.daimler.mbmobilesdk.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MBLoginViewModelFactory(
    private val app: Application,
    private val stageSelectionEnabled: Boolean
) :
    ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MBLoginViewModel(app, stageSelectionEnabled) as T
    }
}