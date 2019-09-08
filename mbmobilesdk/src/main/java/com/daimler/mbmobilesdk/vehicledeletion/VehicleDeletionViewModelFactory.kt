package com.daimler.mbmobilesdk.vehicledeletion

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class VehicleDeletionViewModelFactory(
    private val app: Application,
    private val vehicles: List<DeletableVehicle>
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VehicleDeletionViewModel(app, vehicles) as T
    }
}