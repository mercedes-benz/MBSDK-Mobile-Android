package com.daimler.mbmobilesdk.jumio.identitycheckhint

import android.app.Application
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

class IdentityCheckHintViewModel(app: Application) : MBBaseToolbarViewModel(app) {

    val onStartClickEvent = MutableLiveUnitEvent()
    val onLaterClickedEvent = MutableLiveUnitEvent()

    fun onStartClicked() {
        onStartClickEvent.sendEvent()
    }

    fun onLaterClicked() {
        onLaterClickedEvent.sendEvent()
    }
}