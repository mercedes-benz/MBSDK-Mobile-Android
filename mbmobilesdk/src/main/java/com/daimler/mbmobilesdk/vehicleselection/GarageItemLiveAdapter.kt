package com.daimler.mbmobilesdk.vehicleselection

import com.daimler.mbuikit.components.recyclerview.MBLiveRecyclerAdapter
import kotlin.math.absoluteValue

internal class GarageItemLiveAdapter : MBLiveRecyclerAdapter<BaseVehicleGarageItem>() {

    override fun fillAdapter(items: List<BaseVehicleGarageItem>) {
        val oldSize = itemCount
        super.fillAdapter(items)
        val newSize = itemCount
        // This is done to "fix" the case where an element was added at the end of the list
        // and the old last element is still decorated.
        if (oldSize != 0 && newSize != 0) {
            val diff = (newSize - oldSize).absoluteValue
            if (diff != 0) {
                val index = oldSize - 1
                if (index in 0 until newSize) notifyItemChanged(index)
            }
        }
    }
}