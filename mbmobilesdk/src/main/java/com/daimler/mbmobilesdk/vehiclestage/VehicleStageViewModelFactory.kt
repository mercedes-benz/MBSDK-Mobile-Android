package com.daimler.mbmobilesdk.vehiclestage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo

internal class VehicleStageViewModelFactory(
    private val app: Application,
    private val vehicle: VehicleInfo
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VehicleStageViewModel(app, vehicle) as T
    }
}