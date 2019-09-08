package com.daimler.mbmobilesdk.send2car

import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

class VehicleItem(
    val finOrVin: String,
    val text: String,
    private val clickListener: (vehicleItem: VehicleItem) -> Unit
) : MBBaseRecyclerItem() {

    override fun getLayoutRes(): Int = R.layout.item_vehicle

    override fun getModelId(): Int = BR.item

    fun onClick() {
        clickListener(this)
    }
}