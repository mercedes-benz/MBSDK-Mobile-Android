package com.daimler.mbmobilesdk.vehicleselection

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

internal abstract class BaseVehicleGarageItem(
    val itemId: String,
    val type: GarageItemType,
    private val listener: VehicleGarageItemListener
) : MBBaseRecyclerItem() {

    var visibleToUser = false
        set(value) {
            field = value
            userVisible.set(value)
        }

    val userVisible = ObservableBoolean(visibleToUser)

    abstract val finOrVin: String?

    fun onItemClicked() {
        if (!visibleToUser) {
            notifyScrollToItem()
        } else {
            onHandleItemClick()
        }
    }

    fun isSameItemType(type: GarageItemType) = type == this.type

    fun isVehicleType() = type.isVehicleType

    fun isPendingType() = type == GarageItemType.PENDING

    abstract fun update(vehicle: GarageVehicle)

    abstract fun updateSelection(selected: Boolean)

    abstract fun updateVehicleImage(image: Drawable)

    abstract fun isSameVehicle(vehicle: GarageVehicle): Boolean

    abstract fun isSameFinOrVin(finOrVin: String): Boolean

    abstract fun isVehicleSelected(): Boolean

    abstract fun isSelectableVehicle(): Boolean

    open fun setItemProcessing(processing: Boolean) = Unit

    fun isItemTypeFor(vehicle: GarageVehicle) =
        isSameItemType(GarageItemFactory.getGarageItemType(vehicle))

    fun ifIsSelectableVehicle(action: (String) -> Unit) {
        if (isSelectableVehicle()) {
            finOrVin?.let(action)
        }
    }

    protected open fun onHandleItemClick() = Unit

    protected fun notifyVehicleSelected(vehicle: GarageVehicle) {
        listener.onVehicleSelected(vehicle)
    }

    protected fun notifyAddVehicle() {
        listener.onAssignVehicle()
    }

    protected fun notifyContinueAssignment(vehicle: GarageVehicle) {
        listener.onContinueAssignment(vehicle)
    }

    private fun notifyScrollToItem() {
        listener.onScrollToVehicle(itemId)
    }
}