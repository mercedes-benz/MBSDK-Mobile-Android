package com.daimler.mbmobilesdk.push.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daimler.mbmobilesdk.push.PushData
import com.daimler.mbmobilesdk.push.process.PushProcess
import com.daimler.mbmobilesdk.push.process.PushStateActionHandler
import com.daimler.mbmobilesdk.push.process.PushStateProcess
import com.daimler.mbmobilesdk.push.toPushState
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

internal class PushDialogViewModel(
    pushData: PushData
) : ViewModel(), PushStateActionHandler {

    val title = pushData.title
    val message = pushData.body

    val cancelVisible = MutableLiveData<Boolean>()

    val showUrlEvent = MutableLiveEvent<String>()
    val redirectToDeepLinkEvent = MutableLiveEvent<String>()
    val confirmEvent = MutableLiveUnitEvent()
    val cancelEvent = MutableLiveUnitEvent()

    private val pushProcess: PushProcess

    init {
        val state = pushData.toPushState()
        cancelVisible.postValue(state.cancelable)
        pushProcess = PushStateProcess(state, this)
    }

    fun onContinueClicked() {
        pushProcess.confirm()
    }

    fun onCancelClicked() {
        pushProcess.cancel()
    }

    override fun onPushHandlingCancelled() {
        cancelEvent.sendEvent()
    }

    override fun onGeneralNotification() {
        confirmEvent.sendEvent()
    }

    override fun onShowUrl(url: String) {
        showUrlEvent.sendEvent(url)
    }

    override fun onHandleDeepLink(deepLink: String) {
        redirectToDeepLinkEvent.sendEvent(deepLink)
    }
}