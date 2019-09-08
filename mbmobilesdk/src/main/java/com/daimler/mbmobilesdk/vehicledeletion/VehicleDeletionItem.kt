package com.daimler.mbmobilesdk.vehicledeletion

import androidx.databinding.ObservableBoolean
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

internal class VehicleDeletionItem(
    private val vehicle: DeletableVehicle,
    private val onCheckedStateChanged: (DeletableVehicle, Boolean) -> Unit
) : MBBaseRecyclerItem() {

    val enabled = ObservableBoolean(true)
    val title = vehicle.model
    val checked = ObservableBoolean(false)

    override fun getLayoutRes(): Int = R.layout.item_vehicle_deletion

    override fun getModelId(): Int = BR.item

    fun onCheckedChanged(checked: Boolean) {
        if (checked != this.checked.get()) {
            this.checked.set(checked)
            onCheckedStateChanged.invoke(vehicle, checked)
        }
    }

    fun setCheckedState(checked: Boolean) {
        this.checked.set(checked)
    }

    fun setEnabledState(enabled: Boolean) {
        this.enabled.set(enabled)
    }

    fun isSameFinOrVin(finOrVin: String) = this.vehicle.finOrVin == finOrVin
}