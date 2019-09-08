package com.daimler.mbmobilesdk.app.foreground

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.daimler.mbmobilesdk.utils.WeakRefActivityLifecycleCallbacks
import com.daimler.mbloggerkit.MBLoggerKit

/**
 * [LifecycleObserver] that notifies you about app foreground/ background events.
 */
abstract class ForegroundObserver(app: Application) :
    WeakRefActivityLifecycleCallbacks(true),
    LifecycleObserver {

    private var wasStarted = false
    private var onStartAction: ((AppCompatActivity) -> Unit)? = null

    init {
        initCallbacks(app)
    }

    /**
     * Called when the first activity was started within an application lifecycle.
     */
    abstract fun onFirstActivityStarted(activity: AppCompatActivity)

    /**
     * Called when the app went to the foreground.
     * NOTE: This is NOT called when the application just launched, so this is never called
     * if [onFirstActivityStarted] was called before.
     */
    abstract fun onAppWentToForeground(activity: AppCompatActivity)

    /**
     * Called when the app went to background.
     */
    abstract fun onAppWentToBackground()

    @Suppress("UNUSED")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        if (wasStarted) {
            dispatchActivityStart { onAppWentToForeground(it) }
        } else {
            dispatchActivityStart { onFirstActivityStarted(it) }
        }
        wasStarted = true
    }

    @Suppress("UNUSED")
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        onAppWentToBackground()
    }

    override fun onActivityStarted(activity: Activity) {
        super.onActivityStarted(activity)
        onStartAction?.invoke(activity as AppCompatActivity)
        onStartAction = null
    }

    private fun dispatchActivityStart(action: (AppCompatActivity) -> Unit) {
        if (activitySet) {
            action(activity() as AppCompatActivity)
        } else {
            MBLoggerKit.d("Postponing notification until activity started.")
            onStartAction = action
        }
    }
}