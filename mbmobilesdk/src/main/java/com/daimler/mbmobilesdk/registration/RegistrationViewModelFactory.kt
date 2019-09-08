package com.daimler.mbmobilesdk.registration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.vehiclestage.StageConfig

class RegistrationViewModelFactory(
    private val app: Application,
    private val user: LoginUser,
    private val stageConfig: StageConfig
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegistrationViewModel(app, user, stageConfig) as T
    }
}