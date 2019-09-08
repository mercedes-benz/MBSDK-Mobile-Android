package com.daimler.mbmobilesdk.menu

internal interface PushDataInteractor {

    fun onShowUrl(url: String)

    fun onRedirectToDeepLink(deepLinkReference: String)
}