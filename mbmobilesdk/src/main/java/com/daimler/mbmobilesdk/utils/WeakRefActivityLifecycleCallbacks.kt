package com.daimler.mbmobilesdk.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

/**
 * [Application.ActivityLifecycleCallbacks] implementation that holds the current foreground
 * activity in a [WeakReference]. The reference is set when the activity was started and cleared
 * when the activity stopped.
 * You can receive the current activity via calling [activity]. An exception will be thrown
 * when you try to receive the activity when none is set. Check [activitySet] before.
 *
 * @param needsAppCompatActivity true if you always need an [AppCompatActivity]
 */
open class WeakRefActivityLifecycleCallbacks(
    private val needsAppCompatActivity: Boolean
) : Application.ActivityLifecycleCallbacks {

    private var currentActivity: WeakReference<Activity>? = null

    /**
     * True if there is an activity referenced.
     */
    protected val activitySet: Boolean
        get() = currentActivity?.get() != null

    /**
     * Subclasses MUST call this method to register the callbacks.
     */
    protected fun initCallbacks(app: Application) {
        app.registerActivityLifecycleCallbacks(this)
    }

    /**
     * Returns the current foreground activity.
     *
     * @throws IllegalStateException if there is no activity referenced
     * @throws IllegalArgumentException if this callbacks implementation needs an
     * [AppCompatActivity] but there is none set
     */
    protected fun activity(): Activity {
        val activity = currentActivity?.get()
            ?: throw IllegalStateException("No activity attached. Did you call init()?")
        return if (needsAppCompatActivity) {
            activity as? AppCompatActivity
                ?: throw IllegalArgumentException("Only AppCompatActivity is supported!")
        } else {
            activity
        }
    }

    protected open fun onActivitySet(activity: Activity) = Unit

    protected open fun onActivityCleared() = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityStarted(activity: Activity) {
        currentActivity?.clear()
        currentActivity = WeakReference(activity)
        onActivitySet(activity)
    }

    override fun onActivityDestroyed(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) = Unit

    override fun onActivityStopped(activity: Activity) {
        if (currentActivity?.get() == activity) {
            // other activities would have been cleared when onActivityStarted was called
            currentActivity?.clear()
            currentActivity = null
            onActivityCleared()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit
}