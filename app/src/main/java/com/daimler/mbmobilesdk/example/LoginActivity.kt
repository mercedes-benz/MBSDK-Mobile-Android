package com.daimler.mbmobilesdk.example

import android.content.Intent
import com.daimler.mbmobilesdk.example.menu.MenuActivity
import com.daimler.mbmobilesdk.login.MBLoginActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment

class LoginActivity : MBLoginActivity() {

    override fun onUserAuthenticated() {
        showMenu()
    }

    override fun onLoginFailed(error: String) {
        MBDialogFragment.Builder(0).apply {
            withMessage("Login failed: $error. Still proceeding to menu?")
            withPositiveButtonText("Yes")
            withNegativeButtonText("No")
            withListener(object : MBDialogFragment.DialogListener {
                override fun onNegativeAction(id: Int) = Unit

                override fun onPositiveAction(id: Int) {
                    showMenu()
                }
            })
        }.build().show(supportFragmentManager, null)
    }

    private fun showMenu() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}