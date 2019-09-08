package com.daimler.mbmobilesdk.vehicleselection

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.ViewDataBinding
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.FragmentGarageBinding
import com.daimler.mbmobilesdk.serviceactivation.ServiceOverviewActivity
import com.daimler.mbmobilesdk.utils.extensions.canShowDialog
import com.daimler.mbmobilesdk.utils.extensions.createAndroidViewModel
import com.daimler.mbmobilesdk.utils.extensions.showSimpleTextDialog
import com.daimler.mbmobilesdk.vehicleassignment.AssignVehicleActivity
import com.daimler.mbmobilesdk.vehicleassignment.AssignmentCodeActivity
import com.daimler.mbmobilesdk.vehicleassignment.RifActivity
import com.daimler.mbmobilesdk.vehicledeletion.DeletableVehicle
import com.daimler.mbmobilesdk.vehicledeletion.VehicleDeletionActivity
import com.daimler.mbmobilesdk.vehiclestage.VehicleAssignmentStage
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.toast

internal class GarageFragment : MBBaseMenuFragment<GarageViewModel>() {

    private var listener: VehicleGarageListener? = null

    override fun createViewModel(): GarageViewModel {
        return createAndroidViewModel()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_garage

    override fun getModelId(): Int = BR.model

    override fun getToolbarTitleRes(): Int = R.string.menu_garage

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        setHasOptionsMenu(true)

        (binding as FragmentGarageBinding).recyclerVehicles.adapter = GarageItemLiveAdapter()

        viewModel.apply {
            vehiclesLoadedEvent.observe(this@GarageFragment, onVehiclesLoaded())
            vehicleSelectedEvent.observe(this@GarageFragment, onVehicleSelected())
            addVehicleEvent.observe(this@GarageFragment, onAddVehicle())
            vehicleInfoEvent.observe(this@GarageFragment, onVehicleInfo())
            userManagementEvent.observe(this@GarageFragment, onUserManagement())
            showServicesEvent.observe(this@GarageFragment, onShowServices())
            showVacEvent.observe(this@GarageFragment, onShowVac())
            showNoRifSupportEvent.observe(this@GarageFragment, onNoRifSupport())
            vehiclesNotLoadedError.observe(this@GarageFragment, onVehiclesNotLoadedError())
            unselectVehicleEvent.observe(this@GarageFragment, onUnselectVehicle())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? VehicleGarageListener
            ?: throw IllegalArgumentException("Hosting activity must implement VehicleGarageListener!")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_garage, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        // "Delete" option is only visible if there are vehicles that can be removed.
        menu.findItem(R.id.menu_garage_delete).isVisible = viewModel.fullyAssignedCarsAmount > 0
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.menu_garage_delete == item.itemId) {
            viewModel.deletableVehicles()?.let {
                startVehicleDeletion(it)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_ASSIGN_VEHICLE -> {
                val isLegacyVehicle = data?.getBooleanExtra(AssignVehicleActivity.ARG_IS_LEGACY_VEHICLE, false) ?: false
                data?.getStringExtra(AssignVehicleActivity.ARG_ASSIGNED_VEHICLE_VIN)?.let {
                    viewModel.vehicleAssigned(it, isLegacyVehicle)
                }
            }
            REQ_CODE_DELETE_VEHICLE ->
                data?.getStringExtra(VehicleDeletionActivity.ARG_DELETED_VEHICLE_VIN)?.let {
                    viewModel.vehicleDeleted(it)
                    listener?.onVehicleDeleted(it)
                }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun onVehiclesLoaded() = LiveEventObserver<Int> {
        activity?.invalidateOptionsMenu()
    }

    private fun onVehicleSelected() = LiveEventObserver<VehicleSelectedEvent> {
        listener?.onVehicleSelectedFromGarage(it.vehicleInfo, it.autoSelected)
    }

    private fun onAddVehicle() = LiveEventObserver<Unit> {
        context?.let {
            startActivityForResult(AssignVehicleActivity.getStartIntent(it, false,
                VehicleAssignmentStage.AssignmentSelection(it).toStageConfig()),
                REQ_CODE_ASSIGN_VEHICLE)
        }
    }

    private fun onUserManagement() = LiveEventObserver<GarageVehicle> {
        // TODO: 2019-05-05 implement
        toast("User-Management for ${it.finOrVin}")
    }

    private fun onVehicleInfo() = LiveEventObserver<GarageVehicle> { vehicle ->
        context?.let { ctx ->
            MBCarKit.loadVehicleByVin(vehicle.finOrVin)?.let {
                startActivity(VehicleInfoActivity.getStartIntent(ctx, it))
            }
        }
    }

    private fun onShowServices() = LiveEventObserver<GarageVehicle> { vehicle ->
        context?.let { ctx ->
            MBCarKit.loadVehicleByVin(vehicle.finOrVin)?.let {
                startActivity(ServiceOverviewActivity.getStartIntent(ctx, it))
            }
        }
    }

    private fun onShowVac() = LiveEventObserver<GarageVehicle> { vehicle ->
        context?.let {
            startActivity(AssignmentCodeActivity.getStartIntent(it, vehicle.finOrVin))
        }
    }

    private fun onNoRifSupport() = LiveEventObserver<GarageVehicle> { vehicle ->
        context?.let {
            startActivity(RifActivity.getStartIntent(it, vehicle.finOrVin, true))
        }
    }

    private fun onVehiclesNotLoadedError() = LiveEventObserver<Unit> {
        if (canShowDialog()) {
            showSimpleTextDialog(getString(R.string.general_error_msg))
        }
    }

    private fun onUnselectVehicle() = LiveEventObserver<Unit> {
        listener?.onVehiclesUnselected()
    }

    private fun startVehicleDeletion(vehicles: List<DeletableVehicle>) {
        context?.let {
            startActivityForResult(VehicleDeletionActivity.getStartIntent(it, vehicles), REQ_CODE_DELETE_VEHICLE)
        }
    }

    companion object {

        private const val REQ_CODE_ASSIGN_VEHICLE = 21
        private const val REQ_CODE_DELETE_VEHICLE = 22

        fun newInstance(): GarageFragment = GarageFragment()
    }
}