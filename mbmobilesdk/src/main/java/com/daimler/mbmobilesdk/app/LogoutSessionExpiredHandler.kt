package com.daimler.mbmobilesdk.app

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.LogoutSessionExpiredHandler.init
import com.daimler.mbmobilesdk.utils.WeakRefActivityLifecycleCallbacks
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.login.SessionExpiredHandler
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment

/**
 * [SessionExpiredHandler] implementation that shows a dialog to tell the user that his session
 * is expired. It performs a logout and redirects to the class given by [init]
 * after the dialog has been closed.
 * Socket connection will be closed and all local cache will be cleared upon logout.
 */
object LogoutSessionExpiredHandler :
    SessionExpiredHandler,
    MBDialogFragment.DialogListener,
    WeakRefActivityLifecycleCallbacks(true) {

    private const val ID_DIALOG_SESSION_EXPIRED = 1

    private lateinit var redirectActivityClass: Class<out Activity>
    private var dialogShowing = false

    fun init(app: Application, redirectActivityClass: Class<out Activity>) {
        initCallbacks(app)
        this.redirectActivityClass = redirectActivityClass
    }

    override fun onSessionExpired(statusCode: Int, errorBody: String?) {
        require(::redirectActivityClass.isInitialized) { "Redirect class is not defined. Did you call init()?" }
        if (!dialogShowing) {
            val activity = activity() as AppCompatActivity
            dialogShowing = true
            MBDialogFragment.Builder(ID_DIALOG_SESSION_EXPIRED).apply {
                withMessage(activity.getString(R.string.login_session_expired))
                withPositiveButtonText(activity.getString(R.string.general_okay))
                withListener(this@LogoutSessionExpiredHandler)
            }.build().show(activity.supportFragmentManager, null)
        }
    }

    override fun onPositiveAction(id: Int) {
        dialogShowing = false
        MBIngressKit.logout()
            .onAlways { _, _, _ ->
                MBMobileSDK.destroy()
                val activity = activity()
                val intent = Intent(activity, redirectActivityClass)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK
                activity.startActivity(intent)
            }
    }

    override fun onNegativeAction(id: Int) = Unit
}