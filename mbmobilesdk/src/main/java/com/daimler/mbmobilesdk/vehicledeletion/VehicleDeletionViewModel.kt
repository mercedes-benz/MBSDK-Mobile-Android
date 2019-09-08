package com.daimler.mbmobilesdk.vehicledeletion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.utils.LineDividerDecorator
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class VehicleDeletionViewModel(
    app: Application,
    vehicles: List<DeletableVehicle>
) : AndroidViewModel(app) {

    val disconnectEnabled = mutableLiveDataOf(false)
    val disconnectVisible = mutableLiveDataOf(true)
    val progressVisible = mutableLiveDataOf(false)
    val items = MutableLiveArrayList<VehicleDeletionItem>()

    val cancelEvent = MutableLiveUnitEvent()
    val deleteEvent = MutableLiveEvent<DeletableVehicle>()
    val vehicleDeletedEvent = MutableLiveEvent<String>()
    val errorEvent = MutableLiveEvent<String>()

    private var checkedVehicle: DeletableVehicle? = null

    init {
        prepareItems(vehicles)
    }

    fun getDecorator() = LineDividerDecorator.createDefault(getApplication())

    fun onCancelClicked() {
        cancelEvent.sendEvent()
    }

    fun onDisconnectClicked() {
        checkedVehicle?.let {
            deleteEvent.sendEvent(it)
        }
    }

    fun deleteVehicle(finOrVin: String) {
        onProcessing()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                MBCarKit.assignmentService().unassignVehicleByVin(token.jwtToken.plainToken, finOrVin)
                    .onComplete {
                        MBLoggerKit.d("Completed unassignment of $finOrVin.")
                        onVehicleDeleted(finOrVin)
                        vehicleDeletedEvent.sendEvent(finOrVin)
                    }.onFailure {
                        MBLoggerKit.re("Failed to unassign vehicle.", it)
                        errorEvent.sendEvent(defaultErrorMessage(it))
                    }.onAlways { _, _, _ ->
                        onProcessingFinished()
                    }
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                onProcessingFinished()
                errorEvent.sendEvent(defaultErrorMessage(it))
            }
    }

    private fun prepareItems(vehicles: List<DeletableVehicle>) {
        val items = vehicles.map {
            VehicleDeletionItem(it) { vehicle, checked ->
                markVehicle(vehicle, checked)
            }
        }
        this.items.addAllAndDispatch(items)
        onItemsChanged()
    }

    private fun markVehicle(vehicle: DeletableVehicle, checked: Boolean) {
        if (checked) {
            checkedVehicle = vehicle
            disconnectEnabled.postValue(true)
            items.value.filter {
                !it.isSameFinOrVin(vehicle.finOrVin)
            }.forEach {
                it.setCheckedState(false)
            }
        } else {
            checkedVehicle = null
            disconnectEnabled.postValue(false)
        }
    }

    private fun onVehicleDeleted(finOrVin: String) {
        checkedVehicle = null
        items.value.removeAll { it.isSameFinOrVin(finOrVin) }
        items.dispatchChange()
        onItemsChanged()
    }

    private fun onItemsChanged() {
        disconnectVisible.postValue(items.value.isNotEmpty())
    }

    private fun onProcessing() {
        progressVisible.postValue(true)
        disconnectEnabled.postValue(false)
        disableItems()
    }

    private fun onProcessingFinished() {
        progressVisible.postValue(false)
        disconnectEnabled.postValue(true)
        enableItems()
    }

    private fun enableItems() {
        items.value.forEach { it.setEnabledState(true) }
    }

    private fun disableItems() {
        items.value.forEach { it.setEnabledState(false) }
    }
}