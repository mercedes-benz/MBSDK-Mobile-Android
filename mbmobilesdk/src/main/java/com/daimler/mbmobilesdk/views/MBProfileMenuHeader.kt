package com.daimler.mbmobilesdk.views

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimler.mbmobilesdk.R
import com.daimler.mbcarkit.business.model.vehicle.FuelType
import kotlinx.android.synthetic.main.view_menu_header.view.group_energy
import kotlinx.android.synthetic.main.view_menu_header.view.group_no_vehicle
import kotlinx.android.synthetic.main.view_menu_header.view.group_tank
import kotlinx.android.synthetic.main.view_menu_header.view.group_vehicle
import kotlinx.android.synthetic.main.view_menu_header.view.header_image
import kotlinx.android.synthetic.main.view_menu_header.view.header_vehicle_image
import kotlinx.android.synthetic.main.view_menu_header.view.image_lock
import kotlinx.android.synthetic.main.view_menu_header.view.profile
import kotlinx.android.synthetic.main.view_menu_header.view.text_baumuster
import kotlinx.android.synthetic.main.view_menu_header.view.text_energy
import kotlinx.android.synthetic.main.view_menu_header.view.text_id
import kotlinx.android.synthetic.main.view_menu_header.view.text_lock_state
import kotlinx.android.synthetic.main.view_menu_header.view.text_model
import kotlinx.android.synthetic.main.view_menu_header.view.text_name
import kotlinx.android.synthetic.main.view_menu_header.view.text_no_vehicle
import kotlinx.android.synthetic.main.view_menu_header.view.text_tank
import kotlinx.android.synthetic.main.view_menu_header.view.vehicle

class MBProfileMenuHeader : ConstraintLayout {

    private var tankLevel: Int? = null
    private var energyLevel: Int? = null
    private var vehicleFuelType: FuelType? = null

