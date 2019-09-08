package com.daimler.mbmobilesdk.legal.license

import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem
import com.daimler.mbuikit.eventbus.EventBus

class LicenseItem(private val lib: Library) : MBBaseRecyclerItem() {

    val title = lib.name
    val licenseTitle = lib.licenses.joinToString { it.license }

    override fun getLayoutRes(): Int = R.layout.item_license

    override fun getModelId(): Int = BR.model

    fun onItemClicked() {
        EventBus.fuel(LicenseSelectedEvent(lib))
    }
}