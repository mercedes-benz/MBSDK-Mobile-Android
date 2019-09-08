package com.daimler.mbmobilesdk.vehicleselection

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState

internal class PendingVehicleGarageItem(
    itemId: String,
    override val finOrVin: String,
    private var assignmentState: AssignmentPendingState,
    type: GarageItemType,
    listener: VehicleGarageItemListener
) : BaseVehicleGarageItem(itemId, type, listener) {

    val pendingAction = ObservableField(getPendingAction(assignmentState))

    override fun getLayoutRes(): Int = R.layout.item_pending_vehicle_garage

    override fun getModelId(): Int = BR.item

    override fun isSameFinOrVin(finOrVin: String) = finOrVin == this.finOrVin

    override fun update(vehicle: GarageVehicle) {
        assignmentState = vehicle.assignmentState
        pendingAction.set(getPendingAction(vehicle.assignmentState))
    }

    override fun updateSelection(selected: Boolean) = Unit

    override fun updateVehicleImage(image: Drawable) = Unit

    override fun isSameVehicle(vehicle: GarageVehicle): Boolean = vehicle.finOrVin == finOrVin

    override fun isVehicleSelected(): Boolean = false

    override fun isSelectableVehicle(): Boolean = false

    private fun getPendingAction(assignmentState: AssignmentPendingState) =
        if (assignmentState == AssignmentPendingState.ASSIGN) {
            PendingAction.ASSIGN
        } else {
            PendingAction.UN_ASSIGN
        }

    enum class PendingAction { ASSIGN, UN_ASSIGN }
}