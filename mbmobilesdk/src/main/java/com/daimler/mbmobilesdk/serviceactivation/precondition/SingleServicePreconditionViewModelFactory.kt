package com.daimler.mbmobilesdk.serviceactivation.precondition

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbcarkit.business.model.services.Service

internal class SingleServicePreconditionViewModelFactory(
    private val app: Application,
    private val finOrVin: String,
    private val service: Service
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SingleServicePreconditionViewModel(app, finOrVin, service) as T
    }
}