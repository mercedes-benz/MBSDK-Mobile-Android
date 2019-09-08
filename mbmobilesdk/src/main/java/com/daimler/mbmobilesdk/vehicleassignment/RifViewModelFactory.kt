package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RifViewModelFactory(
    private val app: Application,
    private val finOrVin: String,
    private val showCheckActivationButton: Boolean
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RifViewModel(app, finOrVin, showCheckActivationButton) as T
    }
}