package com.daimler.mbmobilesdk.menu

import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.menu.MBMenuItem
import com.daimler.mbuikit.menu.MenuProvider
import java.util.*

/**
 * Menu provider for the basic menu.
 */
object LoginMenuProvider : MenuProvider() {

    val HOME = MBMenuItemGrey(R.drawable.ic_home, R.string.menu_home)
    val APP_FAMILY = MBMenuItemGrey(R.drawable.ic_app_grid, R.string.app_family_title)
    val SETTINGS = MBMenuItem(R.drawable.ic_settings, R.string.menu_settings)
    val LEGAL = MBMenuItem(R.drawable.ic_info_filled, R.string.menu_legal)
    val SUPPORT = MBMenuItemGrey(R.drawable.ic_chat, R.string.menu_support)

    override val items: List<MBMenuItem> =
        Arrays.asList(HOME, APP_FAMILY, SUPPORT, LEGAL, SETTINGS)
}