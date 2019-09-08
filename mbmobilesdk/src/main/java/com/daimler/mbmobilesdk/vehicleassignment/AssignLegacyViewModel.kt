package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Application
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

class AssignLegacyViewModel(app: Application) : MBBaseToolbarViewModel(app) {

    val closeScreenEvent = MutableLiveUnitEvent()

    fun onCloseClicked() = closeScreenEvent.sendEvent()
}