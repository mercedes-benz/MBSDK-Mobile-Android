package com.daimler.mbmobilesdk.menu

import com.daimler.mbmobilesdk.push.PushData
import com.daimler.mbmobilesdk.push.process.PushStateActionHandler
import com.daimler.mbmobilesdk.push.process.PushStateProcess
import com.daimler.mbmobilesdk.push.toPushState

internal class PushDataHandler(private val interactor: PushDataInteractor) : PushStateActionHandler {

    override fun onPushHandlingCancelled() = Unit

    override fun onGeneralNotification() = Unit

    override fun onShowUrl(url: String) {
        interactor.onShowUrl(url)
    }

    override fun onHandleDeepLink(deepLink: String) {
        interactor.onRedirectToDeepLink(deepLink)
    }

    fun handlePushData(pushData: PushData) {
        val state = pushData.toPushState()
        val process = PushStateProcess(state, this)
        process.confirm()
    }
}