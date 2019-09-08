package com.daimler.mbmobilesdk.vehicleselection

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R

internal class InsufficientVehicleGarageItem(
    itemId: String,
    private val vehicle: GarageVehicle,
    type: GarageItemType,
    listener: VehicleGarageItemListener
) : BaseVehicleGarageItem(itemId, type, listener) {

    val carImage = ObservableField<Drawable>()
    val progressVisible = ObservableBoolean(false)

    override val finOrVin: String = vehicle.finOrVin

    override fun getLayoutRes(): Int = R.layout.item_insufficient_vehicle_garage

    override fun getModelId(): Int = BR.item

    override fun onHandleItemClick() {
        notifyContinueAssignment(vehicle)
    }

    override fun update(vehicle: GarageVehicle) = Unit

    override fun updateSelection(selected: Boolean) = Unit

    override fun updateVehicleImage(image: Drawable) {
        carImage.set(image)
    }

    override fun isSameVehicle(vehicle: GarageVehicle): Boolean = vehicle.finOrVin == this.vehicle.finOrVin

    override fun isSameFinOrVin(finOrVin: String): Boolean = finOrVin == this.vehicle.finOrVin

    override fun isVehicleSelected(): Boolean = false

    override fun isSelectableVehicle(): Boolean = false

    override fun setItemProcessing(processing: Boolean) {
        progressVisible.set(processing)
    }
}