package com.daimler.mbmobilesdk.app

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.PopupPinProvider.init
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.pin.UserPinActivity
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.common.UserPinStatus
import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.components.dialogfragments.MBPinDialogFragment

/**
 * Default [PinProvider] implementation that shows a dialog to let the user input the pin.
 * If the user has no pin set, a dialog to redirect the user to the pin-setup screen is shown.
 * This implementation only supports [AppCompatActivity] subclasses as root for the dialogs.
 *
 * Note the following things to use this class:
 *  1. You need to call [init] from your application subclass.
 *  2. You need to make sure that the user is already loaded so
 *  that this object can access the [UserPinStatus].
 */
object PopupPinProvider :
    BasePinProvider(null),
    MBPinDialogFragment.PinDialogListener,
    MBDialogFragment.DialogListener {

    private const val ID_DIALOG_NO_PIN_SET = 1

    private var currentPinRequest: PinRequest? = null
    private var initialized = false

    fun init(app: Application) {
        if (initialized) return
        initialized = true
        initCallbacks(app)
    }

    override fun onInputCancelled() {
        currentPinRequest?.cancel("Cancelled by user.")
        currentPinRequest = null
    }

    override fun onPinConfirmed(pin: String) {
        currentPinRequest?.confirmPin(pin)
        currentPinRequest = null
    }

    override fun onPositiveAction(id: Int) {
        if (id == ID_DIALOG_NO_PIN_SET) {
            // No pin was set -> setup new pin.
            val activity = activity()
            activity.startActivity(UserPinActivity.getStartIntent(activity, true))
        }
    }

    override fun onNegativeAction(id: Int) = Unit

    override fun isEligible(): Boolean = true

    override fun doRequestPin(pinRequest: PinRequest) {
        val activity = activity() as AppCompatActivity
        currentPinRequest = pinRequest
        UserTask().getCachedUser()
            .onComplete {
                if (it.user.pinStatus == UserPinStatus.NOT_SET) {
                    showNoPinDialog(activity)
                } else {
                    showPinDialog(activity)
                }
            }.onFailure {
                MBLoggerKit.e("Failed to get cached user.", throwable = it)
            }
    }

    private fun showNoPinDialog(activity: AppCompatActivity) {
        MBDialogFragment.Builder(ID_DIALOG_NO_PIN_SET).apply {
            withTitle(activity.getString(R.string.pin_popup_title))
            withMessage(activity.getString(R.string.pin_popup_no_pin_message))
            withPositiveButtonText(activity.getString(R.string.pin_popup_button_setup))
            withNegativeButtonText(activity.getString(R.string.pin_popup_cancel))
            withListener(this@PopupPinProvider)
        }.build().show(activity.supportFragmentManager, null)
    }

    private fun showPinDialog(activity: AppCompatActivity) {
        MBPinDialogFragment.Builder().apply {
            withTitle(activity.getString(R.string.pin_popup_confirm_title))
            withMessage(String.format(activity.getString(R.string.pin_popup_description), UserValuePolicy.USER_PIN_DIGITS))
            withPositiveButtonText(activity.getString(R.string.pin_popup_send))
            withNegativeButtonText(activity.getString(R.string.pin_popup_cancel))
            withListener(this@PopupPinProvider)
        }.build().show(activity.supportFragmentManager, null)
    }
}