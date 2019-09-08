package com.daimler.mbmobilesdk.vehicleselection

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R

internal class VehicleGarageItem(
    itemId: String,
    private var vehicle: GarageVehicle,
    type: GarageItemType,
    listener: VehicleGarageItemListener
) : BaseVehicleGarageItem(itemId, type, listener) {

    val carImage = ObservableField<Drawable>()
    val selected = ObservableBoolean(vehicle.selected)
    val licensePlate = ObservableField(vehicle.licensePlate)

    override val finOrVin: String
        get() = vehicle.finOrVin

    override fun getLayoutRes(): Int {
        return R.layout.item_vehicle_garage
    }

    override fun getModelId(): Int {
        return BR.item
    }

    override fun update(vehicle: GarageVehicle) {
        this.vehicle = vehicle
        selected.set(vehicle.selected)
        licensePlate.set(vehicle.licensePlate)
    }

    override fun updateSelection(selected: Boolean) {
        this.selected.set(selected)
    }

    override fun updateVehicleImage(image: Drawable) {
        carImage.set(image)
    }

    override fun isSameVehicle(vehicle: GarageVehicle): Boolean {
        return this.vehicle.finOrVin == vehicle.finOrVin
    }

    override fun isSameFinOrVin(finOrVin: String): Boolean {
        return this.vehicle.finOrVin == finOrVin
    }

    override fun onHandleItemClick() {
        if (!selected.get()) notifyVehicleSelected(vehicle.copy())
    }

    override fun isVehicleSelected(): Boolean {
        return selected.get()
    }

    override fun isSelectableVehicle(): Boolean {
        return vehicle.isSelectableVehicle()
    }
}