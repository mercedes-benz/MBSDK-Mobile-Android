package com.daimler.mbmobilesdk.app.foreground

internal interface ForegroundObservable {

    val appInForeground: Boolean

    fun registerForegroundListener(listener: ForegroundListener)

    fun unregisterForegroundListener(listener: ForegroundListener)
}