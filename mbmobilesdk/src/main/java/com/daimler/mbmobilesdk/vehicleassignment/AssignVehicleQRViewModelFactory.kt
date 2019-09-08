package com.daimler.mbmobilesdk.vehicleassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AssignVehicleQRViewModelFactory(
    private val hintText: String?
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AssignVehicleQRViewModel(hintText) as T
    }
}