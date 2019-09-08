package com.daimler.mbmobilesdk.app.foreground

/**
 * Listener that notifies about changes of the foreground/ background state of the application.
 */
internal interface ForegroundListener {

    /**
     * Called when the app went to the foreground.
     * NOTE: This is NOT called when the application just launched.
     */
    fun onAppWentToForeground()

    /**
     * Called when the app went to background.
     */
    fun onAppWentToBackground()
}