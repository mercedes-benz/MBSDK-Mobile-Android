package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.vehiclestage.StageConfig
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

class AssignVehicleActionsViewModel(
    app: Application,
    val initialAssignment: Boolean,
    val stageConfig: StageConfig
) : AndroidViewModel(app) {

    val assignWithQrEvent = MutableLiveUnitEvent()
    val assignWithVinEvent = MutableLiveUnitEvent()
    val infoCallEvent = MutableLiveUnitEvent()
    val searchRetailerEvent = MutableLiveUnitEvent()
    val helpEvent = MutableLiveUnitEvent()
    val laterEvent = MutableLiveUnitEvent()

    fun onQrClicked() {
        assignWithQrEvent.sendEvent()
    }

    fun onEnterVinClicked() {
        assignWithVinEvent.sendEvent()
    }

    fun onInfoCallClicked() {
        infoCallEvent.sendEvent()
    }

    fun onSearchRetailerClicked() {
        searchRetailerEvent.sendEvent()
    }

    fun onHelpClicked() {
        helpEvent.sendEvent()
    }

    fun onLaterClicked() {
        laterEvent.sendEvent()
    }
}