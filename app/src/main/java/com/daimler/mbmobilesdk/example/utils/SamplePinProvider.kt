package com.daimler.mbmobilesdk.example.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.socket.PinCommandVehicleApiStatusCallback
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.isPinSet
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.example.dialogs.error.ErrorDialogFragment
import com.daimler.mbmobilesdk.example.dialogs.pin.PinInputDialogFragment
import java.lang.ref.WeakReference
import java.util.Date
import java.util.concurrent.TimeUnit

class SamplePinProvider :
    PinProvider,
    Application.ActivityLifecycleCallbacks,
    PinInputDialogFragment.Callback,
    PinCommandVehicleApiStatusCallback {

    private var activityRef: WeakReference<FragmentActivity>? = null

    private var currentPinRequest: PinRequest? = null

    /*
    PinProvider
     */

    override fun requestPin(pinRequest: PinRequest) {
        clearRequest()

        val activity = activityRef?.get() ?: run {
            pinRequest.cancel(null)
            return
        }

        if (MBIngressKit.cachedUser()?.isPinSet == true) {
            currentPinRequest = pinRequest
            // We only show a dialog if it is not visible yet.
            // In case it is already visible we have just overwritten the current PinRequest,
            // so that the given pin will be delivered to the most recent request.
            showPinDialogIfNotVisible(activity)
        } else {
            showPinNotSetError(activity)
        }
    }

    /*
    PinCommandVehicleApiStatusCallback
     */

    override fun onPinAccepted(commandStatus: CommandVehicleApiStatus, pin: String) {
        MBLoggerKit.i("Pin was accepted.")
    }

    override fun onPinInvalid(commandStatus: CommandVehicleApiStatus, pin: String, attempts: Int) {
        MBLoggerKit.e("Pin was invalid. Attempts: $attempts")
        activityRef?.get()?.let {
            showInvalidPinError(it)
        }
    }

    override fun onUserBlocked(
        commandStatus: CommandVehicleApiStatus,
        pin: String,
        attempts: Int,
        blockingTimeSeconds: Int
    ) {
        MBLoggerKit.e("User is blocked. Attempts = $attempts, blockingTimeSeconds = $blockingTimeSeconds.")
        activityRef?.get()?.let {
            showUserBlockedError(it, attempts, blockingTimeSeconds)
        }
    }

    /*
    ActivityLifecycleCallbacks
     */

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStarted(activity: Activity) {
        // We only accept FragmentActivity.
        (activity as? FragmentActivity)?.let {
            activityRef = WeakReference(it)
        } ?: clearRef()
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity == activityRef?.get()) {
            clearRef()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    /*
    PinDialogCallback
     */

    override fun onPinInput(pin: String) {
        currentPinRequest?.confirmPin(pin)
        clearRequest()
    }

    override fun onCancel() {
        currentPinRequest?.cancel(null)
        clearRequest()
    }

    /* -- */

    fun register(app: Application) {
        app.registerActivityLifecycleCallbacks(this)
    }

    private fun clearRef() {
        activityRef?.clear()
        activityRef = null
    }

    private fun clearRequest() {
        currentPinRequest = null
    }

    private fun showPinDialogIfNotVisible(activity: FragmentActivity) {
        // Just return if the PinInputDialogFragment is already visible on the given activity.
        if (activity.supportFragmentManager.findFragmentByTag(TAG_PIN_DIALOG_FRAGMENT) != null) return

        PinInputDialogFragment.Builder()
            .withCallback(this)
            .build()
            .show(activity.supportFragmentManager, TAG_PIN_DIALOG_FRAGMENT)
    }

    private fun showPinNotSetError(activity: FragmentActivity) {
        ErrorDialogFragment.Builder().apply {
            withTitle(activity.getString(R.string.pin_popup_title))
            withMessage(activity.getString(R.string.pin_popup_no_pin_message))
        }.build().show(activity.supportFragmentManager, null)
    }

    private fun showInvalidPinError(activity: FragmentActivity) {
        ErrorDialogFragment.Builder().apply {
            withTitle(activity.getString(R.string.pin_popup_wrong_pin_title))
            withMessage(activity.getString(R.string.pin_popup_wrong_pin_msg))
        }.build().show(activity.supportFragmentManager, null)
    }

    private fun showUserBlockedError(
        activity: FragmentActivity,
        attempts: Int,
        blockingTimeSeconds: Int
    ) {
        ErrorDialogFragment.Builder().apply {
            withTitle(activity.getString(R.string.general_error))
            withMessage(formatUserBlockedMessage(activity, attempts, blockingTimeSeconds))
        }.build().show(activity.supportFragmentManager, null)
    }

    private fun formatUserBlockedMessage(
        activity: FragmentActivity,
        attempts: Int,
        blockingTimeSeconds: Int
    ): String {
        val template = activity.getString(R.string.pin_popup_user_blocked_message)
        val minutes =
            (
                TimeUnit.SECONDS.toMinutes(blockingTimeSeconds.toLong()) - TimeUnit.MILLISECONDS.toMinutes(
                    Date().time
                )
                ).toInt()
        val plural = activity.resources.getQuantityString(
            R.plurals.pin_popup_user_blocked_message_minutes,
            minutes,
            minutes
        )
        return String.format(template, attempts, plural)
    }

    private companion object {

        private const val TAG_PIN_DIALOG_FRAGMENT = "mb.mobile.sdk.pin.provider.dialog.fragment"
    }
}
