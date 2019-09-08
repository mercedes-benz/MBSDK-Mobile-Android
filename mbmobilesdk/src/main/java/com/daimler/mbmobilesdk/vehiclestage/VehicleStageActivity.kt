package com.daimler.mbmobilesdk.vehiclestage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.serviceactivation.ServiceOverviewActivity
import com.daimler.mbmobilesdk.utils.emptyVehicleInfo
import com.daimler.mbmobilesdk.vehicleassignment.AssignVehicleActivity
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity

@Deprecated("This activity is no longer used in the registration workflow and will be removed in a future release.")
class VehicleStageActivity : MBBaseViewModelActivity<VehicleStageViewModel>(), VehicleStageCallback {

    private lateinit var currentMode: VehicleStageMode
    private var currentResult: Int = 0

    override fun createViewModel(): VehicleStageViewModel {
        currentMode = intent.getSerializableExtra(ARG_MODE) as VehicleStageMode
        val vehicle = intent.getParcelableExtra<VehicleInfo>(ARG_VEHICLE)
        val factory = VehicleStageViewModelFactory(application, vehicle)
        return ViewModelProviders.of(this, factory).get(VehicleStageViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_vehicle_stage

    override fun getModelId(): Int = BR.model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) swapMode(currentMode, false)
    }

    override fun onAssignVehicle() {
        startActivityForResult(AssignVehicleActivity.getStartIntent(this, true,
            VehicleAssignmentStage.AssignmentSelection(this).toStageConfig()),
            REQ_CODE_VEHICLE_ASSIGNMENT)
    }

    override fun onAssignmentCancelled() {
        finishWithResult()
    }

    override fun onActivateServices() {
        startActivityForResult(ServiceOverviewActivity.getStartIntent(this,
            viewModel.vehicle(), true), REQ_CODE_SERVICE_ACTIVATION)
    }

    override fun onServiceActivationCancelled() {
        finishWithResult()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_VEHICLE_ASSIGNMENT -> {
                val finOrVin =
                    data?.getStringExtra(AssignVehicleActivity.ARG_ASSIGNED_VEHICLE_VIN)
                val validated =
                    data?.getBooleanExtra(AssignVehicleActivity.ARG_IS_VALIDATED, false) == true
                if (resultCode == Activity.RESULT_OK && finOrVin != null && validated) {
                    vehicleAssigned(finOrVin)
                } else {
                    finishWithResult()
                }
            }
            REQ_CODE_SERVICE_ACTIVATION -> {
                addToResult(FLAG_SERVICES_ACTIVATED)
                finishWithResult()
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun vehicleAssigned(finOrVin: String) {
        viewModel.updateVin(finOrVin)
        addToResult(FLAG_VEHICLE_ASSIGNED)
        swapMode(VehicleStageMode.SERVICES, false)
    }

    private fun addToResult(flag: Int) {
        currentResult = currentResult.or(flag)
    }

    private fun finishWithResult() {
        val intent = Intent()
        intent.putExtra(ARG_STAGE_RESULT, currentResult)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun swapMode(mode: VehicleStageMode, addToBackStack: Boolean) {
        when (mode) {
            VehicleStageMode.ASSIGN ->
                replace(StageAssignFragment.newInstance(), addToBackStack,
                    getString(R.string.assign_toolbar))
            VehicleStageMode.SERVICES ->
                replace(StageServicesFragment.newInstance(), addToBackStack,
                    getString(R.string.activate_services_title))
        }
    }

    private fun replace(fragment: StageDescriptionFragment<*>, addToBackStack: Boolean, title: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment, null)
            if (addToBackStack) addToBackStack(null)
        }.commitAllowingStateLoss()
        changeTitle(title)
    }

    private fun changeTitle(title: String) = viewModel.toolbarTitle.postValue(title)

    companion object {

        internal const val FLAG_VEHICLE_ASSIGNED = 0x00000001
        internal const val FLAG_SERVICES_ACTIVATED = 0x00000002

        const val ARG_STAGE_RESULT = "arg.stage.result"

        private const val ARG_VEHICLE = "arg.stage.vehicle.licensePlate"
        private const val ARG_MODE = "arg.stage.vehicle.mode"

        private const val REQ_CODE_VEHICLE_ASSIGNMENT = 11
        private const val REQ_CODE_SERVICE_ACTIVATION = 12

        fun getStartIntent(
            context: Context,
            vehicle: VehicleInfo?,
            mode: VehicleStageMode = VehicleStageMode.ASSIGN
        ): Intent {
            val intent = Intent(context, VehicleStageActivity::class.java)
            intent.putExtra(ARG_VEHICLE, vehicle ?: emptyVehicleInfo())
            intent.putExtra(ARG_MODE, mode)
            return intent
        }
    }
}