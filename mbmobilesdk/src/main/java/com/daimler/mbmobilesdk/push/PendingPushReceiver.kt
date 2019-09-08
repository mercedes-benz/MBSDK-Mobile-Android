package com.daimler.mbmobilesdk.push

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbloggerkit.MBLoggerKit

internal class PendingPushReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val data: PushData? = intent?.getParcelableExtra(ARG_PUSH_DATA)
        data?.let { pushData ->
            getLauncherIntent(context)?.let {
                MBMobileSDK.pushDataStorage().latestPushData = pushData
                context.startActivity(it)
            }
        } ?: MBMobileSDK.pushDataStorage().clear()
    }

    private fun getLauncherIntent(context: Context): Intent? {
        val launcherIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        return launcherIntent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            it
        } ?: {
            MBLoggerKit.e("No launcher intent found!")
            null
        }()
    }

    companion object {
        const val ARG_PUSH_DATA = "arg.push.data"
    }
}