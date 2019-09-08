package com.daimler.mbmobilesdk.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.vehicleselection.GarageVehicle
import com.daimler.mbcarkit.business.model.vehicle.VehicleConnectivity
import com.daimler.mbuikit.utils.extensions.findDrawable
import kotlinx.android.synthetic.main.view_garage_vehicle_information.view.*

internal class GarageVehicleInformationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var garageVehicle: GarageVehicle? = null
    private var listener: GarageVehicleInformationListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_garage_vehicle_information, this, true)

        button_vehicle_info.setOnClickListener { notifyVehicleInfoClicked() }
        button_vehicle_services.setOnClickListener { notifyManageServicesClicked() }
        button_vehicle_user_management.setOnClickListener { notifyUserManagementClicked() }
    }

    fun setGarageVehicle(garageVehicle: GarageVehicle?) {
        this.garageVehicle = garageVehicle?.copy()
        this.garageVehicle?.let {
            setCarModel(it.model)
            setLocked(it.locked)
            setTankLevel(it.tankLevel)
            setConnectVehicleFeaturesEnabled(it.vehicleConnectivity == VehicleConnectivity.BUILT_IN)
        }
    }

    private fun setCarModel(title: String?) {
        tv_model.text = title
    }

    private fun setLocked(locked: Boolean?) {
        val (text: String?, drawable: Drawable?) = when (locked) {
            true -> context.getString(R.string.menu_garage_locked) to findDrawable(R.drawable.ic_locked)
            false -> context.getString(R.string.menu_garage_unlocked) to findDrawable(R.drawable.ic_unlocked)
            null -> "-" to findDrawable(R.drawable.ic_error)
        }
        tv_lock_state.text = text
        image_lock.setImageDrawable(drawable)
    }

    private fun setTankLevel(tankLevel: Int?) {
        tv_tank.text = tankLevel?.let { "$it %" } ?: "-"
    }

    private fun setConnectVehicleFeaturesEnabled(enabled: Boolean) {
        button_vehicle_services.isEnabled = enabled
        button_vehicle_services.alpha = if (enabled) 1f else 0.3f
        button_vehicle_services_decoration.alpha = if (enabled) 1f else 0.3f
        button_vehicle_user_management.isEnabled = enabled
        button_vehicle_user_management.alpha = if (enabled) 1f else 0.3f
        button_vehicle_user_management_decoration.alpha = if (enabled) 1f else 0.3f
    }

    fun setVehicleInformationListener(listener: GarageVehicleInformationListener?) {
        this.listener = listener
    }

    private fun notifyVehicleInfoClicked() {
        notifyListener { onVehicleInformationClicked(it) }
    }

    private fun notifyManageServicesClicked() {
        notifyListener { onManageServicesClicked(it) }
    }

    private fun notifyUserManagementClicked() {
        notifyListener { onUserManagementClicked(it) }
    }

    private fun notifyListener(action: GarageVehicleInformationListener.(GarageVehicle) -> Unit) {
        garageVehicle?.let {
            listener?.action(it)
        }
    }

    interface GarageVehicleInformationListener {

        fun onVehicleInformationClicked(vehicle: GarageVehicle)

        fun onManageServicesClicked(vehicle: GarageVehicle)

        fun onUserManagementClicked(vehicle: GarageVehicle)
    }
}