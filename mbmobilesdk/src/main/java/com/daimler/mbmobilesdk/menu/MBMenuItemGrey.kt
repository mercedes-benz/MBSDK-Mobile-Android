package com.daimler.mbmobilesdk.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.menu.MBMenuItem

class MBMenuItemGrey(@DrawableRes drawableRes: Int, @StringRes titleRes: Int) : MBMenuItem(drawableRes, titleRes) {
    override fun getLayoutRes(): Int {
        return R.layout.item_mb_menu_grey
    }
}