    private var listener: OnHeaderClickedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_menu_header, this, true)
        header_image.setOnClickListener { listener?.onHeaderImageClicked() }
        profile.setOnClickListener { listener?.onHeaderProfileClicked() }
        vehicle.setOnClickListener { listener?.onVehicleClicked() }
    }

    fun setUserName(userName: String?) {
        text_name.text = userName
    }

    fun setUserId(userId: String?) {
        text_id.text = userId
    }

    fun setProfilePicture(bitmap: Bitmap?) {
        bitmap?.let { header_image.setImageBitmap(bitmap) }
    }

    fun setVehicleModel(model: String?) {
        text_model.text = model
    }

    fun setVehicleInfo(info: String?) {
        text_baumuster.text = info
    }

    fun setVehicleLocked(locked: Boolean?) {
        text_lock_state.text = when (locked) {
            true -> resources.getText(R.string.menu_garage_locked)
            false -> resources.getText(R.string.menu_garage_unlocked)
            null -> "-"
        }
        val imageRes = when (locked) {
            true -> R.drawable.ic_locked
            false -> R.drawable.ic_unlocked
            null -> R.drawable.ic_error
        }
        image_lock.setImageResource(imageRes)
    }

    fun setLoadingData(loading: Boolean) {
        if (loading) {
            text_no_vehicle.text = resources.getString(R.string.menu_garage_loading)
        } else {
            text_no_vehicle.text = resources.getString(R.string.menu_garage_no_vehicle_connected)
        }
    }

    /**
     * Sets the current tanklevel. Pass null if this vehicle has no tank. This will hide the view.
     */
    fun setVehicleTankLevel(tankLevelPercent: Int?) {
        tankLevel = tankLevelPercent
        setVehicleFuelType(vehicleFuelType)
    }

    /**
     * Sets the current energylevel. Pass null if this vehicle has no tank. This will hide the view.
     */
    fun setVehicleEnergyLevel(energyLevelPercent: Int?) {
        energyLevel = energyLevelPercent
        setVehicleFuelType(vehicleFuelType)
    }

    /**
     * Sets the current fuel type. Pass null if this vehicle has no fuel type. This will hide all the tank views
     */
    fun setVehicleFuelType(fuelType: FuelType?) {
        vehicleFuelType = fuelType
        fuelType?.let {
            showFuelTypes(it)
        } ?: hideFuelTypes()
    }

    fun setVehicleSelected(selected: Boolean) {
        if (selected) {
            showSelection()
        } else {
            showEmptyGarage()
        }
    }

    fun updateProfilePicture(bitmap: Bitmap) {
        header_image.setImageBitmap(bitmap)
    }

    fun updateVehiclePicture(bitmap: Bitmap) {
        header_vehicle_image.setImageBitmap(bitmap)
    }

    fun setOnHeaderClickListener(observer: OnHeaderClickedListener?) {
        listener = observer
    }

    private fun showFuelTypes(fuelType: FuelType) {
        when (fuelType) {
            FuelType.ELECTRIC -> showElectricFuelTypes(energyLevel)
            FuelType.COMBUSTION -> showCombustionFuelTypes(tankLevel)
            FuelType.HYBRID, FuelType.PLUGIN -> showHybridOrPluginFuelTypes(tankLevel, energyLevel)
            FuelType.FUEL_CELL_PLUGIN -> showFuelCellPluginFuelTypes(energyLevel, null)
            else -> hideFuelTypes()
        }
    }

    private fun showElectricFuelTypes(energyLevel: Int?) {
        showOrHideEnergyLevel(energyLevel)
        hideTankLevel()
    }

    private fun showCombustionFuelTypes(tankLevel: Int?) {
        showOrHideTankLevel(tankLevel)
        hideEnergyLevel()
    }

    private fun showHybridOrPluginFuelTypes(tankLevel: Int?, energyLevel: Int?) {
        showOrHideEnergyLevel(energyLevel)
        showOrHideTankLevel(tankLevel)
    }

    /**
     * todo: Add a gas state
     */
    private fun showFuelCellPluginFuelTypes(energyLevel: Int?, gasLevel: Int?) {
        showOrHideEnergyLevel(energyLevel)
    }

    /**
     * Displays the energy level if the passed level is non null
     */
    private fun showOrHideEnergyLevel(energyLevel: Int?) {
        energyLevel?.let {
            showEnergyLevel(it)
        } ?: hideEnergyLevel()
    }

    /**
     * Displays the tank level if the passed level is non null
     */
    private fun showOrHideTankLevel(tankLevel: Int?) {
        tankLevel?.let {
            showTankLevel(it)
        } ?: hideTankLevel()
    }

    private fun hideFuelTypes() {
        hideTankLevel()
        hideEnergyLevel()
    }

    private fun showSelection() {
        group_no_vehicle.visibility = View.GONE
        group_vehicle.visibility = View.VISIBLE

        setVehicleTankLevel(tankLevel)
        setVehicleEnergyLevel(energyLevel)
    }

    private fun showEmptyGarage() {
        group_no_vehicle.visibility = View.VISIBLE
        group_vehicle.visibility = View.GONE
        group_tank.visibility = View.GONE
        group_energy.visibility = View.GONE
    }

    private fun showTankLevel(level: Int) {
        group_tank.visibility = View.VISIBLE
        text_tank.apply {
            text = resources.getString(R.string.menu_vehicle_tank, level.toString())
        }
    }

    private fun hideTankLevel() {
        group_tank.visibility = View.GONE
    }

    private fun showEnergyLevel(level: Int) {
        group_energy.visibility = View.VISIBLE
        text_energy.apply {
            text = resources.getString(R.string.menu_vehicle_tank, level.toString())
        }
    }

    private fun hideEnergyLevel() {
        group_energy.visibility = View.GONE
    }

    interface OnHeaderClickedListener {

        fun onHeaderImageClicked()

        fun onHeaderProfileClicked()

        fun onVehicleClicked()
    }
}