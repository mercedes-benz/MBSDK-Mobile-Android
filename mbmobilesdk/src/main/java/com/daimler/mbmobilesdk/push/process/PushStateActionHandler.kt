package com.daimler.mbmobilesdk.push.process

internal interface PushStateActionHandler {

    fun onPushHandlingCancelled()

    fun onGeneralNotification()

    fun onShowUrl(url: String)

    fun onHandleDeepLink(deepLink: String)
}