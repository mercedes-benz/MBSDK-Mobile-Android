package com.daimler.mbmobilesdk.app.userswap

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.daimler.mbmobilesdk.app.foreground.ForegroundObserver
import com.daimler.mbmobilesdk.app.userswap.id.UserIdProvider
import com.daimler.mbloggerkit.MBLoggerKit

internal class UserChangeProcessor(
    app: Application,
    private val idProvider: UserIdProvider,
    private val onRestartAction: (() -> Unit)?
) : ForegroundObserver(app) {

    override fun onFirstActivityStarted(activity: AppCompatActivity) {
        MBLoggerKit.d("First activity started -> $activity.")
    }

    override fun onAppWentToForeground(activity: AppCompatActivity) {
        MBLoggerKit.d("App went to foreground -> $activity.")
        val last = idProvider.lastKnownUserId()
        val current = idProvider.activeUserId()
        if (last != current) {
            // different user/ login/ logout
            onRestartAction?.invoke()
            idProvider.saveCurrentUserId()
            startLauncherAndClearTasks(activity)
        } else {
            // same user/ still logged out
        }
    }

    override fun onAppWentToBackground() {
        MBLoggerKit.d("App went to background.")
        idProvider.saveCurrentUserId()
    }

    private fun startLauncherAndClearTasks(activity: AppCompatActivity) {
        MBLoggerKit.d("Restarting launcher activity.")
        val pm = activity.packageManager
        val launcherIntent = pm.getLaunchIntentForPackage(activity.packageName)
        launcherIntent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(it)
            activity.finish()
        } ?: MBLoggerKit.e("No launcher intent found!")
    }
}