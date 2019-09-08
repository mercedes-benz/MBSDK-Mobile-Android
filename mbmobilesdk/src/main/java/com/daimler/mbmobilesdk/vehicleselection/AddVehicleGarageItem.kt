package com.daimler.mbmobilesdk.vehicleselection

import android.graphics.drawable.Drawable
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R

internal class AddVehicleGarageItem(
    itemId: String,
    type: GarageItemType,
    listener: VehicleGarageItemListener
) : BaseVehicleGarageItem(itemId, type, listener) {

    override val finOrVin: String? = null

    override fun getLayoutRes(): Int = R.layout.item_add_vehicle_garage

    override fun getModelId(): Int = BR.item

    override fun onHandleItemClick() {
        notifyAddVehicle()
    }

    override fun update(vehicle: GarageVehicle) = Unit

    override fun updateSelection(selected: Boolean) = Unit

    override fun updateVehicleImage(image: Drawable) = Unit

    override fun isSameVehicle(vehicle: GarageVehicle): Boolean = false

    override fun isSameFinOrVin(finOrVin: String): Boolean = false

    override fun isVehicleSelected(): Boolean = false

    override fun isSelectableVehicle(): Boolean = false
}