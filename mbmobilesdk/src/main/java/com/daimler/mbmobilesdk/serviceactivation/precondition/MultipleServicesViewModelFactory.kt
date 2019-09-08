package com.daimler.mbmobilesdk.serviceactivation.precondition

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbcarkit.business.model.services.Service

internal class MultipleServicesViewModelFactory(
    private val app: Application,
    private val finOrVin: String,
    private val services: List<Service>
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MultipleServicesPreconditionViewModel(app, finOrVin, services) as T
    }
}