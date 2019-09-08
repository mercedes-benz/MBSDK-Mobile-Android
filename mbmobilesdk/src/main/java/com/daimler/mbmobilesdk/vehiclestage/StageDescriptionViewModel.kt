package com.daimler.mbmobilesdk.vehiclestage

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal abstract class StageDescriptionViewModel(app: Application) : AndroidViewModel(app) {

    val progressVisible = mutableLiveDataOf(false)
    val actionEvent = MutableLiveUnitEvent()
    val cancelEvent = MutableLiveUnitEvent()

    abstract fun getHeadline(): String

    abstract fun getDescription(): String

    abstract fun getActionButtonText(): String

    abstract fun getCancelButtonText(): String

    @CallSuper
    open fun onActionClicked() {
        actionEvent.sendEvent()
    }

    @CallSuper
    open fun onLaterClicked() {
        cancelEvent.sendEvent()
    }
}