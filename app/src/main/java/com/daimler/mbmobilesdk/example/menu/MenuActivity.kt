package com.daimler.mbmobilesdk.example.menu

import androidx.fragment.app.Fragment
import com.daimler.mbmobilesdk.menu.LoginMenuProvider
import com.daimler.mbmobilesdk.menu.MBMobileSDKActivity
import com.daimler.mbuikit.menu.MBMenuItem
import com.daimler.mbuikit.utils.extensions.toast

class MenuActivity : MBMobileSDKActivity() {

    override val showEndpointInMenuFooter = true

    override fun getContentFragment(): Fragment = HomeFragment.newInstance()

    override fun getInitialMenuItem(): MBMenuItem? = LoginMenuProvider.HOME

    override fun handleMenuItemClick(item: MBMenuItem, alreadySelected: Boolean): Boolean {
        return when (item) {
            LoginMenuProvider.HOME -> {
                replaceFromMenu(HomeFragment.newInstance())
                true
            }
            else -> super.handleMenuItemClick(item, alreadySelected)
        }
    }

    override fun onLogout(success: Boolean) {
        if (success) {
            finish()
        } else {
            toast("logout failed")
        }
    }
}