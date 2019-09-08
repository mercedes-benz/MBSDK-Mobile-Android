package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.vehiclestage.StageConfig

internal class AssignVehicleActionsViewModelFactory(
    private val app: Application,
    private val initialAssignment: Boolean,
    private val stageConfig: StageConfig
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // TODO showHelpContainer
        return AssignVehicleActionsViewModel(app, initialAssignment, stageConfig) as T
    }
}