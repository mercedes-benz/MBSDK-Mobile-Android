package com.daimler.mbmobilesdk.app.foreground

import android.app.Application
import androidx.appcompat.app.AppCompatActivity

internal class MBMobileSDKForegroundObserver(
    app: Application
) : ForegroundObserver(app), ForegroundObservable {

    override val appInForeground: Boolean
        get() = _appInForeground

    private var _appInForeground: Boolean = false

    private val listeners = mutableListOf<ForegroundListener>()

    override fun onFirstActivityStarted(activity: AppCompatActivity) {
        _appInForeground = true
    }

    override fun onAppWentToForeground(activity: AppCompatActivity) {
        _appInForeground = true
        notifyAppWentToForeground()
    }

    override fun onAppWentToBackground() {
        _appInForeground = false
        notifyAppWentToBackground()
    }

    override fun registerForegroundListener(listener: ForegroundListener) {
        if (!listeners.contains(listener)) listeners.add(listener)
    }

    override fun unregisterForegroundListener(listener: ForegroundListener) {
        listeners.remove(listener)
    }

    private fun notifyAppWentToForeground() {
        listeners.forEach { it.onAppWentToForeground() }
    }

    private fun notifyAppWentToBackground() {
        listeners.forEach { it.onAppWentToBackground() }
    }
}